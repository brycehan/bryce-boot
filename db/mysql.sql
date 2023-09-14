create database if not exists bryce_boot default charset utf8mb4;
use bryce_boot;

-- 1、系统部门表
drop table if exists brc_sys_org;
create table brc_sys_org
(
    id              bigint          not null comment 'ID',
    name        varchar(100)    not null comment '机构名称',
    code       varchar(30)     comment '机构编码',
    parent_id       bigint          default '0' comment '父机构ID',
    ancestor        varchar(255)    comment '祖级机构列表',
    leader          varchar(50)     comment '负责人',
    contact_number  varchar(20)     comment '联系电话',
    email           varchar(50)     comment '邮箱',
    sort            int         default '0' comment '显示顺序',
    status          tinyint      default '1' comment '状态（0：停用，1：正常）',
    tenant_id       bigint          comment '租户ID',
    version         int             comment '版本号',
    deleted         tinyint      default '0' comment '删除标识（0：存在，1：已删除）',
    created_user_id  bigint          comment '创建人ID',
    created_time     datetime        comment '创建时间',
    updated_user_id  bigint          comment '修改人ID',
    updated_time     datetime        comment '修改时间',
    primary key (id),
    constraint brc_sys_org_status_check check (status in ('0', '1'))
) engine InnoDB default charset utf8mb4 comment '系统机构';

create index idx_parent_id on brc_sys_org (parent_id);

