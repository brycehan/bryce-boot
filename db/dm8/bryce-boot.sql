create schema if not exists bryce_boot;
/*
    -- 删除表
    drop table if exists brc_sys_dept;
    drop table if exists brc_sys_user;
    drop table if exists brc_sys_role;
    drop table if exists brc_sys_user_role;
    drop table if exists brc_sys_post;
    drop table if exists brc_sys_user_post;
    drop table if exists brc_sys_menu;
    drop table if exists brc_sys_role_menu;
    drop table if exists brc_sys_role_dept;
    drop table if exists brc_sys_login_log;
    drop table if exists brc_sys_operate_log;
    drop table if exists brc_sys_dict_type;
    drop table if exists brc_sys_dict_data;
    drop table if exists brc_sys_param;
    drop table if exists brc_sys_attachment;
    drop table if exists brc_sys_notice;
    drop table if exists brc_sys_area_code;
 */

-- 1、系统部门表
create table brc_sys_dept
(
    id              bigint       not null primary key,
    name            varchar(100) not null,
    code            varchar(30),
    parent_id       bigint       not null,
    ancestor        varchar(255),
    leader_user_id  bigint,
    contact_number  varchar(20),
    email           varchar(50),
    remark          varchar(500),
    sort            integer default 0,
    status          smallint default 1,
    deleted         datetime,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime
);

comment on table brc_sys_dept is '系统部门表';
comment on column brc_sys_dept.id is 'ID';
comment on column brc_sys_dept.name is '部门名称';
comment on column brc_sys_dept.code is '部门编码';
comment on column brc_sys_dept.parent_id is '父部门ID';
comment on column brc_sys_dept.ancestor is '祖级部门列表';
comment on column brc_sys_dept.leader_user_id is '负责人';
comment on column brc_sys_dept.contact_number is '联系电话';
comment on column brc_sys_dept.email is '邮箱';
comment on column brc_sys_dept.remark is '备注';
comment on column brc_sys_dept.sort is '显示顺序';
comment on column brc_sys_dept.status is '状态（0：停用，1：正常）';
comment on column brc_sys_dept.deleted is '删除标识';
comment on column brc_sys_dept.created_user_id is '创建者ID';
comment on column brc_sys_dept.created_time is '创建时间';
comment on column brc_sys_dept.updated_user_id is '修改者ID';
comment on column brc_sys_dept.updated_time is '修改时间';

create index idx_brc_sys_dept_parent_id on brc_sys_dept (parent_id);

