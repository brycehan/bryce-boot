package com.brycehan.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.brycehan.boot.system.entity.po.SysRoleDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 系统角色数据范围Mapper接口
 *
 * @since 2023/09/15
 * @author Bryce Han
 */
@Mapper
public interface SysRoleDeptMapper extends BaseMapper<SysRoleDept> {

    /**
     * 获取用户的数据权限列表
     *
     * @param userId 用户ID
     * @return 数据权限列表
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "getDataScopeDeptIds")
    List<Long> getDataScopeDeptIds(Long userId);

    /**
     * 查询SQL提供者
     */
    class SelectSqlProvider {

        /**
         * 获取用户的数据权限列表
         *
         * @return SQL
         */
        @SuppressWarnings("all")
        public static String getDataScopeDeptIds(Long userId) {
            return """
                    <script>
                        select sro.dept_id
                        from brc_sys_user_role sur
                        inner join brc_sys_role_dept sro on sur.role_id = sro.role_id
                        where sro.deleted is null
                            and sur.deleted is null
                            and sur.user_id = #{userId}
                    </script>
                    """;
        }
    }
}
