-- 初始化-菜单数据
-- 一级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (6, '流程管理', 'C', 0, null, null, 'icon-block', 0, 6, '流程管理目录', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (7, '审批中心', 'C', 0, null, null, 'icon-cloud-server', 0, 7, '审批中心目录', 1, 1, null, 1, now(), 1, now());
-- 二级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (161, '流程模型', 'M', 6, 'bpm/model/index', 'bpm:model:page', 'icon-branches', 0, 1, '流程模型菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (162, '流程表单', 'M', 6, 'bpm/form/index', 'bpm:form:page', 'icon-file', 0, 2, '流程表单菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (163, '流程分类', 'M', 6, 'bpm/category/index', 'bpm:deployment:page', 'icon-sever', 0, 3, '部署管理菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (164, '用户分组', 'M', 2, 'bpm/userGroup/index', 'bpm:userGroup:page', 'icon-menu', 0, 0, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (165, '流程实例', 'M', 6, 'bpm/instance/index', 'bpm:define:page', 'icon-file-copy', 0, 4, '流程实例菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (166, '流程任务', 'M', 6, 'bpm/task/index', 'bpm:task:page', 'icon-carryout', 0, 5, '流程任务菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (171, '发起流程', 'M', 7, 'bpm/startProcess/index', 'bpm:startProcess:page', 'icon-subnode', 0, 1, '发起流程菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (172, '我的流程', 'M', 7, 'bpm/mineProcess/index', 'bpm:mineProcess:page', 'icon-solution', 0, 2, '我的流程菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (173, '待办任务', 'M', 7, 'bpm/waitTask/index', 'bpm:waitTask:page', 'icon-edit', 0, 3, '待办流程菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (174, '已办任务', 'M', 7, 'bpm/completedTask/index', 'bpm:completedTask:page', 'icon-fileprotect', 0, 4, '已办流程菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (175, '抄送我的', 'M', 7, 'bpm/copyMe/index', 'bpm:copyMe:page', 'icon-snippets', 0, 5, '抄送我的菜单', 1, 1, null, 1, now(), 1, now());

-- 三级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1610, '流程定义信息新增', 'B', 161,null, 'bpm:processDefinitionInfo:save', null, 0, 1, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1611, '流程定义信息修改', 'B', 161, null, 'bpm:processDefinitionInfo:update', null, 0, 2, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1612, '流程定义信息删除', 'B', 161, null, 'bpm:processDefinitionInfo:delete', null, 0, 3, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1613, '流程定义信息详情', 'B', 161, null, 'bpm:processDefinitionInfo:info', null, 0, 4, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1614, '流程定义信息导出', 'B', 161, null, 'bpm:processDefinitionInfo:export', null, 0, 5, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1615, '流程定义信息导入', 'B', 161, null, 'bpm:processDefinitionInfo:import', null, 0, 6, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1620, '流程分类新增', 'B', 162,null, 'bpm:category:save', '', 0, 1, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1621, '流程分类修改', 'B', 162, null, 'bpm:category:update', '', 0, 2, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1622, '流程分类删除', 'B', 162, null, 'bpm:category:delete', '', 0, 3, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1623, '流程分类详情', 'B', 162, null, 'bpm:category:info', '', 0, 4, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1624, '流程分类导出', 'B', 162, null, 'bpm:category:export', '', 0, 5, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1625, '流程分类导入', 'B', 162, null, 'bpm:category:import', '', 0, 6, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1630, '表单定义新增', 'B', 163,null, 'bpm:form:save', '', 0, 1, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1631, '表单定义修改', 'B', 163, null, 'bpm:form:update', '', 0, 2, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1632, '表单定义删除', 'B', 163, null, 'bpm:form:delete', '', 0, 3, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1633, '表单定义详情', 'B', 163, null, 'bpm:form:info', '', 0, 4, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1634, '表单定义导出', 'B', 163, null, 'bpm:form:export', '', 0, 5, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1635, '表单定义导入', 'B', 163, null, 'bpm:form:import', '', 0, 6, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1640, '用户组新增', 'B', 164, null, 'bpm:userGroup:save', null, 0, 1, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1641, '用户组修改', 'B', 164, null, 'bpm:userGroup:update', null, 0, 2, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1642, '用户组删除', 'B', 164, null, 'bpm:userGroup:delete', null, 0, 3, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1643, '用户组详情', 'B', 164, null, 'bpm:userGroup:info', null, 0, 4, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1644, '用户组导出', 'B', 164, null, 'bpm:userGroup:export', null, 0, 5, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1645, '用户组导入', 'B', 164, null, 'bpm:userGroup:import', null, 0, 6, null, 1, 1, null, 1, now(), 1, now());
-- 1、流程分类表
create table brc_bpm_category
(
    id              bigint primary key        comment 'ID',
    name            varchar(50)          null comment '分类名称',
    code            varchar(30)          null comment '分类标志',
    description     varchar(300)         null comment '分类描述',
    sort            int        default 0 null comment '显示顺序',
    status          tinyint    default 1 null comment '状态（0：停用，1：正常）',
    deleted         datetime             null comment '删除标识',
    created_user_id bigint               null comment '创建者ID',
    created_time    datetime             null comment '创建时间',
    updated_user_id bigint               null comment '修改者ID',
    updated_time    datetime             null comment '修改时间',
    tenant_id   bigint default 0     not null comment '租户编号'
) comment '流程分类表';

-- 添加数据
INSERT INTO brc_bpm_category (id, name, code, description, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time, tenant_id) VALUES (1, '人资部门', 'humanResourcesDepartment', '', 1, 1, null, 1, now(), 1, now(), 0);
INSERT INTO brc_bpm_category (id, name, code, description, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time, tenant_id) VALUES (2, '营销部门', 'marketingDepartment', '', 2, 1, null, 1, now(), 1, now(), 0);
INSERT INTO brc_bpm_category (id, name, code, description, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time, tenant_id) VALUES (3, '通用流程', 'commonFlow', '', 3, 1, null, 1, now(), 1, now(), 0);

-- 2、流程表单表
create table brc_bpm_form
(
    id              bigint primary key comment '编号',
    name            varchar(64)        not null comment '表单名称',
    status          tinyint default 1      null comment '状态（0：停用，1：正常）',
    conf            varchar(1000)      not null comment '表单的配置',
    fields          varchar(5000)      not null comment '表单项的数组',
    remark          varchar(255)           null comment '备注',
    deleted         datetime               null comment '删除标识',
    created_user_id bigint                 null comment '创建者ID',
    created_time    datetime               null comment '创建时间',
    updated_user_id bigint                 null comment '修改者ID',
    updated_time    datetime               null comment '修改时间',
    tenant_id       bigint default 0   not null comment '租户编号'
) comment '表单定义表';

-- 3、流程定义信息表
create table brc_bpm_process_definition_info
(
    id                           bigint primary key           comment '编号',
    process_definition_id        varchar(64)                  not null comment '流程定义的编号',
    model_id                     varchar(64)                  not null comment '流程模型的编号',
    model_type                   varchar(64)                  null comment '流程模型的类型',
    icon                         varchar(255)                 null comment '图标',
    description                  varchar(255)                 null comment '描述',
    form_type                    tinyint                      not null comment '表单类型',
    form_id                      bigint                       null comment '表单编号',
    form_conf                    varchar(1000)                null comment '表单的配置',
    form_fields                  text                         null comment '表单项的数组',
    form_custom_create_path      varchar(255)                 null comment '自定义表单的提交路径',
    form_custom_view_path        varchar(255)                 null comment '自定义表单的查看路径',
    simple_model                 text                         null comment 'Simple设计器模型数据',
    visible                      tinyint                      null comment '可见范围',
    sort                         bigint default 0             null comment '排序',
    start_user_ids               text                         null comment '可发起人',
    manager_user_ids             text                         null comment '可处理人',
    allow_cancel_running_process tinyint                      null comment '允许取消正在运行的流程',
    process_id_rule              text                         null comment '流程编号规则',
    auto_approval_type           int                          null comment '自动审批类型',
    title_setting                varchar(200)                 null comment '标题设置',
    summary_setting              varchar(300)                 null comment '摘要设置',
    deleted         datetime                                  null comment '删除标识',
    created_user_id bigint                                    null comment '创建者ID',
    created_time    datetime                                  null comment '创建时间',
    updated_user_id bigint                                    null comment '修改者ID',
    updated_time    datetime                                  null comment '修改时间',
    tenant_id       bigint default 0                      not null comment '租户编号'
) comment '流程定义信息表';

-- 4、流程表达式
create table brc_bpm_process_expression
(
    id              bigint primary key                    comment '编号',
    name            varchar(64)                           null comment '流程实例的名字',
    status          tinyint                               not null comment '流程实例的状态',
    expression      varchar(5000)                         null comment '表达式',
    created_user_id bigint                                null comment '创建者ID',
    created_time    datetime                              null comment '创建时间',
    updated_user_id bigint                                null comment '修改者ID',
    updated_time    datetime                              null comment '修改时间',
    deleted         datetime                              null comment '删除标识',
    tenant_id       bigint      default 0                 not null comment '租户编号'
) comment '流程表达式';

-- 5、流程实例抄送
create table brc_bpm_process_instance_copy
(
    id                    bigint        primary key            comment '编号',
    start_user_id         bigint                               not null comment '发起流程的用户编号',
    process_instance_name varchar(64)                          null comment '流程实例的名称',
    process_instance_id   varchar(64)                          not null comment '流程实例的编号',
    category              varchar(64)                          null comment '流程分类',
    task_id               varchar(64)                          null comment '任务主键',
    task_name             varchar(64)                          null comment '任务名称',
    user_id               bigint                               not null comment '用户编号（被抄送的用户编号）',
    created_user_id       bigint                               null comment '创建者ID',
    created_time          datetime                             null comment '创建时间',
    updated_user_id       bigint                               null comment '修改者ID',
    updated_time          datetime                             null comment '修改时间',
    deleted               datetime                             null comment '删除标识',
    tenant_id             bigint        default 0              not null comment '租户编号'
) comment '流程抄送表';

-- 6、流程监听器
create table brc_bpm_process_listener
(
    id              bigint          primary key         not null comment '主键 ID',
    name            varchar(255)                        null comment '监听器名字',
    status          int                                 null comment '状态',
    type            varchar(255)                        null comment '监听类型',
    event           varchar(255)                        null comment '监听事件',
    value_type      varchar(255)                        null comment '值类型',
    value           varchar(255)                        null comment '值',
    created_user_id bigint                              null comment '创建者ID',
    created_time    datetime                            null comment '创建时间',
    updated_user_id bigint                              null comment '修改者ID',
    updated_time    datetime                            null comment '修改时间',
    deleted         datetime                            null comment '删除标识',
    tenant_id   bigint              default 0           not null comment '租户编号'
) comment '流程监听器';

-- 7、用户组表
create table brc_bpm_user_group
(
    id              bigint        primary key                comment '编号',
    name            varchar(30)   default ''            null comment '组名',
    description     varchar(255)  default ''            null comment '描述',
    user_ids        text                 null           comment '成员编号数组',
    created_user_id bigint                              null comment '创建者ID',
    created_time    datetime                            null comment '创建时间',
    updated_user_id bigint                              null comment '修改者ID',
    updated_time    datetime                            null comment '修改时间',
    status          tinyint       default 1 null        comment '状态（0：停用，1：正常）',
    deleted         datetime                            null comment '删除标识',
    tenant_id       bigint        default 0             not null comment '租户编号'
) comment '用户组';

-- 8、请假表单
create table brc_bpm_oa_leave
(
    id                   bigint        primary key       comment '请假表单主键',
    user_id              bigint                          not null comment '申请人的用户编号',
    type                 tinyint                         not null comment '请假类型',
    reason               varchar(200)                    not null comment '请假原因',
    start_time           datetime                        not null comment '开始时间',
    end_time             datetime                        not null comment '结束时间',
    days                 tinyint                         not null comment '请假天数',
    result               tinyint                         not null comment '请假结果',
    process_instance_id  varchar(64)                     null comment '流程实例的编号',
    created_user_id      bigint                          null comment '创建者ID',
    created_time         datetime                        null comment '创建时间',
    updated_user_id      bigint                          null comment '修改者ID',
    updated_time         datetime                        null comment '修改时间',
    deleted              datetime                        null comment '删除标识',
    tenant_id            bigint        default 0         not null comment '租户编号'
) comment 'OA 请假申请表';