-- 初始化-系统部门表数据
INSERT INTO brc_sys_dept (id, name, code, parent_id, ancestor, leader_user_id, contact_number, email, remark, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (100, 'Bryce科技', null, 0, '0', 1, '15800008001', 'brycehan@163.com', null, 0, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_dept (id, name, code, parent_id, ancestor, leader_user_id, contact_number, email, remark, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (101, '北京总公司', null, 100, '0,100', 1, '15800008002', 'brycehan@163.com', null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_dept (id, name, code, parent_id, ancestor, leader_user_id, contact_number, email, remark, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (102, '济南分公司', null, 100, '0,100', 1, '15800008003', 'brycehan@163.com', null, 2, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_dept (id, name, code, parent_id, ancestor, leader_user_id, contact_number, email, remark, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (103, '研发部门', null, 101, '0,100,101', 1, '15800008004', 'brycehan@163.com', null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_dept (id, name, code, parent_id, ancestor, leader_user_id, contact_number, email, remark, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (104, '市场部门', null, 101, '0,100,101', null, '15800008005', 'brycehan@163.com', null, 2, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_dept (id, name, code, parent_id, ancestor, leader_user_id, contact_number, email, remark, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (105, '测试部门', null, 101, '0,100,101', null, '15800008006', 'brycehan@163.com', null, 3, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_dept (id, name, code, parent_id, ancestor, leader_user_id, contact_number, email, remark, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (106, '财务部门', null, 101, '0,100,101', null, '15800008007', 'brycehan@163.com', null, 4, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_dept (id, name, code, parent_id, ancestor, leader_user_id, contact_number, email, remark, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (107, '运维部门', null, 101, '0,100,101', null, '15800008008', 'brycehan@163.com', null, 5, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_dept (id, name, code, parent_id, ancestor, leader_user_id, contact_number, email, remark, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (108, '市场部门', null, 102, '0,100,102', null, '15800008009', 'brycehan@163.com', null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_dept (id, name, code, parent_id, ancestor, leader_user_id, contact_number, email, remark, sort, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (109, '财务部门', null, 102, '0,100,102', null, '15800008010', 'brycehan@163.com', null, 2, 1, null, 1, now(), 1, now());

-- 2、系统用户表
create table brc_sys_user
(
    id                 bigint      not null primary key,
    username           varchar(50) not null,
    password           varchar(255),
    nickname           varchar(50),
    avatar             varchar(200),
    gender             char,
    type               smallint default 0,
    phone              varchar(20),
    email              varchar(50),
    birthday           datetime,
    profession         varchar(50),
    sort               integer  default 0,
    dept_id             bigint,
    status             smallint  default 1,
    remark             varchar(500),
    account_non_locked smallint  default 1,
    last_login_ip      varchar(128),
    last_login_time    datetime,
    deleted            datetime,
    created_user_id    bigint,
    created_time       datetime,
    updated_user_id    bigint,
    updated_time       datetime,
    constraint ck_gender check (gender in ('M', 'F', 'N'))
);

comment on table brc_sys_user is '系统用户表';
comment on column brc_sys_user.id is 'ID';
comment on column brc_sys_user.username is '账号';
comment on column brc_sys_user.password is '密码';
comment on column brc_sys_user.nickname is '姓名';
comment on column brc_sys_user.avatar is '头像地址';
comment on column brc_sys_user.gender is '性别（M：男，F：女，N：未知）';
comment on column brc_sys_user.type is '用户类型（0：系统用户）';
comment on column brc_sys_user.phone is '手机号码';
comment on column brc_sys_user.email is '邮箱';
comment on column brc_sys_user.birthday is '生日';
comment on column brc_sys_user.profession is '职业';
comment on column brc_sys_user.sort is '显示顺序';
comment on column brc_sys_user.dept_id is '部门ID';
comment on column brc_sys_user.status is '状态（0：停用，1：正常）';
comment on column brc_sys_user.remark is '备注';
comment on column brc_sys_user.account_non_locked is '账号锁定状态（0：锁定，1：正常）';
comment on column brc_sys_user.last_login_ip is '最后登录IP';
comment on column brc_sys_user.last_login_time is '最后登录时间';
comment on column brc_sys_user.deleted is '删除标识';
comment on column brc_sys_user.created_user_id is '创建者ID';
comment on column brc_sys_user.created_time is '创建时间';
comment on column brc_sys_user.updated_user_id is '修改者ID';
comment on column brc_sys_user.updated_time is '修改时间';

-- 初始化-系统用户表数据
INSERT INTO brc_sys_user (id, username, password, nickname, avatar, gender, type, phone, email, sort, dept_id, status, remark, account_non_locked, last_login_ip, last_login_time, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, 'admin', '$2a$10$b8Yk.Z8wkNIqz2U9kZO/IOllidB4y56hU9dh3Xv7lPstItDFtqGp6', '超级管理员', null, 'M', 0, '15800006666', 'brycehan@126.com', 0, 103, 1, '超级管理员', 1, '127.0.0.1', now(), null, 1, now(), 1, now());
INSERT INTO brc_sys_user (id, username, password, nickname, avatar, gender, type, phone, email, sort, dept_id, status, remark, account_non_locked, last_login_ip, last_login_time, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, 'brycehan', '$2a$10$.sAp6xiSh2pXwkIyJTXSfOlXHQ.8mXGw6yfGfZLhV9VuPcQR3FLq2', '布莱斯', null, 'M', 0, '15800008888', 'brycehan@163.com', 0, 103, 1, '项目经理', 1, '127.0.0.1', now(), null, 1, now(), 1, now());

-- 3、系统角色表
create table brc_sys_role
(
    id              bigint      not null primary key,
    name            varchar(50) not null,
    code            varchar(50) not null,
    data_scope      smallint,
    sort            integer default 0,
    status          smallint default 1,
    remark          varchar(500),
    dept_id          bigint,
    deleted         datetime,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime
);

comment on table brc_sys_role is '系统角色表';
comment on column brc_sys_role.id is 'ID';
comment on column brc_sys_role.name is '角色名称';
comment on column brc_sys_role.code is '角色编码';
comment on column brc_sys_role.data_scope is '数据范围（0：全部数据，1：自定义数据，2：本部门及以下部门数据，3：本部门数据，4：本人数据）';
comment on column brc_sys_role.sort is '显示顺序';
comment on column brc_sys_role.status is '状态（0：停用，1：正常）';
comment on column brc_sys_role.remark is '备注';
comment on column brc_sys_role.dept_id is '部门ID';
comment on column brc_sys_role.deleted is '删除标识';
comment on column brc_sys_role.created_user_id is '创建者ID';
comment on column brc_sys_role.created_time is '创建时间';
comment on column brc_sys_role.updated_user_id is '修改者ID';
comment on column brc_sys_role.updated_time is '修改时间';

-- 初始化-系统角色表数据
INSERT INTO brc_sys_role (id, name, code, data_scope, sort, status, remark, dept_id, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '管理员', 'SUPER_ADMIN', 1, 0, 1, '管理员', null, null, 1, now(), 1, now());
INSERT INTO brc_sys_role (id, name, code, data_scope, sort, status, remark, dept_id, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '默认角色', 'DEFAULT', 1, 0, 1, '默认角色', null, null, 1, now(), 1, now());

-- 4、系统用户角色关联表
create table brc_sys_user_role
(
    id              bigint not null primary key,
    user_id         bigint not null,
    role_id         bigint not null,
    deleted         datetime,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime
);

comment on table brc_sys_user_role is '系统用户角色关联表';
comment on column brc_sys_user_role.id is 'ID';
comment on column brc_sys_user_role.user_id is '用户ID';
comment on column brc_sys_user_role.role_id is '角色ID';
comment on column brc_sys_user_role.deleted is '删除标识';
comment on column brc_sys_user_role.created_user_id is '创建者ID';
comment on column brc_sys_user_role.created_time is '创建时间';
comment on column brc_sys_user_role.updated_user_id is '修改者ID';
comment on column brc_sys_user_role.updated_time is '修改时间';

create index idx_brc_sys_user_role_user_id on brc_sys_user_role (user_id);
create index idx_brc_sys_user_role_role_id on brc_sys_user_role (role_id);

INSERT INTO brc_sys_user_role (id, user_id, role_id, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, 2, 1, null, 1, now(), 1, now());

-- 5、系统岗位表
create table brc_sys_post
(
    id              bigint      not null primary key,
    name            varchar(50) not null,
    code            varchar(30) not null,
    sort            integer default 0,
    status          smallint default 1,
    remark          varchar(500),
    deleted         datetime,
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
comment on column brc_sys_post.status is '状态（0：停用，1：正常）';
comment on column brc_sys_post.remark is '备注';
comment on column brc_sys_post.deleted is '删除标识';
comment on column brc_sys_post.created_user_id is '创建者ID';
comment on column brc_sys_post.created_time is '创建时间';
comment on column brc_sys_post.updated_user_id is '修改者ID';
comment on column brc_sys_post.updated_time is '修改时间';

-- 初始化-系统岗位表数据
INSERT INTO brc_sys_post (id, name, code, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '董事长', 'ceo', 1, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_post (id, name, code, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '项目经理', 'se', 2, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_post (id, name, code, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (3, '人力资源', 'hr', 3, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_post (id, name, code, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (4, '普通员工', 'user', 4, 1, null, null, 1, now(), 1, now());

-- 6、系统用户岗位关联表
create table brc_sys_user_post
(
    id              bigint not null primary key,
    user_id         bigint not null,
    post_id         bigint not null,
    deleted         datetime,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime
);

comment on table brc_sys_user_post is '系统用户岗位关联表';
comment on column brc_sys_user_post.id is 'ID';
comment on column brc_sys_user_post.user_id is '用户ID';
comment on column brc_sys_user_post.post_id is '岗位ID';
comment on column brc_sys_user_post.deleted is '删除标识';
comment on column brc_sys_user_post.created_user_id is '创建者ID';
comment on column brc_sys_user_post.created_time is '创建时间';
comment on column brc_sys_user_post.updated_user_id is '修改者ID';
comment on column brc_sys_user_post.updated_time is '修改时间';

create index idx_brc_sys_user_post_user_id on brc_sys_user_post (user_id);
create index idx_brc_sys_user_post_post_id on brc_sys_user_post (post_id);

insert into brc_sys_user_post (id, user_id, post_id, deleted, created_user_id, created_time, updated_user_id, updated_time) values (1, 1, 2, null, 1, now(), 1, now());

-- 7、系统菜单表
create table brc_sys_menu
(
    id              bigint      not null primary key,
    name            varchar(50) not null,
    type            char,
    parent_id       bigint      not null,
    url             varchar(255),
    authority       varchar(100),
    icon            varchar(100),
    open_style      smallint,
    sort            integer default 0,
    remark          varchar(500),
    visible         smallint default 1,
    status          smallint default 1,
    deleted         datetime,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime
);

comment on table brc_sys_menu is '系统菜单表';
comment on column brc_sys_menu.id is 'ID';
comment on column brc_sys_menu.name is '菜单名称';
comment on column brc_sys_menu.type is '类型（C：目录，M：菜单，B：按钮）';
comment on column brc_sys_menu.parent_id is '父菜单ID，一级菜单为0';
comment on column brc_sys_menu.url is '组件路径';
comment on column brc_sys_menu.authority is '权限标识';
comment on column brc_sys_menu.icon is '菜单图标';
comment on column brc_sys_menu.open_style is '打开方式（0：内部，1：外部）';
comment on column brc_sys_menu.sort is '显示顺序';
comment on column brc_sys_menu.remark is '备注';
comment on column brc_sys_menu.visible is '显示状态（0：隐藏，1：显示）';
comment on column brc_sys_menu.status is '菜单状态（0：停用，1：正常）';
comment on column brc_sys_menu.deleted is '删除标识';
comment on column brc_sys_menu.created_user_id is '创建者ID';
comment on column brc_sys_menu.created_time is '创建时间';
comment on column brc_sys_menu.updated_user_id is '修改者ID';
comment on column brc_sys_menu.updated_time is '修改时间';

-- 初始化-系统菜单表数据
-- 一级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '权限管理', 'C', 0, null, null, 'ion:shield-checkmark-outline', 0, 1, '权限管理目录', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '系统设置', 'C', 0, null, null, 'ion:settings-outline', 0, 2, '系统管理目录', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (3, '系统监控', 'C', 0, null, null, 'ion:desktop-outline', 0, 3, '系统监控目录', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (4, '系统工具', 'C', 0, null, null, 'ion:wrench', 0, 4, '系统工具目录', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (5, '日志管理', 'C', 0, null, null, 'ion:file-tray-full-outline', 0, 5, '系统日志目录', 1, 1, null, 1, now(), 1, now());
-- 二级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (101, '用户管理', 'M', 1, 'system/user/index', 'system:user:query', 'ion:person-outline', 0, 1, '用户管理菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (102, '角色管理', 'M', 1, 'system/role/index', 'system:role:query', 'ion:people-outline', 0, 2, '角色管理菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (103, '菜单管理', 'M', 1, 'system/menu/index', 'system:menu:list', 'ion:list-outline', 0, 3, '菜单管理菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (104, '部门管理', 'M', 1, 'system/dept/index', 'system:dept:list', 'ion:podium-outline', 0, 4, '部门管理菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (105, '岗位管理', 'M', 1, 'system/post/index', 'system:post:query', 'ion:id-card-outline', 0, 5, '岗位管理菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (112, '数据字典', 'M', 2, 'system/dict/index', 'system:dict-type:query', 'ion:library-outline', 0, 2, '数据字典菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (113, '参数设置', 'M', 2, 'system/param/index', 'system:param:query', 'ion:reader-outline', 0, 3, '参数设置菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (114, '附件管理', 'M', 2, 'system/attachment/index', 'system:attachment:query', 'ion:folder-outline', 0, 4, '参数设置菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (116, '通知公告', 'M', 2, 'system/notice/index', 'system:notice:query', 'ion:information-circle-outline', 0, 5, '通知公告菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (121, '在线用户', 'M', 3, 'monitor/onlineUser/index', 'monitor:onlineUser:query', 'ion:person-outline', 0, 1, '在线用户菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (122, '服务监控', 'M', 3, 'monitor/server/index', 'monitor:server:info', 'ion:server-outline', 0, 2, '服务监控菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (123, '缓存监控', 'M', 3, 'monitor/cache/index', 'monitor:cache:info', 'ion:analytics-outline', 0, 3, '缓存监控菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (131, '代码生成', 'M', 4, '{{apiUrl}}/generator-ui/index.html', 'generator:generator:query', 'ion:code', 1, 1, '代码生成菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (132, '接口文档', 'M', 4, '{{apiUrl}}/doc.html', 'system:swagger:query', 'ion:document-outline', 1, 2, '接口文档菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (141, '操作日志', 'M', 5, 'system/operateLog/index', 'system:operateLog:query', 'ion:document-text-outline', 0, 1, '操作日志菜单', 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (142, '登录日志', 'M', 5, 'system/loginLog/index', 'system:loginLog:query', 'ion:document-lock-outline', 0, 2, '登录日志菜单', 1, 1, null, 1, now(), 1, now());
-- 三级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1010, '用户新增', 'B', 101, null, 'system:user:save', '', 0, 1, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1011, '用户修改', 'B', 101, null, 'system:user:update', '', 0, 2, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1012, '用户删除', 'B', 101, null, 'system:user:delete', '', 0, 3, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1013, '用户导出', 'B', 101, null, 'system:user:export', '', 0, 5, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1014, '用户导入', 'B', 101, null, 'system:user:import', '', 0, 6, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1015, '重置密码', 'B', 101, null, 'system:user:resetPassword', '', 0, 7, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1020, '角色新增', 'B', 102, null, 'system:role:save', '', 0, 1, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1021, '角色修改', 'B', 102, null, 'system:role:update', '', 0, 2, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1022, '角色删除', 'B', 102, null, 'system:role:delete', '', 0, 3, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1023, '角色导出', 'B', 102, null, 'system:role:export', '', 0, 5, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1024, '角色导入', 'B', 102, null, 'system:role:import', '', 0, 6, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1025, '角色列表', 'B', 102, null, 'system:role:list', '', 0, 7, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1026, '角色菜单', 'B', 102, null, 'system:role:menu', '', 0, 8, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1030, '菜单新增', 'B', 103, null, 'system:menu:save', '', 0, 1, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1031, '菜单修改', 'B', 103, null, 'system:menu:update', '', 0, 2, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1032, '菜单删除', 'B', 103, null, 'system:menu:delete', '', 0, 3, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1033, '菜单导出', 'B', 103, null, 'system:menu:export', '', 0, 5, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1034, '菜单导入', 'B', 103, null, 'system:menu:import', '', 0, 6, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1040, '部门新增', 'B', 104, null, 'system:dept:save', '', 0, 1, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1041, '部门修改', 'B', 104, null, 'system:dept:update', '', 0, 2, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1042, '部门删除', 'B', 104, null, 'system:dept:delete', '', 0, 3, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1043, '部门导出', 'B', 104, null, 'system:dept:export', '', 0, 5, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1044, '部门导入', 'B', 104, null, 'system:dept:import', '', 0, 6, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1050, '岗位新增', 'B', 105, null, 'system:post:save', '', 0, 1, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1051, '岗位修改', 'B', 105, null, 'system:post:update', '', 0, 2, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1052, '岗位删除', 'B', 105, null, 'system:post:delete', '', 0, 3, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1053, '岗位导出', 'B', 105, null, 'system:post:export', '', 0, 5, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1054, '岗位导入', 'B', 105, null, 'system:post:import', '', 0, 6, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1120, '字典类型新增', 'B', 112, null, 'system:dict-type:save', '', 0, 1, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1121, '字典类型修改', 'B', 112, null, 'system:dict-type:update', '', 0, 2, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1122, '字典类型删除', 'B', 112, null, 'system:dict-type:delete', '', 0, 3, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1123, '字典类型导出', 'B', 112, null, 'system:dict-type:export', '', 0, 5, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1124, '字典类型导入', 'B', 112, null, 'system:dict-type:import', '', 0, 6, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1125, '字典数据管理', 'B', 112, 'system/dictData/index', 'system:dictData:query', 'ion:menu', 0, 0, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1126, '字典数据新增', 'B', 112, null, 'system:dictData:save', '', 0, 1, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1127, '字典数据修改', 'B', 112, null, 'system:dictData:update', '', 0, 2, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1128, '字典数据删除', 'B', 112, null, 'system:dictData:delete', '', 0, 3, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1130, '参数新增', 'B', 113, null, 'system:param:save', '', 0, 1, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1131, '参数修改', 'B', 113, null, 'system:param:update', '', 0, 2, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1132, '参数删除', 'B', 113, null, 'system:param:delete', '', 0, 3, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1133, '参数导出', 'B', 113, null, 'system:param:export', '', 0, 5, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1134, '参数导入', 'B', 113, null, 'system:param:import', '', 0, 6, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1140, '附件上传', 'B', 114, null, 'system:attachment:save', '', 0, 1, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1141, '附件删除', 'B', 114, null, 'system:attachment:delete', '', 0, 3, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1160, '通知公告新增', 'B', 116, null, 'system:notice:save', '', 0, 1, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1161, '通知公告修改', 'B', 116, null, 'system:notice:update', '', 0, 2, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1162, '通知公告删除', 'B', 116, null, 'system:notice:delete', '', 0, 3, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1163, '通知公告导出', 'B', 116, null, 'system:notice:export', '', 0, 5, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1164, '通知公告导入', 'B', 116, null, 'system:notice:import', '', 0, 6, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1210, '在线用户强退', 'B', 121, null, 'monitor:onlineUser:delete', '', 0, 1, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1410, '登录日志删除', 'B', 141, null, 'system:loginLog:delete', '', 0, 3, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1411, '登录日志导出', 'B', 141, null, 'system:loginLog:export', '', 0, 5, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1420, '操作日志删除', 'B', 142, null, 'system:operateLog:delete', '', 0, 3, null, 1, 1, null, 1, now(), 1, now());
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, visible, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1421, '操作日志导出', 'B', 142, null, 'system:operateLog:export', '', 0, 5, null, 1, 1, null, 1, now(), 1, now());

-- 8、系统角色菜单关联表
create table brc_sys_role_menu
(
    id              bigint not null primary key,
    role_id         bigint not null,
    menu_id         bigint not null,
    deleted         datetime,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime
);

comment on table brc_sys_role_menu is '系统角色菜单关联表';
comment on column brc_sys_role_menu.id is 'ID';
comment on column brc_sys_role_menu.role_id is '角色ID';
comment on column brc_sys_role_menu.menu_id is '菜单ID';
comment on column brc_sys_role_menu.deleted is '删除标识';
comment on column brc_sys_role_menu.created_user_id is '创建者ID';
comment on column brc_sys_role_menu.created_time is '创建时间';
comment on column brc_sys_role_menu.updated_user_id is '修改者ID';
comment on column brc_sys_role_menu.updated_time is '修改时间';

create index idx_brc_sys_role_menu_role_id on brc_sys_role_menu (role_id);
create index idx_brc_sys_role_menu_menu_id on brc_sys_role_menu (menu_id);

-- 9、系统角色部门关联表
create table brc_sys_role_dept
(
    id              bigint not null primary key,
    role_id         bigint not null,
    dept_id          bigint not null,
    deleted         datetime,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime
);

comment on table brc_sys_role_dept is '系统角色部门关联表';
comment on column brc_sys_role_dept.id is 'ID';
comment on column brc_sys_role_dept.role_id is '角色ID';
comment on column brc_sys_role_dept.dept_id is '部门ID';
comment on column brc_sys_role_dept.deleted is '删除标识';
comment on column brc_sys_role_dept.created_user_id is '创建者ID';
comment on column brc_sys_role_dept.created_time is '创建时间';
comment on column brc_sys_role_dept.updated_user_id is '修改者ID';
comment on column brc_sys_role_dept.updated_time is '修改时间';

-- 10、系统登录日志表
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
    status       smallint,
    access_time  datetime,
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
comment on column brc_sys_login_log.created_time is '创建时间';

-- 11、系统操作日志表
create table brc_sys_operate_log
(
    id             bigint not null primary key,
    name           varchar(50),
    module_name    varchar(50),
    request_uri    varchar(2048),
    request_method varchar(10),
    request_param  text,
    json_result    varchar(2000),
    error_message  varchar(2000),
    operated_type  varchar(20),
    operated_time  datetime,
    duration       integer,
    status         smallint,
    ip             varchar(128),
    location       varchar(255),
    source_client  varchar(50),
    user_agent     varchar(500),
    user_id        bigint,
    username       varchar(50),
    dept_id         bigint,
    dept_name       varchar(50),
    created_time   datetime
);

comment on table brc_sys_operate_log is '系统操作日志表';
comment on column brc_sys_operate_log.id is 'ID';
comment on column brc_sys_operate_log.name is '操作名称';
comment on column brc_sys_operate_log.module_name is '模块名';
comment on column brc_sys_operate_log.request_uri is '请求URI';
comment on column brc_sys_operate_log.request_method is '请求方式';
comment on column brc_sys_operate_log.request_param is '请求参数';
comment on column brc_sys_operate_log.json_result is '返回结果';
comment on column brc_sys_operate_log.error_message is '错误消息';
comment on column brc_sys_operate_log.operated_type is '操作类型';
comment on column brc_sys_operate_log.operated_time is '操作时间';
comment on column brc_sys_operate_log.duration is '执行时长（毫秒）';
comment on column brc_sys_operate_log.status is '操作状态（0：失败，1：成功）';
comment on column brc_sys_operate_log.ip is '操作IP';
comment on column brc_sys_operate_log.location is '操作地点';
comment on column brc_sys_operate_log.source_client is '来源客户端';
comment on column brc_sys_operate_log.user_agent is 'User Agent';
comment on column brc_sys_operate_log.user_id is '操作人ID';
comment on column brc_sys_operate_log.username is '操作账号';
comment on column brc_sys_operate_log.dept_id is '部门ID';
comment on column brc_sys_operate_log.dept_name is '部门名称';
comment on column brc_sys_operate_log.created_time is '创建时间';

-- 12、系统字典类型表
create table brc_sys_dict_type
(
    id              bigint not null primary key,
    dict_name       varchar(100),
    dict_type       varchar(100),
    sort            integer default 0,
    status          smallint default 1,
    remark          varchar(500),
    deleted         datetime,
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
comment on column brc_sys_dict_type.status is '状态（0：停用，1：正常）';
comment on column brc_sys_dict_type.remark is '备注';
comment on column brc_sys_dict_type.deleted is '删除标识';
comment on column brc_sys_dict_type.created_user_id is '创建者ID';
comment on column brc_sys_dict_type.created_time is '创建时间';
comment on column brc_sys_dict_type.updated_user_id is '修改者ID';
comment on column brc_sys_dict_type.updated_time is '修改时间';

-- 初始化-系统字典类型表数据
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '用户性别', 'sys_gender', 0, 1, '用户性别列表', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '菜单状态', 'sys_show_hide', 0, 1, '菜单状态列表', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (3, '系统开关', 'sys_status', 0, 1, '系统开关列表', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (4, '系统是否', 'sys_yes_no', 0, 1, '系统是否列表', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (5, '数据范围', 'sys_data_scope', 0, 1, '根据范围权限', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (6, '通知类型', 'sys_notice_type', 0, 1, '通知类型列表', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (7, '通知状态', 'sys_notice_status', 0, 1, '通知状态列表', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (8, '操作类型', 'sys_operate_type', 0, 1, '操作类型列表', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (9, '操作状态', 'sys_operate_status', 0, 1, '操作状态列表', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (10, '登录状态', 'sys_login_status', 0, 1, '登录状态列表', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (11, '参数类型', 'sys_param_type', 0, 1, '参数类型列表', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (12, '访问类型', 'sys_access_type', 0, 1, '访问类型列表', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (13, '任务分组', 'quartz_job_group', 0, 1, '任务分组列表', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (14, '任务状态', 'quartz_job_status', 0, 1, '任务状态列表', null, 1, now(), 1, now());

-- 13、系统字典数据表
create table brc_sys_dict_data
(
    id              bigint not null primary key,
    dict_label      varchar(100),
    dict_value      varchar(100),
    dict_type_id    bigint,
    label_class     varchar(100),
    sort            integer default 0,
    status          smallint default 1,
    remark          varchar(500),
    deleted         datetime,
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
comment on column brc_sys_dict_data.status is '状态（0：停用，1：正常）';
comment on column brc_sys_dict_data.remark is '备注';
comment on column brc_sys_dict_data.deleted is '删除标识';
comment on column brc_sys_dict_data.created_user_id is '创建者ID';
comment on column brc_sys_dict_data.created_time is '创建时间';
comment on column brc_sys_dict_data.updated_user_id is '修改者ID';
comment on column brc_sys_dict_data.updated_time is '修改时间';

-- 初始化-系统字典数据表数据
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (11, '男', 'M', 1, '', 1, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (12, '女', 'F', 1, '', 2, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (13, '未知', 'N', 1, '', 3, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (21, '显示', '1', 2, 'primary', 0, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (22, '隐藏', '0', 2, 'success', 1, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (31, '正常', '1', 3, 'primary', 1, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (32, '停用', '0', 3, 'danger', 2, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (41, '是', '1', 4, 'primary', 1, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (42, '否', '0', 4, 'danger', 2, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (51, '全部数据', '0', 5, '', 1, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (52, '自定义数据', '1', 5, '', 2, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (53, '本部门及以下部门数据', '2', 5, '', 3, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (54, '本部门数据', '3', 5, '', 4, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (55, '本人数据', '4', 5, '', 5, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (63, '通知', '0', 6, 'warning', 1, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (64, '公告', '1', 6, 'success', 2, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (71, '正常', '1', 7, 'primary', 1, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (72, '关闭', '0', 7, 'danger', 2, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (80, '新增', '0', 8, 'primary', 1, 1, '', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (81, '修改', '1', 8, 'info', 2, 1, '', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (82, '删除', '2', 8, 'danger', 3, 1, '', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (83, '查询', '3', 8, '', 4, 1, '', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (84, '导入', '4', 8, 'warning', 6, 1, '', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (85, '导出', '5', 8, 'warning', 5, 1, '', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (86, '授权', '6', 8, 'warning', 7, 1, '', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (87, '强退', '7', 8, 'danger', 7, 1, '', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (88, '生成代码', '8', 8, 'success', 8, 1, '', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (89, '清空数据', '9', 8, 'danger', 9, 1, '', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (90, '其他', '10', 8, 'info', 99, 1, '', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (91, '成功', '1', 9, 'primary', 1, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (92, '失败', '0', 9, 'danger', 2, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (101, '登录成功', '0', 10, 'primary', 1, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (102, '退出成功', '1', 10, 'success', 2, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (103, '验证码错误', '2', 10, 'warning', 3, 1, '', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (104, '账号不存在/密码错误', '3', 10, 'danger', 4, 1, '', null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (111, '内置', '0', 11, 'primary', 0, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (112, '应用', '1', 11, 'success', 0, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (121, '公共', '0', 12, 'success', 1, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (122, '安全', '1', 12, 'primary', 2, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (131, '系统', 'system', 13, 'primary', 1, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (132, '应用', 'app', 13, 'success', 2, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (141, '正常', '1', 14, 'primary', 1, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (142, '暂停', '0', 14, 'danger', 2, 1, null, null, 1, now(), 1, now());

-- 14、系统参数表
create table brc_sys_param
(
    id              bigint not null primary key,
    param_name      varchar(100),
    param_key       varchar(100),
    param_value     text,
    param_type      smallint default 1,
    remark          varchar(500),
    deleted         datetime,
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
comment on column brc_sys_param.param_type is '参数类型（0：内置，1：应用）';
comment on column brc_sys_param.remark is '备注';
comment on column brc_sys_param.deleted is '删除标识';
comment on column brc_sys_param.created_user_id is '创建者ID';
comment on column brc_sys_param.created_time is '创建时间';
comment on column brc_sys_param.updated_user_id is '修改者ID';
comment on column brc_sys_param.updated_time is '修改时间';

-- 初始化-系统参数表数据
INSERT INTO brc_sys_param (id, param_name, param_key, param_value, param_type, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '用户登录-图片验证码开关', 'system.login.captcha.enabled', 'false', '0', '是否开启登录图片验证码功能（true：开启，false：关闭）', null, 1, now(), 1, now());
INSERT INTO brc_sys_param (id, param_name, param_key, param_value, param_type, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '用户登录-短信验证码开关', 'system.login.sms.enabled', 'false', '0', '是否开启登录短信验证码功能（true：开启，false：关闭）', null, 1, now(), 1, now());
INSERT INTO brc_sys_param (id, param_name, param_key, param_value, param_type, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (3, '用户注册-开关', 'system.register.enabled', 'true', '0', '是否开启注册功能（true：开启，false：关闭）', null, 1, now(), 1, now());
INSERT INTO brc_sys_param (id, param_name, param_key, param_value, param_type, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (4, '用户注册-图片验证码开关', 'system.register.captcha.enabled', 'true', '0', '是否开启注册图片验证码功能（true：开启，false：关闭）', null, 1, now(), 1, now());
INSERT INTO brc_sys_param (id, param_name, param_key, param_value, param_type, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (5, '用户注册-短信验证码开关', 'system.register.sms.enabled', 'true', '0', '是否开启注册短信验证码功能（true：开启，false：关闭）', null, 1, now(), 1, now());
INSERT INTO brc_sys_param (id, param_name, param_key, param_value, param_type, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (6, '手机短信-开关', 'system.sms.enabled', 'true', '0', '是否开启短信功能（true：开启，false：关闭）', null, 1, now(), 1, now());
INSERT INTO brc_sys_param (id, param_name, param_key, param_value, param_type, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (7, '用户管理-账号初始密码', 'system.user.init.password', '123321', '0', '初始化密码 123321', null, 1, now(), 1, now());

-- 15、系统附件表
create table brc_sys_attachment
(
    id              bigint not null primary key,
    name            varchar(100),
    path            varchar(255),
    url             varchar(255),
    size            bigint,
    access_type     smallint,
    suffix          varchar(10),
    hash            varchar(255),
    platform        varchar(50),
    deleted         datetime,
    created_user_id bigint,
    created_time    datetime,
    updated_user_id bigint,
    updated_time    datetime
);

comment on table brc_sys_attachment is '系统附件表';
comment on column brc_sys_attachment.id is 'ID';
comment on column brc_sys_attachment.name is '附件名称';
comment on column brc_sys_attachment.path is '附件路径';
comment on column brc_sys_attachment.url is '附件地址';
comment on column brc_sys_attachment.size is '附件大小（单位字节）';
comment on column brc_sys_attachment.access_type is '访问类型（0: 公共，1: 安全）';
comment on column brc_sys_attachment.suffix is '附件名后缀';
comment on column brc_sys_attachment.hash is '哈希码';
comment on column brc_sys_attachment.platform is '存储平台';
comment on column brc_sys_attachment.deleted is '删除标识';
comment on column brc_sys_attachment.created_user_id is '创建者ID';
comment on column brc_sys_attachment.created_time is '创建时间';
comment on column brc_sys_attachment.updated_user_id is '修改者ID';
comment on column brc_sys_attachment.updated_time is '修改时间';

create index idx_brc_sys_attachment_created_time on brc_sys_attachment (created_time);

-- 16、系统通知公告表
create table brc_sys_notice
(
    id              bigint      not null primary key,
    title           varchar(100) not null,
    content         text,
    type            smallint    not null,
    status          smallint default 1,
    remark          varchar(500),
    deleted         datetime,
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
comment on column brc_sys_notice.deleted is '删除标识';
comment on column brc_sys_notice.created_user_id is '创建者ID';
comment on column brc_sys_notice.created_time is '创建时间';
comment on column brc_sys_notice.updated_user_id is '修改者ID';
comment on column brc_sys_notice.updated_time is '修改时间';

-- 初始化-系统通知公告表数据
INSERT INTO brc_sys_notice (id, title, content, type, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '温馨提醒：2022-10-01 Bryce Boot 新版本发布啦', '<p>新版本内容</p>', 1, 1, null, null, 1, now(), 1, now());
INSERT INTO brc_sys_notice (id, title, content, type, status, remark, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '维护通知：2022-10-01 Bryce Boot 系统凌晨维护', '<p>维护内容</p>', 0, 1, null, null, 1, now(), 1, now());

-- 17、地区编码表
create table brc_sys_area_code
(
    id            integer          primary key,
    parent_id     integer          not null,
    deep          integer          not null,
    name          varchar(255) not null,
    code          varchar(255) not null,
    pinyin_prefix varchar(255) not null,
    pinyin        varchar(255) not null,
    ext_id        varchar(50)  not null,
    ext_name      varchar(255) not null
);

comment on table brc_sys_area_code is '地区编码表';

comment on column brc_sys_area_code.id            is 'ID';
comment on column brc_sys_area_code.parent_id     is '父ID';
comment on column brc_sys_area_code.deep          is '层级';
comment on column brc_sys_area_code.name          is '名称';
comment on column brc_sys_area_code.code          is '编码';
comment on column brc_sys_area_code.pinyin_prefix is '拼音前缀';
comment on column brc_sys_area_code.pinyin        is '拼音';
comment on column brc_sys_area_code.ext_id        is '扩展ID';
comment on column brc_sys_area_code.ext_name      is '扩展名称';