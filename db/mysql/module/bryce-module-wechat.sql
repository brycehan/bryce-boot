-- 1、应用表
drop table if exists brc_app;
create table brc_app
(
    id              varchar(36)     not null comment 'ID',
    name            varchar(100)    not null comment '应用名称',
    key             varchar(10)     not null comment '应用key',
    secret          varchar(255)    not null comment '应用密钥',
    tenant_id       varchar(36)     not null comment '租户ID',
    created_user_id  varchar(36)     default null comment '创建者ID',
    created_time     datetime        default null comment '创建时间',
    updated_user_id  varchar(36)     default null comment '修改者ID',
    updated_time     datetime        default null comment '修改时间',
    deleted     tinyint(1)      default '0' comment '删除标识（0：存在，1：删除）',
    primary key (id),
    unique key unique_key(key)
) engine InnoDB default charset utf8mb4 comment '应用表';
-- 2、微信配置表
drop table if exists brc_wechat_config;
create table brc_wechat_config
(
    id              varchar(36)     not null comment 'ID',
    app_id          varchar(100)    not null comment '应用ID',
    app_secret      varchar(255)    not null comment '应用密钥',
    app_type        varchar(10)     default 'mp' comment '应用类型（mp：微信公众号，ma：微信小程序）',
    token           varchar(255)    not null comment '令牌',
    redirect_url    varchar(500)    not null comment '重定向地址',
    tenant_id       varchar(36)     not null comment '租户ID',
    created_user_id  varchar(36)     default null comment '创建者ID',
    created_time     datetime        default null comment '创建时间',
    updated_user_id  varchar(36)     default null comment '修改者ID',
    updated_time     datetime        default null comment '修改时间',
    deleted     tinyint(1)      default '0' comment '删除标识（0：存在，1：删除）',
    primary key (id)
) engine InnoDB default charset utf8mb4 comment '微信配置表';