create schema if not exists bryce_boot;
-- 1、系统机构表
drop table if exists brc_sys_org;
create table brc_sys_org
(
    id              bigint       not null primary key,
    name            varchar(100) not null,
    code            varchar(30),
    parent_id       bigint       not null,
    ancestor        varchar(255),
    leader          varchar(50),
    contact_number  varchar(20),
    email           varchar(50),
    remark          varchar(500),
    sort            integer default 0,
    status          boolean default true,
    tenant_id       bigint,
    version         integer,
    deleted         boolean,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime
);

comment on table brc_sys_org is '系统机构表';
comment on column brc_sys_org.id is 'ID';
comment on column brc_sys_org.name is '机构名称';
comment on column brc_sys_org.code is '机构编码';
comment on column brc_sys_org.parent_id is '父机构ID';
comment on column brc_sys_org.ancestor is '祖级机构列表';
comment on column brc_sys_org.leader is '负责人';
comment on column brc_sys_org.contact_number is '联系电话';
comment on column brc_sys_org.email is '邮箱';
comment on column brc_sys_org.remark is '备注';
comment on column brc_sys_org.sort is '显示顺序';
comment on column brc_sys_org.status is '状态（0：停用，1：正常）';
comment on column brc_sys_org.tenant_id is '租户ID';
comment on column brc_sys_org.version is '版本号';
comment on column brc_sys_org.deleted is '删除标识（false：存在，true：已删除）';
comment on column brc_sys_org.created_user_id is '创建者ID';
comment on column brc_sys_org.created_time is '创建时间';
comment on column brc_sys_org.updated_user_id is '修改者ID';
comment on column brc_sys_org.updated_time is '修改时间';

create index idx_brc_sys_org_parent_id on brc_sys_org (parent_id);

