-- 微信管理菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (6, '微信公众号', 'M', 0, null, null, 'icon-wechat-fill', false, 6, '微信公众号目录', true, null, 1, now(), null, null);
-- 二级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (151, '菜单管理', 'M', 6, 'mp/menu/index', 'mp:menu:page', 'icon-unorderedlist', 0, 1, '公众号菜单管理', true, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (152, '素材管理', 'M', 6, 'mp/messageReplyRule/index', 'mp:messageReplyRule:page', 'icon-menu', 0, 2, null, true, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (153, '自动回复规则', 'M', 6, 'mp/messageReplyRule/index', 'mp:messageReplyRule:page', 'icon-menu', 0, 3, null, true, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (154, '消息模板管理', 'M', 6, 'mp/messageTemplate/index', 'mp:messageTemplate:page', 'icon-menu', 0, 4, null, true, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (155, '带参二维码', 'M', 6, 'mp/qrCode/index', 'mp:qrCode:page', 'icon-menu', 0, 5, null, true, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (156, '粉丝管理', 'M', 6, 'mp/user/index', 'mp:user:page', 'icon-menu', 0, 6, null, true, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (157, '消息管理', 'M', 6, 'mp/message/index', 'mp:message:page', 'icon-menu', 0, 7, null, true, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (158, '模版消息发送记录', 'M', 6, 'mp/templateMessageLog/index', 'mp:templateMessageLog:page', 'icon-menu', 0, 8, null, true, null, 1, now(), null, null);

-- 三级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1511, '微信菜单新增/修改', 'B', 151, null, 'mp:menu:saveOrUpdate', '', false, 1, null, true, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1512, '微信菜单从缓存发布', 'B', 151, null, 'mp:menu:publishByCache', '', false, 2, null, true, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1531, '消息回复规则新增', 'B', 153,null, 'mp:messageReplyRule:save', '', '0', 1, null, true, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1532, '消息回复规则修改', 'B', 153, null, 'mp:messageReplyRule:update', '', '0', 2, null, true, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1533, '消息回复规则删除', 'B', 153, null, 'mp:messageReplyRule:delete', '', '0', 3, null, true, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1534, '消息回复规则详情', 'B', 153, null, 'mp:messageReplyRule:info', '', '0', 4, null, true, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1551, '带参二维码新增', 'B', 155, null, 'mp:qrCode:save', '', '0', 1, null, true, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1552, '带参二维码修改', 'B', 155, null, 'mp:qrCode:update', '', '0', 2, null, true, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1553, '带参二维码删除', 'B', 155, null, 'mp:qrCode:delete', '', '0', 3, null, true, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1554, '带参二维码详情', 'B', 155, null, 'mp:qrCode:info', '', '0', 4, null, true, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1561, '粉丝新增', 'B', 156, null, 'mp:user:save', '', '0', 1, null, true, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1562, '粉丝修改', 'B', 156, null, 'mp:user:update', '', '0', 2, null, true, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1563, '粉丝删除', 'B', 156, null, 'mp:user:delete', '', '0', 3, null, true, null, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1564, '粉丝详情', 'B', 156, null, 'mp:user:info', '', '0', 4, null, true, null, 1, now(), null, null);
-- 1、微信公众号粉丝表
create table brc_mp_user
(
    open_id         varchar(50)                        primary key comment 'openid',
    nickname        varchar(50)                        null comment '昵称',
    head_img_url    varchar(255)                       null comment '头像',
    sex             int                                null comment '性别（0未知，1男，2女）',
    province        varchar(20)                        null comment '省份',
    city            varchar(20)                        null comment '城市',
    language        varchar(10)                        null comment '语言',
    phone           varchar(20)                        null comment '手机号',
    union_id        varchar(50)                        null comment 'unionid',
    tag_ids         json                               null comment '标签ID列表',
    subscribe       tinyint  default 1                 null comment '是否关注',
    subscribe_time  datetime                           null comment '关注时间',
    subscribe_scene varchar(50)                        null comment '关注场景',
    qr_scene_str    varchar(64)                        null comment '扫码场景值',
    remark          varchar(255)                       null comment '备注',
    created_time    datetime                           null comment '创建时间',
    updated_time    datetime                           null comment '修改时间'
) comment '微信公众号粉丝表';

create index idx_union_id on brc_mp_user (union_id) comment 'unionid';

-- 2、微信公众号消息回复规则表
create table brc_mp_msg_reply_rule
(
    id                  bigint                      primary key comment 'ID',
    name                varchar(50)                 not null comment '名称',
    match_value         varchar(200)                not null comment '匹配的关键词、事件等',
    exact_match         tinyint  default 0          null comment '是否精确匹配（1：精确，1：不精确）',
    reply_type          varchar(20) default '1'     null comment '回复消息类型',
    reply_content       varchar(1000)               not null comment '回复消息内容',
    status              tinyint default 1           null comment '状态（0：停用，1：正常）',
    effect_time_start   time  default '00:00:00'    null comment '生效起始时间',
    effect_time_end     time  default '23:59:59'    null comment '生效结束时间',
    priority            int   default 0             null comment '规则优先级',
    remark              varchar(500)                not null comment '备注',
    deleted             datetime                    null comment '删除标识',
    created_user_id     bigint                      null comment '创建者ID',
    created_time        datetime                    null comment '创建时间',
    updated_user_id     bigint                      null comment '修改者ID',
    updated_time        datetime                    null comment '修改时间'
) comment '微信公众号消息回复规则表';

-- 3、微信公众号消息模板表
create table brc_mp_msg_template
(
    id           bigint      primary key comment 'ID',
    template_id  varchar(100) not null comment '公众号模板ID',
    name         varchar(50)  null comment '模版名称',
    title        varchar(20)  null comment '标题',
    content      text         null comment '模板内容',
    data         json         null comment '消息内容',
    url          varchar(255) null comment '链接',
    mini_program json         null comment '小程序信息',
    status       tinyint      not null comment '是否有效',
    updated_time datetime     null comment '修改时间',
    constraint uk_name unique (name) comment '模板名称'
) comment '微信公众号消息模板表';

create index idx_status on brc_mp_msg_template (status) comment '模板状态';

-- 4、微信公众号模版消息发送记录表
create table brc_mp_template_msg_log
(
    id           bigint       primary key comment 'ID',
    to_user      varchar(50)  null comment '用户openid',
    template_id  varchar(50)  null comment 'templateid',
    data         json         null comment '消息数据',
    url          varchar(255) null comment '消息链接',
    mini_program json         null comment '小程序信息',
    send_time    datetime     null comment '发送时间',
    send_result  varchar(255) null comment '发送结果'
) comment '微信公众号模版消息发送记录表';

-- 5、微信公众号消息表
create table brc_mp_msg
(
    id           bigint      primary key comment 'ID',
    open_id      varchar(50) not null comment 'openid',
    in_out       tinyint     null comment '消息方向（1：in，0：out）',
    message_type varchar(25) null comment '消息类型',
    content      json        null comment '消息内容',
    created_user_id     bigint                      null comment '创建者ID',
    created_time        datetime                    null comment '创建时间',
    updated_user_id     bigint                      null comment '修改者ID',
    updated_time        datetime                    null comment '修改时间'
) comment '微信公众号消息';

-- 6、微信公众号带参二维码表
create table brc_mp_qr_code
(
    id           bigint       primary key comment 'ID',
    is_temporary tinyint      null comment '是否为临时二维码',
    scene_str    varchar(64)  null comment '场景值ID',
    ticket       varchar(255) null comment '二维码ticket',
    url          varchar(255) null comment '二维码图片解析后的地址',
    expire_time  datetime     null comment '该二维码失效时间',
    create_time  datetime     null comment '该二维码创建时间'
) comment '微信公众号带参二维码表';