-- 初始化-系统机构表数据
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, sort, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (100, 'Bryce科技', null, 0, '0', '韩先生', '15800008888', 'brycehan@163.com', 0, 0, null, 0, 1, '2023-08-25 12:43:01', null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, sort, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (101, '北京总公司', null, 100, '0,100', '韩先生', '15800008888', 'brycehan@163.com', 1, 0, null, 0, 1, '2023-08-25 12:43:01', null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, sort, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (102, '济南分公司', null, 100, '0,100', '韩先生', '15800008888', 'brycehan@163.com', 2, 0, null, 0, 1, '2023-08-25 12:43:01', null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, sort, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (103, '研发部门', null, 101, '0,100,101', '韩先生', '15800008888', 'brycehan@163.com', 1, 0, null, 0, 1, '2023-08-25 12:43:01', null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, sort, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (104, '市场部门', null, 101, '0,100,101', '韩先生', '15800008888', 'brycehan@163.com', 2, 0, null, 0, 1, '2023-08-25 12:43:01', null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, sort, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (105, '测试部门', null, 101, '0,100,101', '韩先生', '15800008888', 'brycehan@163.com', 3, 0, null, 0, 1, '2023-08-25 12:43:01', null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, sort, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (106, '财务部门', null, 101, '0,100,101', '韩先生', '15800008888', 'brycehan@163.com', 4, 0, null, 0, 1, '2023-08-25 12:43:01', null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, sort, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (107, '运维部门', null, 101, '0,100,101', '韩先生', '15800008888', 'brycehan@163.com', 5, 0, null, 0, 1, '2023-08-25 12:43:01', null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, sort, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (108, '市场部门', null, 102, '0,100,102', '韩先生', '15800008888', 'brycehan@163.com', 1, 0, null, 0, 1, '2023-08-25 12:43:01', null, null);
INSERT INTO brc_sys_org (id, name, code, parent_id, ancestor, leader, contact_number, email, sort, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (109, '财务部门', null, 102, '0,100,102', '韩先生', '15800008888', 'brycehan@163.com', 2, 0, null, 0, null, '2023-08-25 12:43:01', null, null);

-- 2、系统用户表
drop table if exists brc_sys_user;
create table brc_sys_user
(
    id                 bigint           not null comment 'ID',
    username           varchar(50)     not null comment '账号',
    password           varchar(255)    comment '密码',
    full_name          varchar(50)     comment '姓名',
    avatar             varchar(100)    comment '头像',
    gender             char(1)         comment '性别（M：男, F：女，N：未知）',
    type          tinyint               default '0' comment '类型（0：系统用户）',
    phone              varchar(20)     comment '手机号码',
    email              varchar(50)     comment '邮箱',
    sort            int                 default '0' comment '显示顺序',
    org_id            bigint           comment '机构ID',
    super_admin     tinyint      comment '超级管理员',
    tenant_admin     tinyint      comment '租户管理员',
    status          tinyint      default '1' comment '状态（0：停用，1：正常）',
    remark          varchar(500)        comment '备注',
    account_non_locked tinyint          default '1' comment '账号锁定状态（0：锁定，1：正常）',
    last_login_ip      varchar(128)    comment '最后登录IP',
    last_login_time    datetime        comment '最后登录时间',
    tenant_id       bigint          comment '租户ID',
    version         int         comment '版本号',
    deleted         tinyint      default '0' comment '删除标识（0：存在，1：已删除）',
    created_user_id  bigint          comment '创建人ID',
    created_time     datetime        comment '创建时间',
    updated_user_id  bigint          comment '修改人ID',
    updated_time     datetime        comment '修改时间',
    primary key (id),
    constraint brc_sys_user_gender_check check (gender in ('M', 'F')),
    constraint brc_sys_user_non_locked_check check (account_non_locked in ('0', '1'))
) engine InnoDB
  default charset utf8mb4 comment '系统用户表';

-- 初始化-系统用户表数据
INSERT INTO brc_sys_user (id, username, password, full_name, avatar, gender, type, super_admin, tenant_admin, status, phone, email, remark, sort, org_id, account_non_locked, last_login_ip, last_login_time, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, 'admin', '$2a$10$H/0p9EJPQjYAspbCO85QzuDXs4v36TvdWftjx1HJSWhVoSQ85GtHi', '管理员', null, 'M', 0, null, null, 1, '15800008888', 'brycehan@163.com', '管理员', 0, 103, 1, '127.0.0.1', '2023-08-27 15:43:42', null, null, 0, 1, '2023-08-25 12:43:01', null, '2023-08-27 15:43:46');
INSERT INTO brc_sys_user (id, username, password, full_name, avatar, gender, type, super_admin, tenant_admin, status, phone, email, remark, sort, org_id, account_non_locked, last_login_ip, last_login_time, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, 'brycehan', '$2a$10$H/0p9EJPQjYAspbCO85QzuDXs4v36TvdWftjx1HJSWhVoSQ85GtHi', 'Bryce Han', null, 'M', 0, null, null, 1, '15800008888', 'brycehan7@gmail.com', '测试员', 0, 105, 1, null, null, null, null, 0, 1, '2023-08-25 12:43:01', null, null);

-- 3、系统角色表
drop table if exists brc_sys_role;
create table brc_sys_role
(
    id                         bigint not null comment 'ID',
    name                  varchar(50)     not null comment '角色名称',
    code                  varchar(50)     not null comment '角色编码',
    data_scope                 smallint    comment '数据范围（1：全部数据，2：本机构及以下机构数据，3：本机构数据，4：本人数据，5：自定义数据）',
    sort            int         default '0' comment '显示顺序',
    status          tinyint      default '1' comment '状态（0：停用，1：正常）',
    remark                     varchar(500)    comment '备注',
    org_id            bigint           comment '机构ID',
    tenant_id       bigint          comment '租户ID',
    version         int             comment '版本号',
    deleted         tinyint      default '0' comment '删除标识（0：存在，1：已删除）',
    created_user_id  bigint          comment '创建人ID',
    created_time     datetime        comment '创建时间',
    updated_user_id  bigint          comment '修改人ID',
    updated_time     datetime        comment '修改时间',
    primary key (id),
    constraint ck_deleted check (deleted in ('0', '1')),
    constraint ck_status check (status in ('0', '1'))
) engine InnoDB default charset utf8mb4 comment '系统角色';

-- 初始化-系统角色表数据
INSERT INTO bryce_boot.brc_sys_role (id, name, code, data_scope, sort, status, remark, org_id, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '超级管理员', 'admin', 1, 0, 1, '超级管理员', null, null, null, 0, 1, '2023-08-25 12:43:01', null, null);
INSERT INTO bryce_boot.brc_sys_role (id, name, code, data_scope, sort, status, remark, org_id, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '默认角色', 'default', 2, 0, 1, '默认角色', null, null, null, 0, 1, '2023-08-25 12:43:01', null, null);

-- 4、系统岗位表
drop table if exists brc_sys_post;
create table brc_sys_post
(
    id              bigint not null comment 'ID',
    name       varchar(50)     not null comment '岗位名称',
    code       varchar(30)     not null comment '岗位编码',
    sort     int         default '0' comment '显示顺序',
    remark          varchar(500)    comment '备注',
    status          tinyint      default '1' comment '状态（0：停用，1：正常）',
    created_user_id  bigint comment '创建人ID',
    created_time     datetime        comment '创建时间',
    updated_user_id  bigint comment '修改人ID',
    updated_time     datetime        comment '修改时间',
    primary key (id)
) engine InnoDB
  default charset utf8mb4 comment '系统岗位表';

-- 初始化-系统岗位表数据
INSERT INTO brc_sys_post (id, name, code, sort, remark, status, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '董事长', 'ceo', 1, null, 1, 1, '2023-08-25 12:43:01', null, null);
INSERT INTO brc_sys_post (id, name, code, sort, remark, status, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '项目经理', 'se', 2, null, 1, 1, '2023-08-25 12:43:01', null, null);
INSERT INTO brc_sys_post (id, name, code, sort, remark, status, created_user_id, created_time, updated_user_id, updated_time) VALUES (3, '人力资源', 'hr', 3, null, 1, 1, '2023-08-25 12:43:01', null, null);
INSERT INTO brc_sys_post (id, name, code, sort, remark, status, created_user_id, created_time, updated_user_id, updated_time) VALUES (4, '普通员工', 'user', 4, null, 1, 1, '2023-08-25 12:43:01', null, null);



-- 5、系统菜单表
drop table if exists brc_sys_menu;
create table brc_sys_menu
(
    id              bigint not null comment 'ID',
    name       varchar(50)     not null comment '菜单名称',
    type       char(1)         comment '类型（M：菜单，B：按钮，I：接口）',
    parent_id       bigint default '0' comment '父菜单ID，一级菜单为0',
    url       varchar(255)    comment '组件路径',
    authority      varchar(100)    comment '权限标识',
    icon            varchar(100)   comment '菜单图标',
    open_style        tinyint      comment '打开方式（0：内部，1：外部）',
    sort     int         default '0' comment '显示顺序',
    remark          varchar(500)    comment '备注',
    status          tinyint      default '1' comment '状态（0：停用，1：正常）',
    deleted     tinyint      default '0' comment '状态（0：正式数据，1：删除）',
    created_user_id  bigint comment '创建人ID',
    created_time     datetime        comment '创建时间',
    updated_user_id  bigint comment '修改人ID',
    updated_time     datetime        comment '修改时间',
    primary key (id),
    constraint brc_sys_menu_status_check check (status in ('0', '1'))
) engine InnoDB default charset utf8mb4 comment '系统菜单表';

-- 初始化-系统菜单表数据
-- 一级菜单
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '权限管理', 'M', 0, null, null, 'icon-setting', 0, 0, '系统管理目录', 1, 0, 1, '2023-08-25 09:20:22', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '系统设置', 'M', 0, null, null, 'icon-setting', 0, 0, '系统管理目录', 1, 0, 1, '2023-08-25 09:20:22', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '系统监控', 'M', 0, 'Layout', null, 'monitor', 0, 0, '系统监控目录', 1, 0, 1, '2023-08-25 09:20:22', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (3, '系统工具', 'M', 0, 'Layout', null, 'tool', 0, 0, '系统工具目录', 1, 0, 1, '2023-08-25 09:20:22', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (4, 'Bryce官网', 'M', 0, 'Layout', null, 'guide', 1, 0, 'Bryce官网地址', 1, 0, 1, '2023-08-25 09:20:22', null, null);
-- 二级菜单
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (100, '用户管理', 'M', 1, 'system/user/index', 'system:user:page', 'icon-user', 0, 1, '用户管理菜单', 1, 0, 1, '2023-08-25 10:28:33', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (101, '角色管理', 'M', 1, 'system/role/index', 'system:role:page', 'icon-team', 0, 2, '角色管理菜单', 1, 0, 1, '2023-08-25 10:28:33', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (102, '菜单管理', 'M', 1, 'system/menu/index', 'system:menu:page', 'tree-table', 0, 3, '菜单管理菜单', 1, 0, 1, '2023-08-25 10:28:33', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (103, '机构管理', 'M', 1, 'system/org/index', 'system:org:page', 'tree', 0, 4, '部门管理菜单', 1, 0, 1, '2023-08-25 10:28:33', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (104, '岗位管理', 'M', 1, '@/pages/system/SysPost', 'system:post:page', '', 0, 5, '岗位管理菜单', 1, 0, 1, '2023-08-25 10:34:47', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (105, '字典管理', 'M', 1, 'system/dict/index', 'system:dict:page', 'dict', 0, 6, '字典管理菜单', 1, 0, 1, '2023-08-25 10:32:30', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (106, '参数设置', 'M', 1, 'system/config/index', 'system:config:page', 'edit', 0, 7, '参数设置菜单', 1, 0, 1, '2023-08-25 10:35:53', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (107, '通知公告', 'M', 1, 'system/notice/index', 'system:notice:page', 'message', 0, 8, '通知公告菜单', 1, 0, 1, '2023-08-25 10:35:53', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (108, '日志管理', 'M', 1, '', '', 'log', 0, 9, '日志管理菜单', 1, 0, 1, '2023-08-25 10:38:47', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (109, '在线用户', 'M', 2, 'monitor/online/index', 'monitor:online:page', 'online', 0, 1, '在线用户菜单', 1, 0, 1, '2023-08-25 10:38:56', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (110, '定时任务', 'M', 2, 'monitor/job/index', 'monitor:job:page', 'job', 0, 2, '定时任务菜单', 1, 0, 1, '2023-08-25 10:38:56', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (111, '数据监控', 'M', 2, 'monitor/druid/index', 'monitor:druid:page', 'druid', 0, 3, '数据监控菜单', 1, 0, 1, '2023-08-25 10:38:56', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (112, '服务监控', 'M', 2, 'monitor/server/index', 'monitor:server:page', 'server', 0, 4, '服务监控菜单', 1, 0, 1, '2023-08-25 10:38:56', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (113, '缓存监控', 'M', 2, 'monitor/cache/index', 'monitor:cache:page', 'redis', 0, 5, '缓存监控菜单', 1, 0, 1, '2023-08-25 10:38:56', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (114, '缓存列表', 'M', 2, 'monitor/cache/list', 'monitor:cache:page', 'redis-list', 0, 6, '缓存列表菜单', 1, 0, 1, '2023-08-25 10:39:41', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (115, '表单构建', 'M', 3, 'tool/build/index', 'tool:build:page', 'build', 0, 1, '表单构建菜单', 1, 0, 1, '2023-08-25 10:39:47', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (116, '系统接口', 'M', 3, 'tool/swagger/index', 'tool:swagger:page', 'swagger', 0, 3, '系统接口菜单', 1, 0, 1, '2023-08-25 10:39:47', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (500, '操作日志', 'M', 108, 'monitor/operlog/index', 'monitor:operlog:page', 'form', 0, 1, '操作日志菜单', 1, 0, 1, '2023-08-25 10:39:47', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (501, '登录日志', 'M', 108, 'monitor/logininfor/index', 'monitor:logininfor:page', 'logininfor', 0, 2, '登录日志菜单', 1, 0, 1, '2023-08-25 10:39:47', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1000, '用户查询', 'B', 100, 'system:user:info', '', '', 0, 1, null, 1, 1, 1, '2023-08-25 10:46:43', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1001, '用户新增', 'B', 100, null, 'system:user:add', '#', 0, 2, null, 1, 0, 1, '2023-08-25 10:46:50', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1002, '用户修改', 'B', 100, null, 'system:user:update', '', 0, 3, null, 1, 0, 1, '2023-08-25 10:47:46', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1003, '用户删除', 'B', 100, null, 'system:user:delete', '', 0, 4, null, 1, 0, 1, '2023-08-25 10:48:57', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1004, '用户导出', 'B', 100, null, 'system:user:export', '', 0, 5, null, 1, 0, 1, '2023-08-25 10:48:57', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1005, '用户导入', 'B', 100, null, 'system:user:import', '', 0, 6, null, 1, 0, 1, '2023-08-25 10:48:57', null, null);
INSERT INTO bryce_boot.brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1006, '重置密码', 'B', 100, null, 'system:user:resetPwd', '', 0, 7, null, 1, 0, 1, '2023-08-25 10:48:57', null, null);

-- 6、系统用户与角色关系表
drop table if exists brc_sys_user_role;
create table brc_sys_user_role
(
    id              bigint   not null comment 'ID',
    user_id         bigint   not null comment '用户ID',
    role_id         bigint   not null comment '角色ID',
    version         int      null comment '版本号',
    deleted         tinyint  null comment '删除标识（0：存在，1：已删除）',
    created_user_id bigint   null comment '创建人ID',
    created_time    datetime null comment '创建时间',
    updated_user_id bigint   null comment '修改人ID',
    updated_time    datetime null comment '修改时间',
    primary key (id)
) engine InnoDB default charset utf8mb4 comment '系统用户角色关系';

create index idx_user_id on brc_sys_user_role (user_id);
create index idx_role_id on brc_sys_user_role (role_id);

-- 初始化-系统用户与角色关联表数据


-- 7、系统角色菜单关系表
drop table if exists brc_sys_role_menu;
create table brc_sys_role_menu
(
    id              bigint not null comment 'ID',
    role_id bigint not null comment '角色ID',
    menu_id bigint not null comment '菜单ID',
    version         int             comment '版本号',
    deleted         tinyint         comment '删除标识（0：存在，1：已删除）',
    created_user_id  bigint          comment '创建人ID',
    created_time     datetime        comment '创建时间',
    updated_user_id  bigint          comment '修改人ID',
    updated_time     datetime        comment '修改时间',
    primary key (id)
) engine InnoDB
  default charset utf8mb4 comment '系统角色菜单关系';

create index idx_role_id on brc_sys_role_menu (role_id);
create index idx_menu_id on brc_sys_role_menu (menu_id);
-- 初始化-系统角色菜单关系表数据
INSERT INTO bryce_boot.brc_sys_user_role (id, user_id, role_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, 1, 1, 1, 0, 1, null, null, null);
INSERT INTO bryce_boot.brc_sys_user_role (id, user_id, role_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, 2, 2, 1, 0, 1, null, null, null);

-- 8、系统角色与部门关联表
drop table if exists brc_sys_role_dept;
create table brc_sys_role_dept
(
    role_id bigint not null comment '角色ID',
    org_id bigint not null comment '部门ID',
    primary key (role_id, org_id)
) engine InnoDB
  default charset utf8mb4 comment '系统角色与部门关联表';

-- 初始化-系统角色与部门关联表数据
insert into brc_sys_role_dept
values ('2', '100');
insert into brc_sys_role_dept
values ('2', '101');
insert into brc_sys_role_dept
values ('2', '105');

-- 9、系统用户与岗位关联表
drop table if exists brc_sys_user_post;
create table brc_sys_user_post
(
    user_id bigint not null comment '用户ID',
    post_id bigint not null comment '岗位ID',
    primary key (user_id, post_id)
) engine InnoDB
  default charset utf8mb4 comment '系统用户与岗位关联表';

-- 初始化-系统用户与岗位关联表数据
insert into brc_sys_user_post
values ('1', '1');
insert into brc_sys_user_post
values ('2', '2');

-- 10、系统操作日志表
drop table if exists brc_sys_operate_log;
create table brc_sys_operate_log
(
    id                 bigint not null comment 'ID',
    name              varchar(50)   comment '操作名',
    module              varchar(50)   comment '模块名',
    request_uri      varchar(2048)  comment '请求URI',
    request_method     varchar(10)   comment '请求方法',
    request_param    text  comment '请求参数',
    result_message   varchar(500) comment '返回消息',
    operated_type      varchar(20)    comment '操作类型',
    operated_time     datetime      comment '操作时间',
    duration           int           comment '执行时长（毫秒）',
    status   tinyint    comment '操作状态（0：失败，1：成功）',
    user_agent varchar(500)  comment 'User Agent',
    ip       varchar(128)  comment '操作IP',
    location varchar(255)  comment '操作地点',
    user_id  bigint   comment '操作人ID',
    username varchar(50)   comment '操作人账号',
    org_id          bigint   comment '机构ID',
    tenant_id              bigint comment '租户ID',
    created_time     datetime      comment '创建时间',
    primary key (id)
) engine InnoDB
  default charset utf8mb4 comment '系统操作日志';

-- 11、系统字典类型表
drop table if exists brc_sys_dict_type;
create table brc_sys_dict_type
(
    id              bigint not null comment 'ID',
    dict_name       varchar(100)    comment '字典名称',
    dict_type       varchar(100)    comment '字典类型',
    sort            int         default '0' comment '显示顺序',
    status          tinyint      default '1' comment '状态（0：停用，1：正常）',
    remark          varchar(500)    comment '备注',
    tenant_id       bigint          comment '租户ID',
    version         int             comment '版本号',
    deleted         tinyint         comment '删除标识（0：存在，1：已删除）',
    created_user_id  bigint          comment '创建人ID',
    created_time     datetime        comment '创建时间',
    updated_user_id  bigint          comment '修改人ID',
    updated_time     datetime        comment '修改时间',
    primary key (id),
    constraint uk_dict_type unique (dict_type)
) engine InnoDB default charset utf8mb4 comment '系统字典类型';


-- 初始化-系统字典类型表数据
INSERT INTO bryce_boot.brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '用户性别', 'sys_user_gender', null, 1, '用户性别列表', null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '菜单状态', 'sys_show_hide', null, 1, '菜单状态列表', null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (3, '系统开关', 'sys_normal_disable', null, 1, '系统开关列表', null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (4, '任务状态', 'sys_job_status', null, 1, '任务状态列表', null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (5, '任务分组', 'sys_job_group', null, 1, '任务分组列表', null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (6, '系统是否', 'sys_yes_no', null, 1, '系统是否列表', null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (7, '通知类型', 'sys_notice_type', null, 1, '通知类型列表', null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (8, '通知状态', 'sys_notice_status', null, 1, '通知状态列表', null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (9, '操作类型', 'sys_operate_type', null, 1, '操作类型列表', null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (10, '系统状态', 'sys_common_status', null, 1, '登录状态列表', null, null, null, 1, '2023-08-25 12:45:16', null, null);

-- 12、系统字典数据表
drop table if exists brc_sys_dict_data;
create table brc_sys_dict_data
(
    id              bigint not null comment 'ID',
    dict_label      varchar(100)    comment '字典标签',
    dict_value      varchar(100)    comment '字典值',
    dict_type_id    bigint    comment '字典类型',
    label_class       varchar(100)    comment '标签属性',
    sort            int         default '0' comment '显示顺序',
    status          tinyint      default '1' comment '状态（0：停用，1：正常）',
    remark          varchar(500)    comment '备注',
    tenant_id       bigint          comment '租户ID',
    version         int             comment '版本号',
    deleted         tinyint         comment '删除标识（0：存在，1：已删除）',
    created_user_id  bigint          comment '创建人ID',
    created_time     datetime        comment '创建时间',
    updated_user_id  bigint          comment '修改人ID',
    updated_time     datetime        comment '修改时间',
    primary key (id)
) engine InnoDB
  default charset utf8mb4 comment '系统字典数据';

-- 初始化-系统字典数据表数据
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '男', '0', null, '', 1, '', 1, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '女', '1', null, '', 0, '', 2, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (3, '未知', '2', null, '', 0, '', 3, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (4, '显示', '0', '2', 'primary', 1, '', 1, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (5, '隐藏', '1', '2', 'danger', 0, '', 2, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (6, '正常', '0', '3', 'primary', 1, '', 1, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (7, '停用', '1', '3', 'danger', 0, '', 2, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (8, '正常', '0', '4', 'primary', 1, '', 1, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (9, '暂停', '1', '4', 'danger', 0, '', 2, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (10, '默认', 'DEFAULT', '5', '', 1, '', 1, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (11, '系统', 'SYSTEM', '5', '', 0, '', 2, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (12, '是', 'Y', '6', 'primary', 1, '', 1, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (13, '否', 'N', '6', 'danger', 0, '', 2, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (14, '通知', '1', '7', 'warning', 1, '', 1, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (15, '公告', '2', '7', 'success', 0, '', 2, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (16, '正常', '0', '8', 'primary', 1, '', 1, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (17, '关闭', '1', '8', 'danger', 0, '', 2, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (18, '其他', '0', '9', 'info', 0, '', 99, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (19, '新增', '1', '9', 'info', 0, '', 1, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (20, '修改', '2', '9', 'info', 0, '', 2, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (21, '删除', '3', '9', 'danger', 0, '', 3, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (22, '授权', '4', '9', 'primary', 0, '', 4, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (23, '导出', '5', '9', 'warning', 0, '', 5, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (24, '导入', '6', '9', 'warning', 0, '', 6, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (25, '强退', '7', '9', 'danger', 0, '', 7, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (26, '生成代码', '8', '9', 'warning', 0, '', 8, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (27, '清空数据', '9', '9', 'danger', 0, '', 9, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (28, '成功', '0', '10', 'primary', 0, '', 1, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);
INSERT INTO bryce_boot.brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, `default`, remark, sort, status, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (29, '失败', '1', '10', 'danger', 0, '', 2, 1, null, null, null, 1, '2023-08-25 12:45:16', null, null);

-- 13、系统参数表
drop table if exists brc_sys_param;
create table brc_sys_param
(
    id              bigint       not null comment 'ID'

    name            varchar(100) null comment '参数名称',
    built_in        tinyint      null comment '是否系统内置（1：是，0：否）',
    `key`           varchar(100) null comment '参数键',
    value           text         null comment '参数值',
    remark          varchar(500) null comment '备注',
    tenant_id       bigint       null comment '租户ID',
    version         int          null comment '版本号',
    deleted         tinyint      null comment '删除标识（0：存在，1：已删除）',
    created_user_id bigint       null comment '创建人ID',
    created_time    datetime     null comment '创建时间',
    updated_user_id bigint       null comment '修改人ID',
    updated_time    datetime     null comment '修改时间',
    primary key(id),
    constraint uk_key unique (`key`)
) comment '系统参数';

-- 初始化-系统配置表数据
insert into brc_sys_config
values (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', '1', '1', sysdate(), null, null,
        '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
insert into brc_sys_config
values (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', '1', '1', sysdate(), null, null, '初始化密码 123456');
insert into brc_sys_config
values (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', '1', '1', sysdate(), null, null,
        '深色主题theme-dark，浅色主题theme-light');
insert into brc_sys_config
values (4, '账号自助-图片验证码开关', 'sys.account.captchaEnabled', 'false', '1', '1', sysdate(), null, null,
        '是否开启图片验证码功能（true开启，false关闭）');
insert into brc_sys_config
values (5, '账号自助-短信验证码开关', 'sys.account.smsEnabled', 'true', '1', '1', sysdate(), null, null,
        '是否开启短信验证码功能（优先级高于图片验证码，true开启，false关闭）');
insert into brc_sys_config
values (6, '账号自助-用户注册功能开关', 'sys.account.registerEnabled', 'false', '1', '1', sysdate(), null, null,
        '是否开启注册用户功能（true开启，false关闭）');

-- 14、系统登录日志表
drop table if exists brc_sys_login_log;
create table brc_sys_login_log
(
    id             bigint not null comment 'ID',
    username       varchar(50)  comment '用户账号',
    message        varchar(255) comment '操作消息',
    ip              varchar(128) comment '登录IP地址',
    location        varchar(255) comment '登录地点',
    browser        varchar(50)  comment '浏览器类型',
    os             varchar(50)  comment '操作系统',
    user_agent        varchar(50)  comment 'User Agent',
    status         tinyint comment '状态（0：失败，1：成功）',
    access_time     datetime     comment '访问时间',
    tenant_id     bigint     comment '租户ID',
    created_time     datetime     comment '创建时间',
    primary key (id)
) engine InnoDB default charset utf8mb4 comment '系统登录日志';

-- 15、系统定时任务调度表
drop table if exists brc_sys_job;
create table brc_sys_job
(
    id                   bigint not null comment 'ID',
    job_name             varchar(50)     comment '任务名称',
    job_group            varchar(50)     default 'DEFAULT' comment '任务组名',
    invoke_target        varchar(500)    not null comment '调用目标字符串',
    cron_expression      varchar(255)    comment 'cron执行表达式',
    execute_error_policy tinyint      default '3' comment '计划执行错误策略（1：立即执行，2：执行一次，3：放弃执行）',
    is_concurrent        tinyint      default '0' comment '是否并发执行（0：否，1：是）',
    sort                 int         default '0' comment '显示顺序',
    status               tinyint      default '1' comment '状态（0：停用，1：正常）',
    created_user_id       bigint comment '创建人ID',
    create_username      varchar(50)     comment '创建人账号',
    created_time          datetime        comment '创建时间',
    updated_user_id       bigint comment '修改人ID',
    updated_time          datetime        comment '修改时间',
    remark               varchar(500)    comment '备注',
    primary key (id)
) engine InnoDB
  default charset utf8mb4 comment '系统定时任务调度表';

-- 初始化-系统定时任务调度表数据
insert into brc_sys_job
values (1, '系统默认（无参）', 'DEFAULT', 'brcTask.brcNoParams', '0/10 * * * * ?', '3', '0', '1', '0', '1', sysdate(),
        null, null, '');
insert into brc_sys_job
values (2, '系统默认（有参）', 'DEFAULT', 'brcTask.brcParams(\'brc\')', '0/15 * * * * ?', '3', '0', '2', '0', '1', 'admin',
        sysdate(), null, null, '');
insert into brc_sys_job
values (3, '系统默认（多参）', 'DEFAULT', 'brcTask.brcMultipleParams(\'brc\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?',
        '3', '0', '3', '0', '1', sysdate(), null, null, '');

-- 16、系统定时任务调度日志表
drop table if exists brc_sys_job_log;
create table brc_sys_job_log
(
    id             bigint not null comment 'ID',
    job_name       varchar(64)     not null comment '任务名称',
    job_group      varchar(64)     not null comment '任务组名',
    invoke_target  varchar(500)    not null comment '调用目标字符串',
    job_message    varchar(500) comment '日志信息',
    execute_status tinyint    default '0' comment '执行状态（0：失败，1：正常）',
    exception_info varchar(2000) comment '异常信息',
    created_time    datetime      comment '创建时间',
    primary key (id)
) engine InnoDB
  default charset utf8mb4 comment '系统定时任务调度日志表';

-- 17、系统通知公告表
drop table if exists brc_sys_notice;
create table brc_sys_notice
(
    id              bigint not null comment 'ID',
    notice_title    varchar(50)     not null comment '公告标题',
    notice_content  longtext        comment '公告内容',
    notice_type     tinyint      not null comment '公告类型（0：通知，1：公告）',
    status          tinyint      default '1' comment '状态（0：关闭，1：正常）',
    created_user_id  bigint comment '创建人ID',
    created_time     datetime        comment '创建时间',
    updated_user_id  bigint comment '修改人ID',
    updated_time     datetime        comment '修改时间',
    remark          varchar(500)    comment '备注',
    primary key (id)
) engine InnoDB
  default charset utf8mb4 comment '系统通知公告表';

-- 初始化-公告信息表数据
insert into brc_sys_notice
values ('1', '温馨提醒：2022-10-01 Bryce新版本发布啦', '新版本内容', '2', '1', '1', sysdate(), null, null, '管理员');
insert into brc_sys_notice
values ('2', '维护通知：2022-10-01 Bryce系统凌晨维护', '维护内容', '1', '1', '1', sysdate(), null, null, '管理员');

-- 18、租户表
drop table if exists brc_tenant;
create table brc_tenant
(
    id              bigint     not null comment 'ID',
    name            varchar(100)    not null comment '租户名称',
    site_domain     varchar(200)    comment '租户域名',
    site_url        varchar(100)    comment '租户网址',
    site_logo       varchar(200)    comment '租户网址logo',
    site_config     text            comment '租户网址配置',
    admin_id        bigint     not null comment '管理员ID',
    created_user_id  bigint     comment '创建人ID',
    created_time     datetime        comment '创建时间',
    updated_user_id  bigint     comment '修改人ID',
    updated_time     datetime        comment '修改时间',
    primary key (id),
    unique key unique_site_domain (site_domain)
) engine InnoDB default charset utf8mb4 comment '租户表';

-- 19、附件表
drop table if exists brc_sys_upload_file;
CREATE TABLE `brc_sys_upload_file`
(
    `id`            bigint not null comment 'ID',
    `old_name`      varchar(100) comment '文件原始名称',
    `new_path`      varchar(255) comment '文件路径',
    `file_type`     varchar(50)  comment '文件类型',
    `suffix`        varchar(10)  comment '文件名后缀',
    `hash`          varchar(255) comment '哈希码',
    `size`          bigint       comment '文件大小（单位字节）',
    `width`         int          DEFAULT '0' comment '宽',
    `height`        int          DEFAULT '0' comment '高',
    `lng`           varchar(30)  comment '经度',
    `lat`           varchar(30)  comment '纬度',
    `tags`          varchar(255) comment '标签',
    `sort`          int          DEFAULT '0' comment '排序',
    `version`       int          DEFAULT 1 comment '版本',
    created_user_id  bigint comment '创建用户ID',
    created_time     datetime comment '创建时间',
    primary key (id),
    key bryce_file_sort_index (sort) comment '排序索引',
    key bryce_file_created_user_id_index (created_user_id) comment '创建用户ID索引',
    key bryce_file_created_time_index (created_time) comment '创建时间索引'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_general_ci comment '上传文件表';







