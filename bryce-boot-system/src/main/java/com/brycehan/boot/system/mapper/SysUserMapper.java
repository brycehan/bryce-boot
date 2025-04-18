package com.brycehan.boot.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.system.entity.po.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * 系统用户Mapper接口
 *
 * @since 2022/5/08
 * @author Bryce Han
 */
@Mapper
public interface SysUserMapper extends BryceBaseMapper<SysUser> {

    default SysUser getByUsername(String username) {
        return selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username), false);
    }

    default SysUser getByPhone(String phone) {
        return selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhone, phone), false);
    }

    default SysUser getByEmail(String email) {
        return selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getEmail, email), false);
    }

    @SelectProvider(type = SelectSqlProvider.class, method = "list")
    List<SysUser> list(Map<String, Object> params);

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
                        select su.id, su.username, su.password, su.nickname, su.avatar,
                            su.gender, su.type, su.phone, su.email, su.birthday, 
                            su.profession, su.sort, su.dept_id, su.status, su.remark,
                            su.account_non_locked, su.last_login_ip, su.last_login_time, su.deleted, su.created_user_id,
                            su.created_time, su.updated_user_id, su.updated_time
                        from brc_sys_user su
                        where su.deleted is null
                            <if test="username != null and username.trim() != ''">
                                and su.username like concat('%', #{username}, '%')
                            </if>
                            <if test="phone != null and phone.trim() != ''">
                                and su.phone like concat('%', #{phone}, '%')
                            </if>
                            <if test="gender != null and gender.trim() != ''">
                                and su.gender = #{gender}
                            </if>
                            <if test="type != null">
                                and su.type = #{type}
                            </if>
                            <if test="deptId != null">
                                and su.dept_id = #{deptId}
                            </if>
                            <if test="status != null">
                                and su.status = #{status}
                            </if>
                            <choose>
                                <when test="createdTimeStart != null and createdTimeEnd != null">
                                    and su.created_time <![CDATA[ >= ]]> #{createdTimeStart}
                                    and su.created_time <![CDATA[ <= ]]> #{createdTimeEnd}
                                </when>
                                <when test="createdTimeStart = null and createdTimeEnd != null">
                                    and su.created_time <![CDATA[ <= ]]> #{createdTimeEnd}
                                </when>
                                <when test="createdTimeStart != null and createdTimeEnd = null">
                                    and su.created_time <![CDATA[ >= ]]> #{createdTimeStart}
                                </when>
                            </choose>
                    </script>
                    """;
        }
    }
}
