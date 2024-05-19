-- 微信管理菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (6, '微信公众号', 'M', 0, null, null, 'icon-wechat-fill', false, 6, '微信公众号目录', true, 1, false, 1, now(), null, null);
-- 二级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (151, '菜单管理', 'M', 6, 'mp/menu/index', 'mp:menu:page', 'icon-unorderedlist', 0, 1, '公众号菜单管理', true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (152, '素材管理', 'M', 6, 'mp/messageReplyRule/index', 'mp:messageReplyRule:page', 'icon-menu', 0, 2, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (153, '自动回复规则', 'M', 6, 'mp/messageReplyRule/index', 'mp:messageReplyRule:page', 'icon-menu', 0, 3, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (154, '消息模板管理', 'M', 6, 'mp/messageTemplate/index', 'mp:messageTemplate:page', 'icon-menu', 0, 4, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (155, '带参二维码', 'M', 6, 'mp/qrCode/index', 'mp:qrCode:page', 'icon-menu', 0, 5, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (156, '粉丝管理', 'M', 6, 'mp/user/index', 'mp:user:page', 'icon-menu', 0, 6, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (157, '消息管理', 'M', 6, 'mp/message/index', 'mp:message:page', 'icon-menu', 0, 7, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (158, '模版消息发送记录', 'M', 6, 'mp/templateMessageLog/index', 'mp:templateMessageLog:page', 'icon-menu', 0, 8, null, true, 1, false, 1, now(), null, null);

-- 三级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1511, '微信菜单新增/修改', 'B', 151, null, 'mp:menu:saveOrUpdate', '', false, 1, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1512, '微信菜单从缓存发布', 'B', 151, null, 'mp:menu:publishByCache', '', false, 2, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1531, '消息回复规则新增', 'B', 153,null, 'mp:messageReplyRule:save', '', '0', 1, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1532, '消息回复规则修改', 'B', 153, null, 'mp:messageReplyRule:update', '', '0', 2, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1533, '消息回复规则删除', 'B', 153, null, 'mp:messageReplyRule:delete', '', '0', 3, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1534, '消息回复规则详情', 'B', 153, null, 'mp:messageReplyRule:info', '', '0', 4, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1551, '带参二维码新增', 'B', 155, null, 'mp:qrCode:save', '', '0', 1, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1552, '带参二维码修改', 'B', 155, null, 'mp:qrCode:update', '', '0', 2, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1553, '带参二维码删除', 'B', 155, null, 'mp:qrCode:delete', '', '0', 3, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1554, '带参二维码详情', 'B', 155, null, 'mp:qrCode:info', '', '0', 4, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1561, '粉丝新增', 'B', 156, null, 'mp:user:save', '', '0', 1, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1562, '粉丝修改', 'B', 156, null, 'mp:user:update', '', '0', 2, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1563, '粉丝删除', 'B', 156, null, 'mp:user:delete', '', '0', 3, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1564, '粉丝详情', 'B', 156, null, 'mp:user:info', '', '0', 4, null, true, 1, false, 1, now(), null, null);

-- 1、微信公众号粉丝表
create table brc_mp_user
(
    open_id         varchar(50) not null primary key,
    nickname        varchar(50),
    head_img_url    varchar(255),
    sex             integer,
    province        varchar(20),
    city            varchar(20),
    language        varchar(10),
    phone           varchar(20),
    union_id        varchar(50),
    tag_ids         json,
    subscribe       boolean default true,
    subscribe_time  datetime,
    subscribe_scene varchar(50),
    qr_scene_str    varchar(64),
    remark          varchar(255),
    created_time    datetime,
    updated_time    datetime
);

create index idx_brc_mp_user_union_id on brc_mp_user (union_id);

comment on table brc_mp_user is '微信公众号粉丝表';

comment on column   brc_mp_user.open_id         is 'openid';
comment on column   brc_mp_user.nickname        is '昵称';
comment on column   brc_mp_user.head_img_url    is '头像';
comment on column   brc_mp_user.sex             is '性别（0未知，1男，2女）';
comment on column   brc_mp_user.province        is '省份';
comment on column   brc_mp_user.city            is '城市';
comment on column   brc_mp_user.language        is '语言';
comment on column   brc_mp_user.phone           is '手机号';
comment on column   brc_mp_user.union_id        is 'unionid';
comment on column   brc_mp_user.tag_ids         is '标签ID列表';
comment on column   brc_mp_user.subscribe       is '是否关注';
comment on column   brc_mp_user.subscribe_time  is '关注时间';
comment on column   brc_mp_user.subscribe_scene is '关注场景';
comment on column   brc_mp_user.qr_scene_str    is '扫码场景值';
comment on column   brc_mp_user.remark          is '备注';
comment on column   brc_mp_user.created_time    is '创建时间';
comment on column   brc_mp_user.updated_time    is '修改时间';

-- 2、微信公众号消息回复规则表
create table brc_mp_msg_reply_rule
(
    id                bigint                    not null primary key,
    name              varchar(50)               not null,
    match_value       varchar(200)              not null,
    exact_match       boolean     default false,
    reply_type        varchar(20) default '1'::character varying,
    reply_content     varchar(1000)             not null,
    status            boolean     default true,
    effect_time_start time        default '00:00:00'::time without time zone,
    effect_time_end   time        default '23:59:59'::time without time zone,
    priority          integer     default 0,
    remark            varchar(500)              not null,
    version           integer                   not null,
    deleted           boolean     default false not null,
    created_user_id   bigint,
    created_time      datetime,
    updated_user_id   bigint,
    updated_time      datetime
);

comment on table brc_mp_msg_reply_rule is '微信公众号消息回复规则表';

comment on column brc_mp_msg_reply_rule.id                is 'ID';
comment on column brc_mp_msg_reply_rule.name              is '名称';
comment on column brc_mp_msg_reply_rule.match_value       is '匹配的关键词、事件等';
comment on column brc_mp_msg_reply_rule.exact_match       is '是否精确匹配（1：精确，1：不精确）';
comment on column brc_mp_msg_reply_rule.reply_type        is '回复消息类型';
comment on column brc_mp_msg_reply_rule.reply_content     is '回复消息内容';
comment on column brc_mp_msg_reply_rule.status            is '状态（0：停用，1：正常）';
comment on column brc_mp_msg_reply_rule.effect_time_start is '生效起始时间';
comment on column brc_mp_msg_reply_rule.effect_time_end   is '生效结束时间';
comment on column brc_mp_msg_reply_rule.priority          is '规则优先级';
comment on column brc_mp_msg_reply_rule.remark            is '备注';
comment on column brc_mp_msg_reply_rule.version           is '版本号';
comment on column brc_mp_msg_reply_rule.deleted           is '删除标识（0：存在，1：已删除）';
comment on column brc_mp_msg_reply_rule.created_user_id   is '创建者ID';
comment on column brc_mp_msg_reply_rule.created_time      is '创建时间';
comment on column brc_mp_msg_reply_rule.updated_user_id   is '修改者ID';
comment on column brc_mp_msg_reply_rule.updated_time      is '修改时间';

-- 3、微信公众号消息模板表
create table brc_mp_msg_template
(
    id           bigint       not null primary key,
    template_id  varchar(100) not null,
    name         varchar(50),
    title        varchar(20),
    content      text,
    data         json,
    url          varchar(255),
    mini_program json,
    status       boolean      not null,
    updated_time datetime,
    constraint uk_name unique (name)
);

create index idx_brc_mp_msg_template_status on brc_mp_msg_template (status);

comment on table brc_mp_msg_template is '微信公众号消息模板表';

comment on column  brc_mp_msg_template.id           is 'ID';
comment on column  brc_mp_msg_template.template_id  is '公众号模板ID';
comment on column  brc_mp_msg_template.name         is '模版名称';
comment on column  brc_mp_msg_template.title        is '标题';
comment on column  brc_mp_msg_template.content      is '模板内容';
comment on column  brc_mp_msg_template.data         is '消息内容';
comment on column  brc_mp_msg_template.url          is '链接';
comment on column  brc_mp_msg_template.mini_program is '小程序信息';
comment on column  brc_mp_msg_template.status       is '是否有效';
comment on column  brc_mp_msg_template.updated_time is '修改时间';

-- 4、微信公众号模版消息发送记录表
create table brc_mp_template_msg_log
(
    id           bigint not null primary key,
    to_user      varchar(50),
    template_id  varchar(50),
    data         json,
    url          varchar(255),
    mini_program json,
    send_time    datetime,
    send_result  varchar(255)
);

comment on table brc_mp_template_msg_log is '微信公众号模版消息发送记录表';

comment on column brc_mp_template_msg_log.id           is 'ID';
comment on column brc_mp_template_msg_log.to_user      is '用户openid';
comment on column brc_mp_template_msg_log.template_id  is 'templateid';
comment on column brc_mp_template_msg_log.data         is '消息数据';
comment on column brc_mp_template_msg_log.url          is '消息链接';
comment on column brc_mp_template_msg_log.mini_program is '小程序信息';
comment on column brc_mp_template_msg_log.send_time    is '发送时间';
comment on column brc_mp_template_msg_log.send_result  is '发送结果';

-- 5、微信公众号消息表
create table brc_mp_msg
(
    id              bigint      not null primary key,
    open_id         varchar(50) not null,
    in_out          boolean,
    message_type    varchar(25),
    content         json,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime
);

comment on table brc_mp_msg is '微信公众号消息';

comment on column brc_mp_msg.id              is 'ID';
comment on column brc_mp_msg.open_id         is 'openid';
comment on column brc_mp_msg.in_out          is '消息方向（1：in，0：out）';
comment on column brc_mp_msg.message_type    is '消息类型';
comment on column brc_mp_msg.content         is '消息内容';
comment on column brc_mp_msg.created_user_id is '创建者ID';
comment on column brc_mp_msg.created_time    is '创建时间';
comment on column brc_mp_msg.updated_user_id is '修改者ID';
comment on column brc_mp_msg.updated_time    is '修改时间';

-- 6、微信公众号带参二维码表
create table brc_mp_qr_code
(
    id           bigint not null primary key,
    is_temporary boolean,
    scene_str    varchar(64),
    ticket       varchar(255),
    url          varchar(255),
    expire_time  datetime,
    create_time  datetime
);

comment on table brc_mp_qr_code is '微信公众号带参二维码表';

comment on column brc_mp_qr_code.id           is 'ID';
comment on column brc_mp_qr_code.is_temporary is '是否为临时二维码';
comment on column brc_mp_qr_code.scene_str    is '场景值ID';
comment on column brc_mp_qr_code.ticket       is '二维码ticket';
comment on column brc_mp_qr_code.url          is '二维码图片解析后的地址';
comment on column brc_mp_qr_code.expire_time  is '该二维码失效时间';
comment on column brc_mp_qr_code.create_time  is '该二维码创建时间';