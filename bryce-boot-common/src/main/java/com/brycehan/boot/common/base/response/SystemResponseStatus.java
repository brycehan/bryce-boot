package com.brycehan.boot.common.base.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 系统响应状态枚举
 * <br/>
 *
 * @since 2022/5/30
 * @author Bryce Han
 */
@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum SystemResponseStatus implements ResponseStatus {

    // 用户模块
    USER_REGISTER_NOT_ENABLED(6_000_000, "当前系统没有开启注册功能", ResponseType.ERROR),
    USER_REGISTER_EXISTS(6_000_001, "注册{}失败，账号已经存在", ResponseType.ERROR),
    USER_REGISTER_ERROR(6_000_002, "注册失败，请联系管理人员", ResponseType.ERROR),
    USER_USERNAME_EXISTS(6_000_003, "用户账号已经存在", ResponseType.ERROR),
    USER_USERNAME_NOT_EXISTS(6_000_004, "账号{}还未注册", ResponseType.ERROR),
    USER_USERNAME_LOCKED(6_000_005, "用户帐号已被锁定", ResponseType.ERROR),
    USER_USERNAME_DISABLED(6_000_006, "用户账号已停用", ResponseType.ERROR),
    USER_USERNAME_OR_PASSWORD_ERROR(6_000_007, "账号或密码错误", ResponseType.ERROR),
    USER_PASSWORD_FAILED(6_000_008, "用户密码校验失败", ResponseType.ERROR),
    USER_PASSWORD_RETRY_LIMIT_EXCEEDED(6_000_009, "密码输入错误{}次，帐户锁定{}", ResponseType.ERROR),
    USER_PASSWORD_NOT_MATCH(6_000_010, "原密码错误", ResponseType.ERROR),
    USER_PASSWORD_SAME_AS_OLD_ERROR(6_000_011, "新密码不能与旧密码相同", ResponseType.ERROR),
    USER_PASSWORD_CHANGE_ERROR(6_000_012, "修改密码异常，请联系管理员", ResponseType.ERROR),
    USER_IMPORT_LIST_IS_EMPTY(6_000_013, "导入用户数据不能为空！", ResponseType.ERROR),
    USER_IMPORT_INIT_PASSWORD(6_000_014, "初始密码不能为空", ResponseType.ERROR),
    USER_PROFILE_PHONE_EXISTS(6_000_015, "修改用户{}失败，手机号码已存在", ResponseType.ERROR),
    USER_PROFILE_EMAIL_EXISTS(6_000_016, "修改用户{}失败，邮箱已存在", ResponseType.ERROR),
    USER_PROFILE_ALTER_INFO_ERROR(6_000_017, "修改个人信息异常，请联系管理员", ResponseType.ERROR),
    USER_PROFILE_ALTER_AVATAR_ERROR(6_000_018, "修改头像异常，请联系管理员", ResponseType.ERROR),
    USER_COUNT_MAX(6_000_019, "创建用户失败，原因：超过租户最大租户配额({})！", ResponseType.ERROR),
    USER_IS_DISABLE(6_000_020, "名字为【{}】的用户已被禁用", ResponseType.ERROR),
    USER_FORCE_LOGOUT(6_000_021, "管理员强制退出，请重新登录", ResponseType.ERROR),
    USER_BALANCE_INSUFFICIENT(6_000_022, "账户余额不足，还差{}元", ResponseType.ERROR),

    // 角色模块
    ROLE_NOT_EXISTS(6_001_000, "角色不存在", ResponseType.ERROR),
    ROLE_NAME_DUPLICATE(6_001_001, "已经存在名为【{}】的角色", ResponseType.ERROR),
    ROLE_CODE_DUPLICATE(6_001_002, "已经存在标识为【{}】的角色", ResponseType.ERROR),
    ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE(6_001_003, "不能操作类型为系统内置的角色", ResponseType.ERROR),
    ROLE_IS_DISABLE(6_001_004, "名字为【{}】的角色已被禁用", ResponseType.ERROR),
    ROLE_ADMIN_CODE_ERROR(6_001_005, "标识【{}】不能使用", ResponseType.ERROR),

    // 菜单模块
    MENU_NAME_DUPLICATE(6_002_000, "已经存在该名字的菜单", ResponseType.ERROR),
    MENU_PARENT_NOT_EXISTS(6_002_001, "父菜单不存在", ResponseType.ERROR),
    MENU_PARENT_ERROR(6_002_002, "不能设置自己为父菜单", ResponseType.ERROR),
    MENU_NOT_EXISTS(6_002_003, "菜单不存在", ResponseType.ERROR),
    MENU_EXISTS_CHILDREN(6_002_004, "存在子菜单，无法删除", ResponseType.ERROR),
    MENU_PARENT_NOT_DIR_OR_MENU(6_002_005, "父菜单的类型必须是目录或者菜单", ResponseType.ERROR),

    // 部门模块
    DEPT_NAME_DUPLICATE(6_003_000, "已经存在该名字的部门", ResponseType.ERROR),
    DEPT_PARENT_NOT_EXITS(6_003_001,"父级部门不存在", ResponseType.ERROR),
    DEPT_NOT_FOUND(6_003_002, "当前部门不存在", ResponseType.ERROR),
    DEPT_EXITS_CHILDREN(6_003_003, "存在下级部门，不允许删除", ResponseType.WARN),
    DEPT_PARENT_ERROR(6_003_004, "不能设置自己为父部门", ResponseType.WARN),
    DEPT_NOT_ENABLE(6_003_006, "部门({})不处于开启状态，不允许选择", ResponseType.ERROR),
    DEPT_PARENT_IS_CHILD(6_003_007, "不能设置自己的子部门为父部门", ResponseType.ERROR),
    
    // 岗位模块
    POST_NOT_FOUND(6_004_00, "当前岗位不存在", ResponseType.ERROR),
    POST_NOT_ENABLE(6_004_01, "岗位({}) 不处于开启状态，不允许选择", ResponseType.ERROR),
    POST_NAME_DUPLICATE(6_004_02, "已经存在该名字的岗位", ResponseType.ERROR),
    POST_CODE_DUPLICATE(6_004_03, "已经存在该标识的岗位", ResponseType.ERROR),

    // 字典类型
    DICT_TYPE_NOT_EXISTS(6_005_001, "当前字典类型不存在", ResponseType.ERROR),
    DICT_TYPE_NOT_ENABLE(6_005_002, "字典类型不处于开启状态，不允许选择", ResponseType.ERROR),
    DICT_TYPE_NAME_DUPLICATE(6_005_003, "已经存在该名字的字典类型", ResponseType.ERROR),
    DICT_TYPE_TYPE_DUPLICATE(6_005_004, "已经存在该类型的字典类型", ResponseType.ERROR),
    DICT_TYPE_HAS_CHILDREN(6_005_005, "无法删除，该字典类型还有字典数据", ResponseType.ERROR),

    // 字典数据
    DICT_DATA_NOT_EXISTS(6_006_001, "当前字典数据不存在", ResponseType.ERROR),
    DICT_DATA_NOT_ENABLE(6_006_002, "字典数据({})不处于开启状态，不允许选择", ResponseType.ERROR),
    DICT_DATA_VALUE_DUPLICATE(6_006_003, "已经存在该值的字典数据", ResponseType.ERROR),

    // 通知公告
    NOTICE_NOT_FOUND(6_007_001, "当前通知公告不存在", ResponseType.ERROR),
    ;

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 状态值
     */
    private final String value;

    /**
     * 响应类型
     */
    private final ResponseType type;
}
