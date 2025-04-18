package com.brycehan.boot.system.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.system.common.MenuType;
import com.brycehan.boot.system.entity.po.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Set;

/**
 * 系统菜单 Mapper 接口
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
@Mapper
public interface SysMenuMapper extends BryceBaseMapper<SysMenu> {

    /**
     * 根据用户ID查询菜单权限
     *
     * @param userId 用户ID
     * @return 菜单权限集合
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "findAuthorityByUserId")
    Set<String> findAuthorityByUserId(Long userId);

    /**
     * 根据角色ID查询菜单权限
     *
     * @param roleId 角色ID
     * @return 菜单权限集合
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "findAuthorityByRoleId")
    Set<String> findAuthorityByRoleId(Long roleId);

    /**
     * 查询菜单树列表
     *
     * @param userId 用户ID
     * @param type   菜单类型
     * @return 菜单列表
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "selectMenuTreeList")
    List<SysMenu> selectMenuTreeList(Long userId, MenuType... type);

    /**
     * 查询SQL提供者
     */
    @SuppressWarnings("all")
    class SelectSqlProvider {

        /**
         * 根据用户ID查询菜单权限
         *
         * @return SQL
         */
        public static String findAuthorityByUserId(Long userId) {
            return """
                    <script>
                        select sm.authority
                            from brc_sys_menu sm
                            left join brc_sys_role_menu srm on sm.id = srm.menu_id
                            left join brc_sys_user_role sur on srm.role_id = sur.role_id
                            where sm.status = '1'
                                and sm.deleted is null
                                and sm.authority is not null
                                and srm.deleted is null
                                and sur.user_id = #{userId}
                                and sur.deleted is null
                    </script>
                    """;
        }

        /**
         * 根据角色ID查询菜单权限
         *
         * @return SQL
         */
        public static String findAuthorityByRoleId(Long roleId) {
            return """
                    <script>
                        select sm.authority
                            from brc_sys_menu sm
                            left join brc_sys_role_menu srm on sm.id = srm.menu_id
                            where sm.status = '1'
                                and sm.deleted is null
                                and sm.authority is not null
                                and srm.deleted is null
                                and srm.role_id = #{roleId}
                    </script>
                    """;
        }

        /**
         * 查询菜单树列表
         *
         * @return SQL
         */
        public static String selectMenuTreeList(Long userId, MenuType... type) {
            return """
                    <script>
                        select distinct sm.id, sm.name, sm.type, sm.parent_id, sm.icon,
                            sm.url, sm.authority, sm.sort, sm.visible, sm.status,
                            sm.created_time
                        from brc_sys_menu sm
                        left join brc_sys_role_menu srm on sm.id = srm.menu_id
                        left join brc_sys_user_role sur on srm.role_id = sur.role_id
                        where sm.deleted is null
                            and sm.status = '1'
                            and srm.deleted is null
                            and sur.deleted is null
                            and sur.user_id = #{userId}
                            <if test="type != null and type.length > 0">
                                and sm.type in
                                <foreach collection="type" item="item" separator="," open="(" close=")">
                                    #{item}
                                </foreach>
                            </if>
                        order by sm.parent_id, sm.sort
                    </script>
                    """;
        }
    }
}
