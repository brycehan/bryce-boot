package com.brycehan.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.brycehan.boot.system.entity.po.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * 系统部门Mapper接口
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 部门列表
     *
     * @param params 参数
     * @return 部门列表
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "list")
    List<SysDept> list(Map<String, Object> params);

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
        public static String list(Map<String, Object> params) {
            return """
                    <script>
                        select sd.id, sd.name, sd.code, sd.parent_id, sd.ancestor,
                            sd.leader_user_id, sd.contact_number, sd.email, sd.sort, sd.status,
                            sd.deleted, sd.created_user_id, sd.created_time,
                            sd.updated_user_id, sd.updated_time
                        from brc_sys_dept sd
                        where sd.deleted is null
                            <if test="name != null and name != ''">
                                and sd.name like concat('%', #{params.name}, '%')
                            </if>
                            <if test="status != null">
                                and sd.status = #{status}
                            </if>
                        order by sd.parent_id, sd.sort asc
                    </script>
                    """;
        }
    }
}
