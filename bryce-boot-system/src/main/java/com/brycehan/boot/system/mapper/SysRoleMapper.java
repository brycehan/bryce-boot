package com.brycehan.boot.system.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.system.entity.po.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Set;

/**
 * 系统角色Mapper接口
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
@Mapper
public interface SysRoleMapper extends BryceBaseMapper<SysRole> {

    /**
     * 根据用户ID查询角色编码
     *
     * @param userId 用户ID
     * @return 角色编码集合
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "getRoleByUserId")
    Set<SysRole> getRoleByUserId(Long userId);

    /**
     * 查询SQL提供者
     */
    class SelectSqlProvider {

        /**
         * 部门列表
         *
         * @return SQL
         */
        @SuppressWarnings("all")
        public static String getRoleByUserId(Long userId) {
            return """
                    <script>
                        select sr.id, sr.code, sr.data_scope
                        from brc_sys_role sr
                        left join brc_sys_user_role sur on sr.id = sur.role_id
                        where sr.deleted is null
                            and sr.status = 1
                            and sur.deleted is null
                            and sur.user_id = #{userId}
                    </script>
                    """;
        }
    }
}
