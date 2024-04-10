/**
  -- 删除模块表
  drop table brc_ma_user;
 */
-- 1、微信小程序用户表
create table brc_ma_user
(
    id                      bigint       primary key comment 'ID',
    nickname                varchar(50)  null comment '昵称',
    avatar                  varchar(255) null comment '头像',
    gender                  char         null comment '性别（0女，1男）',
    country                 varchar(64)  null comment '所在国家',
    province                varchar(64)  null comment '所在省份',
    city                    varchar(64)  null comment '所在城市',
    language                varchar(64)  null comment '用户语言',
    phone                   varchar(20)  null comment '手机号码',
    open_id                 varchar(64)  not null comment 'openid',
    union_id                varchar(64)  null comment 'unionid',
    account                 varchar(50)  null comment '账号',
    birthday                datetime     null comment '生日',
    full_location           varchar(100) null comment '省市区',
    profession              varchar(50)  null comment '职业',
    tag_ids                 json         null comment '标签ID列表',
    group_id                varchar(64)  null comment '用户组',
    qr_scene_str            varchar(64)  null comment '二维码扫码场景',
    geo_latitude            double       null comment '地理位置纬度',
    geo_longitude           double       null comment '地理位置经度',
    geo_precision           double       null comment '地理位置精度',
    session_key             varchar(200) null comment '会话密钥',
    session_created_time    datetime     null comment '会话创建时间',
    remark                  varchar(255) null comment '备注',
    created_time datetime   null comment '创建时间',
    updated_time datetime   null comment '修改时间',
    constraint uk_openid unique (open_id)
) comment '微信小程序用户表';

create index idx_union_id on brc_ma_user (union_id) comment 'unionid';


