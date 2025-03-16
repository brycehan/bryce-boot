package com.brycehan.boot.api.system.dto;


import com.brycehan.boot.common.enums.StatusType;
import lombok.Data;

import java.util.Set;

/**
 * 流程用户 Dto
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Data
public class BpmUserDto {

    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 帐号状态
     *
     * 枚举 {@link StatusType}
     */
    private Integer status;

    /**
     * 部门ID
     */
    private Long deptId;
    /**
     * 岗位编号数组
     */
    private Set<Long> postIds;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 用户头像
     */
    private String avatar;

}
