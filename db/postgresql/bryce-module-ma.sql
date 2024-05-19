/**
  -- 删除模块表
  drop table brc_ma_user;
 */
-- 1、微信小程序用户表
create table brc_ma_user
(
    id                   bigint      not null primary key,
    nickname             varchar(50),
    avatar               varchar(255),
    gender               char,
    country              varchar(64),
    province             varchar(64),
    city                 varchar(64),
    county               varchar(64),
    language             varchar(64),
    phone                varchar(20),
    open_id              varchar(64) not null,
    union_id             varchar(64),
    account              varchar(50),
    birthday             timestamp,
    profession           varchar(50),
    tag_ids              json,
    group_id             varchar(64),
    qr_scene_str         varchar(64),
    geo_latitude         double precision,
    geo_longitude        double precision,
    geo_precision        double precision,
    session_key          varchar(200),
    session_created_time timestamp,
    remark               varchar(255),
    created_time         timestamp,
    updated_time         timestamp,
    constraint uk_openid unique (open_id)
);

create index idx_union_id on brc_ma_user (union_id);

comment on table brc_ma_user                       is '微信小程序用户表';
comment on column brc_ma_user.id                   is 'ID';
comment on column brc_ma_user.nickname             is '昵称';
comment on column brc_ma_user.avatar               is '头像';
comment on column brc_ma_user.gender               is '性别（0女，1男）';
comment on column brc_ma_user.country              is '所在国家';
comment on column brc_ma_user.province             is '省份编码';
comment on column brc_ma_user.city                 is '城市编码';
comment on column brc_ma_user.county               is '区/县编码';
comment on column brc_ma_user.language             is '用户语言';
comment on column brc_ma_user.phone                is '手机号码';
comment on column brc_ma_user.open_id              is 'openid';
comment on column brc_ma_user.union_id             is 'unionid';
comment on column brc_ma_user.account              is '账号';
comment on column brc_ma_user.birthday             is '生日';
comment on column brc_ma_user.profession           is '职业';
comment on column brc_ma_user.tag_ids              is '标签ID列表';
comment on column brc_ma_user.group_id             is '用户组';
comment on column brc_ma_user.qr_scene_str         is '二维码扫码场景';
comment on column brc_ma_user.geo_latitude         is '地理位置纬度';
comment on column brc_ma_user.geo_longitude        is '地理位置经度';
comment on column brc_ma_user.geo_precision        is '地理位置精度';
comment on column brc_ma_user.session_key          is '会话密钥';
comment on column brc_ma_user.session_created_time is '会话创建时间';
comment on column brc_ma_user.remark               is '备注';
comment on column brc_ma_user.created_time         is '创建时间';
comment on column brc_ma_user.updated_time         is '修改时间';



