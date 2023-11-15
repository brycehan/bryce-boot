-- 微信管理菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (6, '微信管理', 'M', 0, null, null, 'icon-wechat-fill', false, 6, '微信公众号目录', true, 1, false, 1, now(), null, null);
-- 二级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (151, '应用配置', 'M', 6, 'wechat/app/index', 'wechat:app:page', 'icon-pic-center', false, 1, '公众号配置菜单', true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (152, '菜单管理', 'M', 6, 'wechat/menu/index', 'wechat:menu:page', 'icon-unorderedlist', false, 1, '公众号菜单管理菜单', true, 1, false, 1, now(), null, null);
-- 三级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1511, '微信应用新增', 'B', 151, null, 'wechat:app:save', '', false, 1, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1512, '微信应用修改', 'B', 151, null, 'wechat:app:update', '', false, 2, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1513, '微信应用删除', 'B', 151, null, 'wechat:app:delete', '', false, 3, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1514, '微信应用详情', 'B', 151, null, 'wechat:app:info', '', false, 4, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1515, '微信应用导出', 'B', 151, null, 'wechat:app:export', '', false, 5, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1516, '微信应用导入', 'B', 151, null, 'wechat:app:import', '', false, 6, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1517, '微信应用列表', 'B', 151, null, 'wechat:app:list', '', false, 7, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1521, '微信菜单新增/修改', 'B', 152, null, 'wechat:menu:saveOrUpdate', '', false, 1, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1522, '微信应用删除', 'B', 152, null, 'wechat:menu:delete', '', false, 2, null, true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1523, '微信应用详情', 'B', 152, null, 'wechat:menu:info', '', false, 3, null, true, 1, false, 1, now(), null, null);

-- 1、微信应用表
drop table if exists brc_wechat_app;
create table brc_wechat_app
(
    id              bigint                   primary key comment 'ID',
    name            varchar(50)              not null comment '名称',
    app_id          varchar(255)             not null comment '应用ID',
    app_secret      varchar(255)             not null comment '应用密钥',
    type            varchar(10) default 'mp' not null comment '类型（mp：微信公众号，ma：微信小程序）',
    token           varchar(100)             null comment '令牌',
    redirect_url    varchar(500)             null comment '重定向地址',
    tenant_id       bigint                   null comment '租户ID',
    status          tinyint     default 1    not null comment '状态（0：停用，1：正常）',
    remark          varchar(500)             null comment '备注',
    version         int                      not null comment '版本号',
    deleted         tinyint     default 0    not null comment '删除标识（0：存在，1：已删除）',
    created_user_id bigint                   null comment '创建者ID',
    created_time    datetime                 null comment '创建时间',
    updated_user_id bigint                   null comment '修改者ID',
    updated_time    datetime                 null comment '修改时间'
) engine InnoDB comment '微信应用表';