-- 初始化-系统机构表数据
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (100, 'Bryce科技', null, 0, '0', '韩先生', '15800008001', 'brycehan@163.com', null, 0, true, null, null, false, 1, now(), null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (101, '北京总公司', null, 100, '0,100', '韩先生', '15800008002', 'brycehan@163.com', null, 1, true, null, null, false, 1, now(), null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (102, '济南分公司', null, 100, '0,100', '韩先生', '15800008003', 'brycehan@163.com', null, 2, true, null, null, false, 1, now(), null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (103, '研发部门', null, 101, '0,100,101', '韩先生', '15800008004', 'brycehan@163.com', null, 1, true, null, null, false, 1, now(), null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (104, '市场部门', null, 101, '0,100,101', '韩先生', '15800008005', 'brycehan@163.com', null, 2, true, null, null, false, 1, now(), null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (105, '测试部门', null, 101, '0,100,101', '韩先生', '15800008006', 'brycehan@163.com', null, 3, true, null, null, false, 1, now(), null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (106, '财务部门', null, 101, '0,100,101', '韩先生', '15800008007', 'brycehan@163.com', null, 4, true, null, null, false, 1, now(), null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (107, '运维部门', null, 101, '0,100,101', '韩先生', '15800008008', 'brycehan@163.com', null, 5, true, null, null, false, 1, now(), null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (108, '市场部门', null, 102, '0,100,102', '韩先生', '15800008009', 'brycehan@163.com', null, 1, true, null, null, false, 1, now(), null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (109, '财务部门', null, 102, '0,100,102', '韩先生', '15800008010', 'brycehan@163.com', null, 2, true, null, null, false, 1, now(), null, null);

-- 2、系统用户表
drop table if exists brc_sys_user;
create table brc_sys_user
(
    id                 bigint      not null primary key,
    username           varchar(50) not null,
    password           varchar(255),
    full_name          varchar(50),
    avatar             varchar(100),
    gender             char,
    type               smallint default 0,
    phone              varchar(20),
    email              varchar(50),
    sort               integer  default 0,
    org_id             bigint,
    super_admin        boolean,
    tenant_admin       boolean,
    status             boolean  default true,
    remark             varchar(500),
    account_non_locked boolean  default true,
    last_login_ip      varchar(128),
    last_login_time    datetime,
    tenant_id          bigint,
    version            integer,
    deleted            boolean  default false,
    created_user_id    bigint,
    created_time       datetime,
    updated_user_id    bigint,
    updated_time       datetime,
    constraint ck_gender check (gender in ('M', 'F'))
);

comment on table brc_sys_user is '系统用户表';
comment on column brc_sys_user.id is 'ID';
comment on column brc_sys_user.username is '账号';
comment on column brc_sys_user.password is '密码';
comment on column brc_sys_user.full_name is '姓名';
comment on column brc_sys_user.avatar is '头像地址';
comment on column brc_sys_user.gender is '性别（M：男, F：女，N：未知）';
comment on column brc_sys_user.type is '用户类型（0：系统用户）';
comment on column brc_sys_user.phone is '手机号码';
comment on column brc_sys_user.email is '邮箱';
comment on column brc_sys_user.sort is '显示顺序';
comment on column brc_sys_user.org_id is '部门ID';
comment on column brc_sys_user.super_admin is '超级管理员';
comment on column brc_sys_user.tenant_admin is '租户管理员';
comment on column brc_sys_user.status is '状态（0：停用，1：正常）';
comment on column brc_sys_user.remark is '备注';
comment on column brc_sys_user.account_non_locked is '账号锁定状态（0：锁定，1：正常）';
comment on column brc_sys_user.last_login_ip is '最后登录IP';
comment on column brc_sys_user.last_login_time is '最后登录时间';
comment on column brc_sys_user.tenant_id is '租户ID';
comment on column brc_sys_user.version is '版本号';
comment on column brc_sys_user.deleted is '删除标识（false：存在，true：已删除）';
comment on column brc_sys_user.created_user_id is '创建者ID';
comment on column brc_sys_user.created_time is '创建时间';
comment on column brc_sys_user.updated_user_id is '修改者ID';
comment on column brc_sys_user.updated_time is '修改时间';

-- 初始化-系统用户表数据
INSERT INTO brc_sys_user (id, username, password, full_name, avatar, gender, type, phone, email, sort, org_id, super_admin, tenant_admin, status, remark, account_non_locked, last_login_ip, last_login_time, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, 'admin', '$2a$10$TPs3BLw2Ag9iZ5bxo./GsuR2BRqUz5J2KQh6h2aSvwQ6Vi.3LzQB6', '管理员', null, 'M', 0, '15853155402', 'brycehan@163.com', 0, 103, true, false, true, '超级管理员', true, '127.0.0.1', now(), null, null, false, 1, now(), null, null);
INSERT INTO brc_sys_user (id, username, password, full_name, avatar, gender, type, phone, email, sort, org_id, super_admin, tenant_admin, status, remark, account_non_locked, last_login_ip, last_login_time, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, 'brycehan', '$2a$10$H/0p9EJPQjYAspbCO85QzuDXs4v36TvdWftjx1HJSWhVoSQ85GtHi', 'Bryce Han', null, 'M', 0, '15800008888', 'brycehan7@gmail.com', 0, 105, false, true, true, '租户管理员', true, '127.0.0.1', now(), null, null, false, 1, now(), null, null);

-- 3、系统角色表
drop table if exists brc_sys_role;
create table brc_sys_role
(
    id              bigint      not null primary key,
    name            varchar(50) not null,
    code            varchar(50) not null,
    data_scope      smallint,
    sort            integer default 0,
    status          boolean default true,
    remark          varchar(500),
    org_id          bigint,
    tenant_id       bigint,
    version         integer,
    deleted         boolean default false,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime
);

comment on table brc_sys_role is '系统角色表';
comment on column brc_sys_role.id is 'ID';
comment on column brc_sys_role.name is '角色名称';
comment on column brc_sys_role.code is '角色编码';
comment on column brc_sys_role.data_scope is '数据范围（1：全部数据，2：本机构及以下机构数据，3：本机构数据，4：本人数据，5：自定义数据）';
comment on column brc_sys_role.sort is '显示顺序';
comment on column brc_sys_role.status is '状态（false：停用，true：正常）';
comment on column brc_sys_role.remark is '备注';
comment on column brc_sys_role.org_id is '机构ID';
comment on column brc_sys_role.tenant_id is '租户ID';
comment on column brc_sys_role.version is '版本号';
comment on column brc_sys_role.deleted is '删除标识（false：存在，true：已删除）';
comment on column brc_sys_role.created_user_id is '创建者ID';
comment on column brc_sys_role.created_time is '创建时间';
comment on column brc_sys_role.updated_user_id is '修改者ID';
comment on column brc_sys_role.updated_time is '修改时间';

-- 初始化-系统角色表数据
INSERT INTO brc_sys_role (id, name, code, data_scope, sort, status, remark, org_id, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '管理员', 'admin', 2, 0, true, '管理员', null, null, null, false, 1, now(), null, null);
INSERT INTO brc_sys_role (id, name, code, data_scope, sort, status, remark, org_id, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '默认角色', 'default', 2, 0, true, '默认角色', null, null, null, false, 1, now(), null, null);

-- 4、系统用户角色关系表
drop table if exists brc_sys_user_role;
create table brc_sys_user_role
(
    id              bigint not null primary key,
    user_id         bigint not null,
    role_id         bigint not null,
    version         integer,
    deleted         boolean,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime
);

comment on table brc_sys_user_role is '系统用户角色关系表';
comment on column brc_sys_user_role.id is 'ID';
comment on column brc_sys_user_role.user_id is '用户ID';
comment on column brc_sys_user_role.role_id is '角色ID';
comment on column brc_sys_user_role.version is '版本号';
comment on column brc_sys_user_role.deleted is '删除标识（false：存在，true：已删除）';
comment on column brc_sys_user_role.created_user_id is '创建者ID';
comment on column brc_sys_user_role.created_time is '创建时间';
comment on column brc_sys_user_role.updated_user_id is '修改者ID';
comment on column brc_sys_user_role.updated_time is '修改时间';

create index idx_brc_sys_user_role_user_id on brc_sys_user_role (user_id);
create index idx_brc_sys_user_role_role_id on brc_sys_user_role (role_id);

-- 5、系统岗位表
drop table if exists brc_sys_post;
create table brc_sys_post
(
    id              bigint      not null primary key,
    name            varchar(50) not null,
    code            varchar(30) not null,
    sort            integer default 0,
    status          boolean default true,
    remark          varchar(500),
    tenant_id       bigint,
    version         integer,
    deleted         boolean default false,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime
);

comment on table brc_sys_post is '系统岗位表';
comment on column brc_sys_post.id is 'ID';
comment on column brc_sys_post.name is '岗位名称';
comment on column brc_sys_post.code is '岗位编码';
comment on column brc_sys_post.sort is '显示顺序';
comment on column brc_sys_post.status is '状态（false：停用，true：正常）';
comment on column brc_sys_post.remark is '备注';
comment on column brc_sys_post.tenant_id is '租户ID';
comment on column brc_sys_post.version is '版本号';
comment on column brc_sys_post.deleted is '删除标识（false：存在，true：已删除）';
comment on column brc_sys_post.created_user_id is '创建者ID';
comment on column brc_sys_post.created_time is '创建时间';
comment on column brc_sys_post.updated_user_id is '修改者ID';
comment on column brc_sys_post.updated_time is '修改时间';

-- 初始化-系统岗位表数据
INSERT INTO brc_sys_post (id, name, code, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '董事长', 'ceo', 1, true, null, null, null, false, 1, now(), null, null);
INSERT INTO brc_sys_post (id, name, code, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '项目经理', 'se', 2, true, null, null, null, false, 1, now(), null, null);
INSERT INTO brc_sys_post (id, name, code, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (3, '人力资源', 'hr', 3, true, null, null, null, false, 1, now(), null, null);
INSERT INTO brc_sys_post (id, name, code, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (4, '普通员工', 'user', 4, true, null, null, null, false, 1, now(), null, null);

-- 6、系统用户岗位关系表
drop table if exists brc_sys_user_post;
create table brc_sys_user_post
(
    id              bigint not null primary key,
    user_id         bigint not null,
    post_id         bigint not null,
    version         integer,
    deleted         boolean,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime
);

comment on table brc_sys_user_post is '系统用户岗位关系表';
comment on column brc_sys_user_post.id is 'ID';
comment on column brc_sys_user_post.user_id is '用户ID';
comment on column brc_sys_user_post.post_id is '岗位ID';
comment on column brc_sys_user_post.version is '版本号';
comment on column brc_sys_user_post.deleted is '删除标识（false：存在，true：已删除）';
comment on column brc_sys_user_post.created_user_id is '创建者ID';
comment on column brc_sys_user_post.created_time is '创建时间';
comment on column brc_sys_user_post.updated_user_id is '修改者ID';
comment on column brc_sys_user_post.updated_time is '修改时间';

create index idx_brc_sys_user_post_user_id on brc_sys_user_post (user_id);
create index idx_brc_sys_user_post_post_id on brc_sys_user_post (post_id);

-- 7、系统菜单表
drop table if exists brc_sys_menu;
create table brc_sys_menu
(
    id              bigint      not null primary key,
    name            varchar(50) not null,
    type            char,
    parent_id       bigint      not null,
    url             varchar(255),
    authority       varchar(100),
    icon            varchar(100),
    open_style      boolean,
    sort            integer default 0,
    remark          varchar(500),
    status          boolean default true,
    version         integer,
    deleted         boolean default false,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime
);

comment on table brc_sys_menu is '系统菜单表';
comment on column brc_sys_menu.id is 'ID';
comment on column brc_sys_menu.name is '菜单名称';
comment on column brc_sys_menu.type is '类型（M：菜单，B：按钮，I：接口）';
comment on column brc_sys_menu.parent_id is '父菜单ID，一级菜单为0';
comment on column brc_sys_menu.url is '组件路径';
comment on column brc_sys_menu.authority is '权限标识';
comment on column brc_sys_menu.icon is '菜单图标';
comment on column brc_sys_menu.open_style is '打开方式（0：内部，1：外部）';
comment on column brc_sys_menu.sort is '显示顺序';
comment on column brc_sys_menu.remark is '备注';
comment on column brc_sys_menu.status is '状态（false：停用，true：正常）';
comment on column brc_sys_menu.version is '版本号';
comment on column brc_sys_menu.deleted is '删除标识（false：存在，true：已删除）';
comment on column brc_sys_menu.created_user_id is '创建者ID';
comment on column brc_sys_menu.created_time is '创建时间';
comment on column brc_sys_menu.updated_user_id is '修改者ID';
comment on column brc_sys_menu.updated_time is '修改时间';

-- 初始化-系统菜单表数据
-- 一级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '权限管理', 'M', 0, null, null, 'icon-safetycertificate', false, 1, '系统管理目录', true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '系统设置', 'M', 0, null, null, 'icon-setting', false, 2, '系统管理目录', true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (3, '系统监控', 'M', 0, null, null, 'icon-Report', false, 3, '系统监控目录', true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (4, '系统工具', 'M', 0, null, null, 'icon-wrench', false, 4, '系统工具目录', true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (5, '日志管理', 'M', 0, null, null, 'icon-filedone', false, 5, '系统日志目录', true, null, false, 1, now(), null, null);
-- 二级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (101, '用户管理', 'M', 1, 'system/user/index', 'system:user:page', 'icon-user', false, 1, '用户管理菜单', true, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (102, '机构管理', 'M', 1, 'system/org/index', 'system:org:page', 'icon-cluster', false, 2, '机构管理菜单', true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (103, '岗位管理', 'M', 1, 'system/post/index', 'system:post:page', 'icon-solution', false, 3, '岗位管理菜单', true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (104, '角色管理', 'M', 1, 'system/role/index', 'system:role:page', 'icon-team', false, 4, '角色管理菜单', true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (105, '菜单管理', 'M', 1, 'system/menu/index', 'system:menu:page', 'icon-menu', false, 5, '菜单管理菜单', true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (112, '数据字典', 'M', 2, 'system/dict/index', 'system:dictType:page', 'icon-insertrowabove', false, 2, '数据字典菜单', true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (113, '参数设置', 'M', 2, 'system/param/index', 'system:param:page', 'icon-control', false, 3, '参数设置菜单', true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (114, '附件管理', 'M', 2, 'system/attachment/index', 'system:attachment:page', 'icon-folder', false, 4, '参数设置菜单', true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (116, '通知公告', 'M', 2, 'system/notice/index', 'system:notice:page', 'icon-message', false, 8, '通知公告菜单', true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (117, '租户管理', 'M', 2, 'system/tenant/index', 'system:tenant:page', 'icon-deploymentunit', false, 6, '系统租户菜单', true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (121, '在线用户', 'M', 3, 'monitor/onlineUser/index', 'monitor:onlineUser:page', 'icon-solution', false, 1, '在线用户菜单', true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (122, '服务监控', 'M', 3, 'monitor/server/index', 'monitor:server:info', 'icon-sever', false, 2, '服务监控菜单', true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (123, '缓存监控', 'M', 3, 'monitor/cache/index', 'monitor:cache:info', 'icon-fund', false, 3, '缓存监控菜单', true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (132, '接口文档', 'M', 4, '{{apiUrl}}/doc.html', 'system:swagger:page', 'icon-file-text', true, 2, '接口文档菜单', true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (141, '登录日志', 'M', 5, 'system/loginLog/index', 'system:loginLog:page', 'icon-solution', false, 1, '登录日志菜单', true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (142, '操作日志', 'M', 5, 'system/operateLog/index', 'system:operateLog:page', 'icon-file-text', false, 2, '操作日志菜单', true, null, false, 1, now(), null, null);
-- 三级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1011, '用户新增', 'B', 101, null, 'system:user:save', '', false, 1, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1012, '用户修改', 'B', 101, null, 'system:user:update', '', false, 2, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1013, '用户删除', 'B', 101, null, 'system:user:delete', '', false, 3, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1014, '用户详情', 'B', 101, null, 'system:user:info', '', false, 4, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1015, '用户导出', 'B', 101, null, 'system:user:export', '', false, 5, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1016, '用户导入', 'B', 101, null, 'system:user:import', '', false, 6, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1017, '重置密码', 'B', 101, null, 'system:user:resetPwd', '', false, 7, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1021, '机构新增', 'B', 102, null, 'system:org:save', '', false, 1, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1022, '机构修改', 'B', 102, null, 'system:org:update', '', false, 2, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1023, '机构删除', 'B', 102, null, 'system:org:delete', '', false, 3, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1024, '机构详情', 'B', 102, null, 'system:org:info', '', false, 4, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1025, '机构导出', 'B', 102, null, 'system:org:export', '', false, 5, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1026, '机构导入', 'B', 102, null, 'system:org:import', '', false, 6, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1031, '岗位新增', 'B', 103, null, 'system:post:save', '', false, 1, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1032, '岗位修改', 'B', 103, null, 'system:post:update', '', false, 2, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1033, '岗位删除', 'B', 103, null, 'system:post:delete', '', false, 3, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1034, '岗位详情', 'B', 103, null, 'system:post:info', '', false, 4, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1035, '岗位导出', 'B', 103, null, 'system:post:export', '', false, 5, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1036, '岗位导入', 'B', 103, null, 'system:post:import', '', false, 6, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1041, '角色新增', 'B', 104, null, 'system:role:save', '', false, 1, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1042, '角色修改', 'B', 104, null, 'system:role:update', '', false, 2, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1043, '角色删除', 'B', 104, null, 'system:role:delete', '', false, 3, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1044, '角色详情', 'B', 104, null, 'system:role:info', '', false, 4, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1045, '角色导出', 'B', 104, null, 'system:role:export', '', false, 5, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1046, '角色导入', 'B', 104, null, 'system:role:import', '', false, 6, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1047, '角色列表', 'B', 104, null, 'system:role:list', '', false, 7, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1048, '角色菜单', 'B', 104, null, 'system:role:menu', '', false, 8, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1051, '菜单新增', 'B', 105, null, 'system:menu:save', '', false, 1, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1052, '菜单修改', 'B', 105, null, 'system:menu:update', '', false, 2, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1053, '菜单删除', 'B', 105, null, 'system:menu:delete', '', false, 3, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1054, '菜单详情', 'B', 105, null, 'system:menu:info', '', false, 4, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1055, '菜单导出', 'B', 105, null, 'system:menu:export', '', false, 5, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1056, '菜单导入', 'B', 105, null, 'system:menu:import', '', false, 6, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1121, '字典类型新增', 'B', 112, null, 'system:dictType:save', '', false, 1, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1122, '字典类型修改', 'B', 112, null, 'system:dictType:update', '', false, 2, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1123, '字典类型删除', 'B', 112, null, 'system:dictType:delete', '', false, 3, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1124, '字典类型详情', 'B', 112, null, 'system:dictType:info', '', false, 4, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1125, '字典类型导出', 'B', 112, null, 'system:dictType:export', '', false, 5, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1126, '字典类型导入', 'B', 112, null, 'system:dictType:import', '', false, 6, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1127, '字典数据管理', 'B', 112, 'system/dictData/index', 'system:dictData:page', 'icon-menu', false, 0, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1128, '字典数据新增', 'B', 112, null, 'system:dictData:save', '', false, 1, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1129, '字典数据修改', 'B', 112, null, 'system:dictData:update', '', false, 2, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1130, '字典数据删除', 'B', 112, null, 'system:dictData:delete', '', false, 3, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1131, '字典数据详情', 'B', 112, null, 'system:dictData:info', '', false, 4, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1132, '字典数据导出', 'B', 112, null, 'system:dictData:export', '', false, 5, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1133, '字典数据导入', 'B', 112, null, 'system:dictData:import', '', false, 6, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1134, '参数新增', 'B', 113, null, 'system:param:save', '', false, 1, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1135, '参数修改', 'B', 113, null, 'system:param:update', '', false, 2, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1136, '参数删除', 'B', 113, null, 'system:param:delete', '', false, 3, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1137, '参数详情', 'B', 113, null, 'system:param:info', '', false, 4, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1138, '参数导出', 'B', 113, null, 'system:param:export', '', false, 5, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1139, '参数导入', 'B', 113, null, 'system:param:import', '', false, 6, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1141, '附件上传', 'B', 114, null, 'system:attachment:save', '', false, 1, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1142, '附件删除', 'B', 114, null, 'system:attachment:delete', '', false, 3, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1143, '附件详情', 'B', 114, null, 'system:attachment:info', '', false, 4, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1161, '通知公告新增', 'B', 116, null, 'system:notice:save', '', false, 1, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1162, '通知公告修改', 'B', 116, null, 'system:notice:update', '', false, 2, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1163, '通知公告删除', 'B', 116, null, 'system:notice:delete', '', false, 3, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1164, '通知公告详情', 'B', 116, null, 'system:notice:info', '', false, 4, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1165, '通知公告导出', 'B', 116, null, 'system:notice:export', '', false, 5, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1166, '通知公告导入', 'B', 116, null, 'system:notice:import', '', false, 6, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1171, '租户新增', 'B', 117, null, 'system:tenant:save', '', false, 1, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1172, '租户修改', 'B', 117, null, 'system:tenant:update', '', false, 2, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1173, '租户删除', 'B', 117, null, 'system:tenant:delete', '', false, 3, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1174, '租户详情', 'B', 117, null, 'system:tenant:info', '', false, 4, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1175, '租户导出', 'B', 117, null, 'system:tenant:export', '', false, 5, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1176, '租户导入', 'B', 117, null, 'system:tenant:import', '', false, 6, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1211, '在线用户强退', 'B', 121, null, 'monitor:onlineUser:delete', '', false, 1, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1411, '登录日志删除', 'B', 141, null, 'system:loginLog:delete', '', false, 3, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1412, '登录日志详情', 'B', 141, null, 'system:loginLog:info', '', false, 4, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1413, '登录日志导出', 'B', 141, null, 'system:loginLog:export', '', false, 5, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1421, '操作日志删除', 'B', 142, null, 'system:operateLog:delete', '', false, 3, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1422, '操作日志详情', 'B', 142, null, 'system:operateLog:info', '', false, 4, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1423, '操作日志导出', 'B', 142, null, 'system:operateLog:export', '', false, 5, null, true, null, false, 1, now(), null, null);

-- 8、系统角色菜单关系表
drop table if exists brc_sys_role_menu;
create table brc_sys_role_menu
(
    id              bigint not null primary key,
    role_id         bigint not null,
    menu_id         bigint not null,
    version         integer,
    deleted         boolean,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime
);

comment on table brc_sys_role_menu is '系统角色菜单关系表';
comment on column brc_sys_role_menu.id is 'ID';
comment on column brc_sys_role_menu.role_id is '角色ID';
comment on column brc_sys_role_menu.menu_id is '菜单ID';
comment on column brc_sys_role_menu.version is '版本号';
comment on column brc_sys_role_menu.deleted is '删除标识（false：存在，true：已删除）';
comment on column brc_sys_role_menu.created_user_id is '创建者ID';
comment on column brc_sys_role_menu.created_time is '创建时间';
comment on column brc_sys_role_menu.updated_user_id is '修改者ID';
comment on column brc_sys_role_menu.updated_time is '修改时间';

create index idx_brc_sys_role_menu_role_id on brc_sys_role_menu (role_id);
create index idx_brc_sys_role_menu_menu_id on brc_sys_role_menu (menu_id);

-- 9、系统角色数据范围表
drop table if exists brc_sys_role_data_scope;
create table brc_sys_role_data_scope
(
    id              bigint not null primary key,
    role_id         bigint not null,
    org_id          bigint not null,
    version         integer,
    deleted         boolean,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime
);

comment on table brc_sys_role_data_scope is '系统角色数据范围表';
comment on column brc_sys_role_data_scope.id is 'ID';
comment on column brc_sys_role_data_scope.role_id is '角色ID';
comment on column brc_sys_role_data_scope.org_id is '机构ID';
comment on column brc_sys_role_data_scope.version is '版本号';
comment on column brc_sys_role_data_scope.deleted is '删除标识（false：存在，true：已删除）';
comment on column brc_sys_role_data_scope.created_user_id is '创建者ID';
comment on column brc_sys_role_data_scope.created_time is '创建时间';
comment on column brc_sys_role_data_scope.updated_user_id is '修改者ID';
comment on column brc_sys_role_data_scope.updated_time is '修改时间';

-- 10、系统登录日志表
drop table if exists brc_sys_login_log;
create table brc_sys_login_log
(
    id           bigint not null primary key,
    username     varchar(50),
    info         smallint,
    ip           varchar(128),
    location     varchar(255),
    browser      varchar(50),
    os           varchar(50),
    user_agent   varchar(500),
    status       boolean,
    access_time  datetime,
    tenant_id    bigint,
    created_time datetime
);

comment on table brc_sys_login_log is '系统登录日志表';
comment on column brc_sys_login_log.id is 'ID';
comment on column brc_sys_login_log.username is '用户账号';
comment on column brc_sys_login_log.info is '操作信息';
comment on column brc_sys_login_log.ip is '登录IP地址';
comment on column brc_sys_login_log.location is '登录地点';
comment on column brc_sys_login_log.browser is '浏览器类型';
comment on column brc_sys_login_log.os is '操作系统';
comment on column brc_sys_login_log.user_agent is 'User Agent';
comment on column brc_sys_login_log.status is '状态（0：失败，1：成功）';
comment on column brc_sys_login_log.access_time is '访问时间';
comment on column brc_sys_login_log.tenant_id is '租户ID';
comment on column brc_sys_login_log.created_time is '创建时间';

-- 11、系统操作日志表
drop table if exists brc_sys_operate_log;
create table brc_sys_operate_log
(
    id             bigint not null primary key,
    name           varchar(50),
    module_name    varchar(50),
    request_uri    varchar(2048),
    request_method varchar(10),
    request_param  text,
    result_message varchar(500),
    operated_type  varchar(20),
    operated_time  datetime,
    duration       integer,
    status         boolean,
    user_agent     varchar(500),
    ip             varchar(128),
    location       varchar(255),
    user_id        bigint,
    username       varchar(50),
    org_id         bigint,
    tenant_id      bigint,
    created_time   datetime
);

comment on table brc_sys_operate_log is '系统操作日志表';
comment on column brc_sys_operate_log.id is 'ID';
comment on column brc_sys_operate_log.name is '操作名称';
comment on column brc_sys_operate_log.module_name is '模块名';
comment on column brc_sys_operate_log.request_uri is '请求URI';
comment on column brc_sys_operate_log.request_method is '请求方法';
comment on column brc_sys_operate_log.request_param is '请求参数';
comment on column brc_sys_operate_log.result_message is '返回消息';
comment on column brc_sys_operate_log.operated_type is '操作类型';
comment on column brc_sys_operate_log.operated_time is '操作时间';
comment on column brc_sys_operate_log.duration is '执行时长（毫秒）';
comment on column brc_sys_operate_log.status is '操作状态（0：失败，1：成功）';
comment on column brc_sys_operate_log.user_agent is 'User Agent';
comment on column brc_sys_operate_log.ip is '操作IP';
comment on column brc_sys_operate_log.location is '操作地点';
comment on column brc_sys_operate_log.user_id is '操作人ID';
comment on column brc_sys_operate_log.username is '操作人账号';
comment on column brc_sys_operate_log.org_id is '机构ID';
comment on column brc_sys_operate_log.tenant_id is '租户ID';
comment on column brc_sys_operate_log.created_time is '创建时间';

-- 12、系统字典类型表
drop table if exists brc_sys_dict_type;
create table brc_sys_dict_type
(
    id              bigint not null primary key,
    dict_name       varchar(100),
    dict_type       varchar(100),
    sort            integer default 0,
    status          boolean default true,
    remark          varchar(500),
    tenant_id       bigint,
    version         integer,
    deleted         boolean,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime,
    constraint uk_dict_type unique (dict_type)
);

comment on table brc_sys_dict_type is '系统字典类型表';
comment on column brc_sys_dict_type.id is 'ID';
comment on column brc_sys_dict_type.dict_name is '字典名称';
comment on column brc_sys_dict_type.dict_type is '字典类型';
comment on column brc_sys_dict_type.sort is '显示顺序';
comment on column brc_sys_dict_type.status is '状态（false：停用，true：正常）';
comment on column brc_sys_dict_type.remark is '备注';
comment on column brc_sys_dict_type.tenant_id is '租户ID';
comment on column brc_sys_dict_type.version is '版本号';
comment on column brc_sys_dict_type.deleted is '删除标识（false：存在，true：已删除）';
comment on column brc_sys_dict_type.created_user_id is '创建者ID';
comment on column brc_sys_dict_type.created_time is '创建时间';
comment on column brc_sys_dict_type.updated_user_id is '修改者ID';
comment on column brc_sys_dict_type.updated_time is '修改时间';

-- 初始化-系统字典类型表数据
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '用户性别', 'sys_user_gender', 0, true, '用户性别列表', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '系统开关', 'sys_status', 0, true, '系统开关列表', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (3, '数据范围', 'sys_data_scope', 0, true, '根据范围权限', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (4, '操作状态', 'sys_operate_status', 0, true, '操作状态列表', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (5, '操作信息', 'sys_operate_info', 0, true, '登录状态列表', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (6, '操作类型', 'sys_operate_type', 0, true, '操作类型列表', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (7, '通知类型', 'sys_notice_type', 0, true, '通知类型列表', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (8, '通知状态', 'sys_notice_status', 0, true, '通知状态列表', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (9, '系统是否', 'sys_yes_no', 0, true, '系统是否列表', null, 1, false, 1, now(), null, null);

-- 13、系统字典数据表
drop table if exists brc_sys_dict_data;
create table brc_sys_dict_data
(
    id              bigint not null primary key,
    dict_label      varchar(100),
    dict_value      varchar(100),
    dict_type_id    bigint,
    label_class     varchar(100),
    sort            integer default 0,
    status          boolean default true,
    remark          varchar(500),
    tenant_id       bigint,
    version         integer,
    deleted         boolean,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime
);

comment on table brc_sys_dict_data is '系统字典数据表';
comment on column brc_sys_dict_data.id is 'ID';
comment on column brc_sys_dict_data.dict_label is '字典标签';
comment on column brc_sys_dict_data.dict_value is '字典值';
comment on column brc_sys_dict_data.dict_type_id is '字典类型';
comment on column brc_sys_dict_data.label_class is '标签属性';
comment on column brc_sys_dict_data.sort is '显示顺序';
comment on column brc_sys_dict_data.status is '状态（false：停用，true：正常）';
comment on column brc_sys_dict_data.remark is '备注';
comment on column brc_sys_dict_data.tenant_id is '租户ID';
comment on column brc_sys_dict_data.version is '版本号';
comment on column brc_sys_dict_data.deleted is '删除标识（false：存在，true：已删除）';
comment on column brc_sys_dict_data.created_user_id is '创建者ID';
comment on column brc_sys_dict_data.created_time is '创建时间';
comment on column brc_sys_dict_data.updated_user_id is '修改者ID';
comment on column brc_sys_dict_data.updated_time is '修改时间';

-- 初始化-系统字典数据表数据
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (11, '男', 'M', 1, '', 1, true, null, null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (12, '女', 'F', 1, '', 2, true, null, null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (13, '未知', 'N', 1, '', 3, true, null, null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (21, '正常', 'true', 2, 'primary', 1, true, null, null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (22, '停用', 'false', 2, 'danger', 2, true, null, null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (31, '全部数据', '1', 3, '', 1, true, null, null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (32, '本机构及以下机构数据', '2', 3, '', 2, true, null, null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (33, '本机构数据', '3', 3, '', 3, true, null, null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (34, '本人数据', '4', 3, '', 4, true, null, null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (35, '自定义数据', '5', 3, '', 5, true, null, null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (41, '成功', 'true', 4, 'primary', 1, true, '', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (42, '失败', 'false', 4, 'danger', 2, true, '', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (51, '登录成功', '0', 5, 'primary', 1, true, '', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (52, '退出成功', '1', 5, 'success', 2, true, '', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (53, '验证码错误', '2', 5, 'warning', 3, true, '', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (54, '账号密码错误', '3', 5, 'danger', 4, true, '', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (61, '其他', 'OTHER', 6, 'info', 99, true, '', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (62, '新增', 'INSERT', 6, 'primary', 1, true, '', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (63, '修改', 'UPDATE', 6, 'info', 2, true, '', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (64, '删除', 'DELETE', 6, 'danger', 3, true, '', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (65, '授权', 'GRANT', 6, 'warning', 7, true, '', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (66, '导出', 'EXPORT', 6, 'warning', 5, true, '', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (67, '导入', 'IMPORT', 6, 'warning', 6, true, '', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (68, '强退', 'FORCE_QUIT', 6, 'danger', 7, true, '', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (69, '生成代码', 'GEN_CODE', 6, 'success', 8, true, '', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (70, '清空数据', 'CLEAN_DATA', 6, 'danger', 9, true, '', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (71, '查询', 'GET', 6, '', 4, true, '', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (73, '通知', '1', 7, 'warning', 1, true, null, null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (74, '公告', '2', 7, 'success', 2, true, null, null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (81, '正常', '0', 8, 'primary', 1, true, null, null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (82, '关闭', '1', 8, 'danger', 2, true, null, null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (91, '是', 'Y', 9, 'primary', 1, true, null, null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (92, '否', 'N', 9, 'danger', 2, true, null, null, 1, false, 1, now(), null, null);

-- 14、系统参数表
drop table if exists brc_sys_param;
create table brc_sys_param
(
    id              bigint not null primary key,
    param_name      varchar(100),
    param_key       varchar(100),
    param_value     text,
    built_in        char,
    remark          varchar(500),
    tenant_id       bigint,
    version         integer,
    deleted         boolean,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime,
    constraint uk_param_key unique (param_key)
);

comment on table brc_sys_param is '系统参数表';
comment on column brc_sys_param.id is 'ID';
comment on column brc_sys_param.param_name is '参数名称';
comment on column brc_sys_param.param_key is '参数键';
comment on column brc_sys_param.param_value is '参数值';
comment on column brc_sys_param.built_in is '是否系统内置（Y：是，N：否）';
comment on column brc_sys_param.remark is '备注';
comment on column brc_sys_param.tenant_id is '租户ID';
comment on column brc_sys_param.version is '版本号';
comment on column brc_sys_param.deleted is '删除标识（false：存在，true：已删除）';
comment on column brc_sys_param.created_user_id is '创建者ID';
comment on column brc_sys_param.created_time is '创建时间';
comment on column brc_sys_param.updated_user_id is '修改者ID';
comment on column brc_sys_param.updated_time is '修改时间';

-- 初始化-系统配置表数据
INSERT INTO brc_sys_param (id, param_name, param_key, param_value, built_in, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '用户登录-验证码开关', 'system.account.captchaEnabled', 'false', 'Y', '是否开启验证码功能（true：开启，false：关闭）', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_param (id, param_name, param_key, param_value, built_in, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '用户账号-注册开关', 'system.account.registerEnabled', 'false', 'Y', '是否开启注册功能（true：开启，false：关闭）', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_param (id, param_name, param_key, param_value, built_in, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (3, '短信-短信开关', 'system.sms.enabled', 'true', 'Y', '是否开启短信功能（true：开启，false：关闭）', null, 1, false, 1, now(), null, null);

-- 15、系统租户表
drop table if exists brc_sys_tenant;
create table brc_sys_tenant
(
    id              bigint       primary key,
    name            varchar(100) not null,
    site_domain     varchar(200),
    site_url        varchar(100) not null,
    site_logo       varchar(200),
    config          text,
    expires_time    datetime,
    status          boolean default true,
    version         integer,
    deleted         boolean,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime
);

comment on table brc_sys_tenant is '系统租户表';
comment on column brc_sys_tenant.id is 'ID';
comment on column brc_sys_tenant.name is '租户名称';
comment on column brc_sys_tenant.site_domain is '站点域名';
comment on column brc_sys_tenant.site_url is '访问网址';
comment on column brc_sys_tenant.site_logo is 'logo';
comment on column brc_sys_tenant.config is '系统配置';
comment on column brc_sys_tenant.expires_time is '过期时间';
comment on column brc_sys_tenant.status is '状态（0：停用，1：正常）';
comment on column brc_sys_tenant.version is '版本号';
comment on column brc_sys_tenant.deleted is '删除标识（0：存在，1：已删除）';
comment on column brc_sys_tenant.created_user_id is '创建者ID';
comment on column brc_sys_tenant.created_time is '创建时间';
comment on column brc_sys_tenant.updated_user_id is '修改者ID';
comment on column brc_sys_tenant.updated_time is '修改时间';

-- 16、系统附件表
drop table if exists brc_sys_attachment;
create table brc_sys_attachment
(
    id              bigint not null primary key,
    name            varchar(100),
    url             varchar(255),
    size            bigint,
    type            varchar(50),
    suffix          varchar(10),
    hash            varchar(255),
    platform        varchar(50),
    tenant_id       bigint,
    version         integer,
    deleted         boolean,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime
);

comment on table brc_sys_attachment is '系统附件表';
comment on column brc_sys_attachment.id is 'ID';
comment on column brc_sys_attachment.name is '附件名称';
comment on column brc_sys_attachment.url is '附件地址';
comment on column brc_sys_attachment.size is '附件大小（单位字节）';
comment on column brc_sys_attachment.type is '附件类型';
comment on column brc_sys_attachment.suffix is '附件名后缀';
comment on column brc_sys_attachment.hash is '哈希码';
comment on column brc_sys_attachment.platform is '存储平台';
comment on column brc_sys_attachment.tenant_id is '租户ID';
comment on column brc_sys_attachment.version is '版本号';
comment on column brc_sys_attachment.deleted is '删除标识（false：存在，true：已删除）';
comment on column brc_sys_attachment.created_user_id is '创建者ID';
comment on column brc_sys_attachment.created_time is '创建时间';
comment on column brc_sys_attachment.updated_user_id is '修改者ID';
comment on column brc_sys_attachment.updated_time is '修改时间';

create index idx_brc_sys_attachment_created_time on brc_sys_attachment (created_time);

-- 17、系统通知公告表
drop table if exists brc_sys_notice;
create table brc_sys_notice
(
    id              bigint      not null primary key,
    title           varchar(50) not null,
    content         text,
    type            smallint    not null,
    status          boolean default true,
    remark          varchar(500),
    tenant_id       bigint,
    version         integer,
    deleted         boolean,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime
);

comment on table brc_sys_notice is '系统通知公告表';
comment on column brc_sys_notice.id is 'ID';
comment on column brc_sys_notice.title is '标题';
comment on column brc_sys_notice.content is '内容';
comment on column brc_sys_notice.type is '公告类型（0：通知，1：公告）';
comment on column brc_sys_notice.status is '状态（0：关闭，1：正常）';
comment on column brc_sys_notice.remark is '备注';
comment on column brc_sys_notice.tenant_id is '租户ID';
comment on column brc_sys_notice.version is '版本号';
comment on column brc_sys_notice.deleted is '删除标识（false：存在，true：已删除）';
comment on column brc_sys_notice.created_user_id is '创建者ID';
comment on column brc_sys_notice.created_time is '创建时间';
comment on column brc_sys_notice.updated_user_id is '修改者ID';
comment on column brc_sys_notice.updated_time is '修改时间';

-- 初始化-系统通知公告表数据
INSERT INTO brc_sys_notice (id, title, content, type, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '温馨提醒：2022-10-01 Bryce Boot 新版本发布啦', '<p>新版本内容</p>', 2, true, null, null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_notice (id, title, content, type, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '维护通知：2022-10-01 Bryce Boot 系统凌晨维护', '<p>维护内容</p>', 1, true, null, null, 1, false, 1, now(), null, null);
