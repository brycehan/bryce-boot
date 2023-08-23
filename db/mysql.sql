create database if not exists bryce_boot default charset utf8mb4;
use bryce_boot;

-- 1、系统部门表
drop table if exists brc_sys_dept;
create table brc_sys_dept
(
    id              varchar(36)     not null comment 'ID',
    dept_name       varchar(100)    not null comment '部门名称',
    dept_code       varchar(30)     default null comment '部门编码',
    parent_id       varchar(36)     default '0' comment '父部门ID',
    ancestor        varchar(255)    default '' comment '祖级部门列表',
    leader          varchar(50)     default null comment '负责人',
    contact_number  varchar(20)     default null comment '联系电话',
    email           varchar(50)     default null comment '邮箱',
    sort            integer         default '0' comment '显示顺序',
    status          tinyint(1)      default '1' comment '状态（0：停用，1：正常）',
    delete_flag     tinyint(1)      default '0' comment '删除标志（0：存在，1：删除）',
    create_user_id  varchar(36)     default null comment '创建人ID',
    create_username varchar(50)     default '' comment '创建人账号',
    create_time     datetime        default null comment '创建时间',
    update_user_id  varchar(36)     default null comment '修改人ID',
    update_time     datetime        default null comment '修改时间',
    remark          varchar(500)    default null comment '备注',
    primary key (id),
    constraint brc_sys_dept_status_check check (status in ('0', '1'))
) engine InnoDB
  default charset utf8mb4 comment '系统部门表';

-- 初始化-系统部门表数据
insert into brc_sys_dept
values (100, 'Bryce科技', null, 0, '0', '韩先生', '15800008888', 'brycehan@163.com', 0, 0, 0, 1, 'admin', sysdate(), null,
        null, null);
insert into brc_sys_dept
values (101, '北京总公司', null, 100, '0,100', '韩先生', '15800008888', 'brycehan@163.com', 1, 0, 0, 1, 'admin', sysdate(),
        null, null, null);
insert into brc_sys_dept
values (102, '济南分公司', null, 100, '0,100', '韩先生', '15800008888', 'brycehan@163.com', 2, 0, 0, 1, 'admin', sysdate(),
        null, null, null);
insert into brc_sys_dept
values (103, '研发部门', null, 101, '0,100,101', '韩先生', '15800008888', 'brycehan@163.com', 1, 0, 0, 1, 'admin', sysdate(),
        null, null, null);
insert into brc_sys_dept
values (104, '市场部门', null, 101, '0,100,101', '韩先生', '15800008888', 'brycehan@163.com', 2, 0, 0, 1, 'admin', sysdate(),
        null, null, null);
insert into brc_sys_dept
values (105, '测试部门', null, 101, '0,100,101', '韩先生', '15800008888', 'brycehan@163.com', 3, 0, 0, 1, 'admin', sysdate(),
        null, null, null);
insert into brc_sys_dept
values (106, '财务部门', null, 101, '0,100,101', '韩先生', '15800008888', 'brycehan@163.com', 4, 0, 0, 1, 'admin', sysdate(),
        null, null, null);
insert into brc_sys_dept
values (107, '运维部门', null, 101, '0,100,101', '韩先生', '15800008888', 'brycehan@163.com', 5, 0, 0, 1, 'admin', sysdate(),
        null, null, null);
insert into brc_sys_dept
values (108, '市场部门', null, 102, '0,100,102', '韩先生', '15800008888', 'brycehan@163.com', 1, 0, 0, 1, 'admin', sysdate(),
        null, null, null);
insert into brc_sys_dept
values (109, '财务部门', null, 102, '0,100,102', '韩先生', '15800008888', 'brycehan@163.com', 2, 0, 0, null, 'admin',
        sysdate(), null, null, null);

-- 2、系统用户表
drop table if exists brc_sys_user;
create table brc_sys_user
(
    id                 varchar(36)     not null comment 'ID',
    username           varchar(50)     not null comment '账号',
    password           varchar(255)    default null comment '密码',
    full_name          varchar(50)     default '' comment '姓名',
    avatar             varchar(100)    default null comment '头像地址',
    gender             char(1)         default 'M' comment '性别（M：男, F：女）',
    user_type          tinyint(1)      default '0' comment '用户类型（0：系统用户）',
    dept_id            varchar(36)     default null comment '部门ID',
    phone              varchar(20)     default '' comment '手机号码',
    email              varchar(50)     default null comment '邮箱',
    account_non_locked tinyint(1)      default '1' comment '账号锁定状态（0：锁定，1：正常）',
    last_login_ip      varchar(128)    default '' comment '最后登录IP',
    last_login_time    datetime        default null comment '最后登录时间',
    sort               integer         default '0' comment '显示顺序',
    status             tinyint(1)      default '1' comment '状态（0：停用，1：正常）',
    delete_flag        tinyint(1)      default '0' comment '删除标志（0：存在，1：删除）',
    create_user_id     varchar(36)     default null comment '创建人ID',
    create_username    varchar(50)     default '' comment '创建人账号',
    create_time        datetime        default null comment '创建时间',
    update_user_id     varchar(36) default null comment '修改人ID',
    update_time        datetime        default null comment '修改时间',
    remark             varchar(500)    default null comment '备注',
    primary key (id),
    constraint brc_sys_user_gender_check check (gender in ('M', 'F')),
    constraint brc_sys_user_non_locked_check check (account_non_locked in ('0', '1'))
) engine InnoDB
  default charset utf8mb4 comment '系统用户表';

-- 初始化-系统用户表数据
insert into brc_sys_user
values (1, 'admin', '$2a$10$H/0p9EJPQjYAspbCO85QzuDXs4v36TvdWftjx1HJSWhVoSQ85GtHi', '管理员', null, 'M', 0, 103,
        '15800008888', 'brycehan@163.com', 1, null, NULL, '0', '1', '0', '1', 'admin', sysdate(), NULL, NULL, '管理员');
insert into brc_sys_user
values (2, 'brycehan', '$2a$10$H/0p9EJPQjYAspbCO85QzuDXs4v36TvdWftjx1HJSWhVoSQ85GtHi', 'Bryce Han', null, 'M', 0, 105,
        '15800008888', 'brycehan7@gmail.com', 1, null, NULL, '0', '1', '0', '1', 'admin', sysdate(), NULL, NULL, '测试员');

-- 3、系统岗位表
drop table if exists brc_sys_post;
create table brc_sys_post
(
    id              varchar(36) not null comment 'ID',
    post_code       varchar(30)     not null comment '岗位编码',
    post_name       varchar(50)     not null comment '岗位名称',
    sort_number     integer         default '0' comment '显示顺序',
    status          tinyint(1)      default '1' comment '状态（0：停用，1：正常）',
    create_user_id  varchar(36) default null comment '创建人ID',
    create_username varchar(50)     default '' comment '创建人账号',
    create_time     datetime        default null comment '创建时间',
    update_user_id  varchar(36) default null comment '修改人ID',
    update_time     datetime        default null comment '修改时间',
    remark          varchar(500)    default null comment '备注',
    primary key (id)
) engine InnoDB
  default charset utf8mb4 comment '系统岗位表';

-- 初始化-系统岗位表数据
insert into brc_sys_post
values (1, 'ceo', '董事长', 1, '1', '1', 'admin', sysdate(), null, null, null);
insert into brc_sys_post
values (2, 'se', '项目经理', 2, '1', '1', 'admin', sysdate(), null, null, null);
insert into brc_sys_post
values (3, 'hr', '人力资源', 3, '1', '1', 'admin', sysdate(), null, null, null);
insert into brc_sys_post
values (4, 'user', '普通员工', 4, '1', '1', 'admin', sysdate(), null, null, null);

-- 4、系统角色表
drop table if exists brc_sys_role;
create table brc_sys_role
(
    id                         varchar(36) not null comment 'ID',
    role_name                  varchar(50)     not null comment '角色名称',
    role_code                  varchar(50)     not null comment '角色编码',
    data_scope                 tinyint(1)      default '1' comment '数据范围（1：全部数据权限，2：自定数据权限，3：本部门数据权限，4：本部门及以下数据权限）',
    menu_association_displayed tinyint(1)      default '1' comment '菜单树父子选择项是否关联显示（1：是，0：否）',
    dept_association_displayed tinyint(1)      default '1' comment '部门树父子选择项是否关联显示（1：是，0：否）',
    delete_flag                tinyint(1)      default '0' comment '状态（0：正式数据，1：删除）',
    sort                       integer         default '0' comment '显示顺序',
    status                     tinyint(1)      default '1' comment '状态（0：停用，1：正常）',
    create_user_id             varchar(36) default null comment '创建人ID',
    create_username            varchar(50)     default '' comment '创建人账号',
    create_time                datetime        default null comment '创建时间',
    update_user_id             varchar(36) default null comment '修改人ID',
    update_time                datetime        default null comment '修改时间',
    remark                     varchar(500)    default null comment '备注',
    primary key (id),
    constraint brc_sys_role_delete_flag_check check (delete_flag in ('0', '1')),
    constraint brc_sys_role_status_check check (status in ('0', '1'))
) engine InnoDB
  default charset utf8mb4 comment '系统角色表';

-- 初始化-系统角色表数据
insert into brc_sys_role
values ('1', '超级管理员', 'admin', 1, 1, 1, '0', '0', '1', '1', 'admin', sysdate(), null, null, '超级管理员');
insert into brc_sys_role
values ('2', '默认角色', 'default', 2, 0, 1, '0', '0', '1', '1', 'admin', sysdate(), null, null, '默认角色');

-- 5、系统菜单表
drop table if exists brc_sys_menu;
create table brc_sys_menu
(
    id              varchar(36) not null comment 'ID',
    menu_name       varchar(50)     not null comment '菜单名称',
    menu_type       char(1)         default null comment '类型（D：目录，M：菜单，B：按钮）',
    parent_id       varchar(36) default '0' comment '父菜单ID，一级菜单为0',
    icon            varchar(100)    default '#' comment '菜单图标',
    url       varchar(255)    default null comment '组件路径',
    query           varchar(255)    default null comment '路由参数',
    is_frame        tinyint(1)      default '0' comment '是否为外链（0：否，1：是）',
    is_cache        tinyint(1)      default '1' comment '是否缓存（0：否，1：是）',
    visible         tinyint(1)      default '1' comment '菜单状态（0：隐藏，1：显示）',
    permission      varchar(100)    default null comment '权限标识',
    delete_flag     tinyint(1)      default '0' comment '状态（0：正式数据，1：删除）',
    sort_number     integer         default '0' comment '显示顺序',
    status          tinyint(1)      default '1' comment '状态（0：停用，1：正常）',
    create_user_id  varchar(36) default null comment '创建人ID',
    create_time     datetime        default null comment '创建时间',
    update_user_id  varchar(36) default null comment '修改人ID',
    update_time     datetime        default null comment '修改时间',
    remark          varchar(500)    default null comment '备注',
    primary key (id),
    constraint brc_sys_menu_status_check check (status in ('0', '1'))
) engine InnoDB
  default charset utf8mb4 comment '系统菜单表';

-- 初始化-系统菜单表数据
-- 一级菜单
insert into brc_sys_menu
values ('1', '系统管理', 'D', '0', 'icon-setting', null, '', '0', '1', '1', null, '0', '0', '1', '1', sysdate(), null, null, '系统管理目录');
insert into brc_sys_menu
values ('2', '系统监控', 'D', '0', 'monitor', 'Layout', '', '0', '1', '1', null, '0', '0', '1', '1', sysdate(), null, null, '系统监控目录');
insert into brc_sys_menu
values ('3', '系统工具', 'D', '0', 'tool', 'Layout', '', '0', '1', '1', null, '0', '0', '1', '1',
        sysdate(), null, null, '系统工具目录');
insert into brc_sys_menu
values ('4', 'Bryce官网', 'D', '0', 'guide', 'Layout', '', '1', '1', '1', null, '0', '0', '1', '1',
         sysdate(), null, null, 'Bryce官网地址');
-- 二级菜单
insert into brc_sys_menu
values ('100', '用户管理', 'M', '1', 'icon-user', 'system/user/index', null, 0, '1', '1',
        'system:user:page', '0', '1', '1', '1', 'admin', sysdate(), null, null, '用户管理菜单');
insert into brc_sys_menu
values ('101', '角色管理', 'M', '1', 'icon-team',  'system/role/index', null, 0, '1', '1', 'system:role:page', '0',
        '2', '1', '1', 'admin', sysdate(), null, null, '角色管理菜单');
insert into brc_sys_menu
values ('102', '菜单管理', 'M', '1', 'tree-table', 'system/menu/index', null, 0, '1', '1', 'system:menu:page', '0',
        '3', '1', '1', 'admin', sysdate(), null, null, '菜单管理菜单');
insert into brc_sys_menu
values ('103', '部门管理', 'M', '1', 'tree',  'system/dept/index', null, 0, '1', '1', 'system:dept:page', '0', '4',
        '1', '1', 'admin', sysdate(), null, null, '部门管理菜单');
insert into brc_sys_menu
values ('104', '岗位管理', 'M', '1', '', '@/pages/system/SysPost', null, 0, '1', '1', 'system:post:page', '0', '5',
        '1', '1', 'admin', sysdate(), null, null, '岗位管理菜单');
insert into brc_sys_menu
values ('105', '字典管理', 'M', '1', 'dict', 'system/dict/index', null, 0, '1', '1', 'system:dict:page', '0', '6',
        '1', '1', 'admin', sysdate(), null, null, '字典管理菜单');
insert into brc_sys_menu
values ('106', '参数设置', 'M', '1', 'edit', 'system/config/index', null, 0, '1', '1', 'system:config:page', '0',
        '7', '1', '1', 'admin', sysdate(), null, null, '参数设置菜单');
insert into brc_sys_menu
values ('107', '通知公告', 'M', '1', 'message', 'system/notice/index', null, 0, '1', '1', 'system:notice:page',
        '0', '8', '1', '1', 'admin', sysdate(), null, null, '通知公告菜单');
insert into brc_sys_menu
values ('108', '日志管理', 'M', '1', 'log', '', null, 0, '1', '1', '', '0', '9', '1', '1', 'admin', sysdate(), null,
        null, '日志管理菜单');
insert into brc_sys_menu
values ('109', '在线用户', 'M', '2', 'online', 'monitor/online/index', null, 0, '1', '1', 'monitor:online:page',
        '0', '1', '1', '1', 'admin', sysdate(), null, null, '在线用户菜单');
insert into brc_sys_menu
values ('110', '定时任务', 'M', '2', 'job',  'monitor/job/index', null, 0, '1', '1', 'monitor:job:page', '0', '2',
        '1', '1', 'admin', sysdate(), null, null, '定时任务菜单');
insert into brc_sys_menu
values ('111', '数据监控', 'M', '2', 'druid', 'monitor/druid/index', null, 0, '1', '1', 'monitor:druid:page', '0',
        '3', '1', '1', 'admin', sysdate(), null, null, '数据监控菜单');
insert into brc_sys_menu
values ('112', '服务监控', 'M', '2', 'server',  'monitor/server/index', null, 0, '1', '1', 'monitor:server:page',
        '0', '4', '1', '1', 'admin', sysdate(), null, null, '服务监控菜单');
insert into brc_sys_menu
values ('113', '缓存监控', 'M', '2', 'redis',  'monitor/cache/index', null, 0, '1', '1', 'monitor:cache:page', '0',
        '5', '1', '1', 'admin', sysdate(), null, null, '缓存监控菜单');
insert into brc_sys_menu
values ('114', '缓存列表', 'M', '2', 'redis-list',  'monitor/cache/list', null, 0, '1', '1',
        'monitor:cache:page', '0', '6', '1', '1', 'admin', sysdate(), null, null, '缓存列表菜单');
insert into brc_sys_menu
values ('115', '表单构建', 'M', '3', 'build', 'tool/build/index', null, 0, '1', '1', 'tool:build:page', '0', '1',
        '1', '1', 'admin', sysdate(), null, null, '表单构建菜单');
insert into brc_sys_menu
values ('116', '代码生成', 'M', '3', 'code', 'tool/gen/index', null, 0, '1', '1', 'tool:gen:page', '0', '2', '1',
        '1', 'admin', sysdate(), null, null, '代码生成菜单');
insert into brc_sys_menu
values ('117', '系统接口', 'M', '3', 'swagger', 'tool/swagger/index', null, 0, '1', '1', 'tool:swagger:page',
        '0', '3', '1', '1', 'admin', sysdate(), null, null, '系统接口菜单');
-- 三级菜单
insert into brc_sys_menu
values ('500', '操作日志', 'M', '108', 'form', 'monitor/operlog/index', null, 0, '1', '1',
        'monitor:operlog:page', '0', '1', '1', '1', 'admin', sysdate(), null, null, '操作日志菜单');
insert into brc_sys_menu
values ('501', '登录日志', 'M', '108', 'logininfor', 'monitor/logininfor/index', null, 0, '1', '1',
        'monitor:logininfor:page', '0', '2', '1', '1', 'admin', sysdate(), null, null, '登录日志菜单');
-- 用户管理按钮
insert into brc_sys_menu
values ('1000', '用户查询', 'B', '100', '#', null, null, '0', '1', '1', 'system:user:info', '0', '1', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1001', '用户新增', 'B', '100', '#',null, null, '0', '1', '1', 'system:user:add', '0', '2', '1', '1', 'admin',
        sysdate(), null, null, null);
insert into brc_sys_menu
values ('1002', '用户修改', 'B', '100', '#', null, null, '0', '1', '1', 'system:user:update', '0', '3', '1', '1', 'admin',
        sysdate(), null, null, null);
insert into brc_sys_menu
values ('1003', '用户删除', 'B', '100', '#', null, null, '0', '1', '1', 'system:user:delete', '0', '4', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1004', '用户导出', 'B', '100', '#', null, null, '0', '1', '1', 'system:user:export', '0', '5', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1005', '用户导入', 'B', '100', '#', null, null, '0', '1', '1', 'system:user:import', '0', '6', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1006', '重置密码', 'B', '100', '#', null, null, '0', '1', '1', 'system:user:resetPwd', '0', '7', '1', '1',
        'admin', sysdate(), null, null, null);
-- 角色管理按钮
insert into brc_sys_menu
values ('1007', '角色查询', 'B', '101', '#', null, null, '0', '1', '1', 'system:role:info', '0', '1', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1008', '角色新增', 'B', '101', '#', null, null, '0', '1', '1', 'system:role:add', '0', '2', '1', '1', 'admin',
        sysdate(), null, null, null);
insert into brc_sys_menu
values ('1009', '角色修改', 'B', '101', '#', null, null, '0', '1', '1', 'system:role:update', '0', '3', '1', '1', 'admin',
        sysdate(), null, null, null);
insert into brc_sys_menu
values ('1010', '角色删除', 'B', '101', '#', null, null, '0', '1', '1', 'system:role:delete', '0', '4', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1011', '角色导出', 'B', '101', '#', null, null, '0', '1', '1', 'system:role:export', '0', '5', '1', '1',
        'admin', sysdate(), null, null, null);
-- 菜单管理按钮
insert into brc_sys_menu
values ('1012', '菜单查询', 'B', '102', '#', null, null, '0', '1', '1', 'system:menu:info', '0', '1', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1013', '菜单新增', 'B', '102', '#', null, null, '0', '1', '1', 'system:menu:add', '0', '2', '1', '1', 'admin',
        sysdate(), null, null, null);
insert into brc_sys_menu
values ('1014', '菜单修改', 'B', '102', '#', null, null, '0', '1', '1', 'system:menu:update', '0', '3', '1', '1', 'admin',
        sysdate(), null, null, null);
insert into brc_sys_menu
values ('1015', '菜单删除', 'B', '102', '#', null, null, '0', '1', '1', 'system:menu:delete', '0', '4', '1', '1',
        'admin', sysdate(), null, null, null);
-- 部门管理按钮
insert into brc_sys_menu
values ('1016', '部门查询', 'B', '103', '#', null, null, '0', '1', '1', 'system:dept:info', '0', '1', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1017', '部门新增', 'B', '103', '#', null, null, '0', '1', '1', 'system:dept:add', '0', '2', '1', '1', 'admin',
        sysdate(), null, null, null);
insert into brc_sys_menu
values ('1018', '部门修改', 'B', '103', '#', null, null, '0', '1', '1', 'system:dept:update', '0', '3', '1', '1', 'admin',
        sysdate(), null, null, null);
insert into brc_sys_menu
values ('1019', '部门删除', 'B', '103', '#', null, null, '0', '1', '1', 'system:dept:delete', '0', '4', '1', '1',
        'admin', sysdate(), null, null, null);
-- 岗位管理按钮
insert into brc_sys_menu
values ('1020', '岗位查询', 'B', '104', '#', null, null, '0', '1', '1', 'system:post:info', '0', '1', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1021', '岗位新增', 'B', '104', '#', null, null, '0', '1', '1', 'system:post:add', '0', '2', '1', '1', 'admin',
        sysdate(), null, null, null);
insert into brc_sys_menu
values ('1022', '岗位修改', 'B', '104', '#', null, null, '0', '1', '1', 'system:post:update', '0', '3', '1', '1', 'admin',
        sysdate(), null, null, null);
insert into brc_sys_menu
values ('1023', '岗位删除', 'B', '104', '#', null, null, '0', '1', '1', 'system:post:delete', '0', '4', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1024', '岗位导出', 'B', '104', '#', null, null, '0', '1', '1', 'system:post:export', '0', '5', '1', '1',
        'admin', sysdate(), null, null, null);
-- 字典管理按钮
insert into brc_sys_menu
values ('1025', '字典查询', 'B', '105', '#', null, null, '0', '1', '1', 'system:dict:info', '0', '1', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1026', '字典新增', 'B', '105', '#', null, null, '0', '1', '1', 'system:dict:add', '0', '2', '1', '1', 'admin',
        sysdate(), null, null, null);
insert into brc_sys_menu
values ('1027', '字典修改', 'B', '105', '#', null, null, '0', '1', '1', 'system:dict:update', '0', '3', '1', '1', 'admin',
        sysdate(), null, null, null);
insert into brc_sys_menu
values ('1028', '字典删除', 'B', '105', '#', null, null, '0', '1', '1', 'system:dict:delete', '0', '4', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1029', '字典导出', 'B', '105', '#', null, null, '0', '1', '1', 'system:dict:export', '0', '5', '1', '1',
        'admin', sysdate(), null, null, null);
-- 参数设置按钮
insert into brc_sys_menu
values ('1030', '参数查询', 'B', '106', '#', null, null, '0', '1', '1', 'system:config:info', '0', '1', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1031', '参数新增', 'B', '106', '#', null, null, '0', '1', '1', 'system:config:add', '0', '2', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1032', '参数修改', 'B', '106', '#', null, null, '0', '1', '1', 'system:config:update', '0', '3', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1033', '参数删除', 'B', '106', '#', null, null, '0', '1', '1', 'system:config:delete', '0', '4', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1034', '参数导出', 'B', '106', '#', null, null, '0', '1', '1', 'system:config:export', '0', '5', '1', '1',
        'admin', sysdate(), null, null, null);
-- 通知公告按钮
insert into brc_sys_menu
values ('1035', '公告查询', 'B', '107', '#', null, null, '0', '1', '1', 'system:notice:info', '0', '1', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1036', '公告新增', 'B', '107', '#', null, null, '0', '1', '1', 'system:notice:add', '0', '2', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1037', '公告修改', 'B', '107', '#', null, null, '0', '1', '1', 'system:notice:update', '0', '3', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1038', '公告删除', 'B', '107', '#', null, null, '0', '1', '1', 'system:notice:delete', '0', '4', '1', '1',
        'admin', sysdate(), null, null, null);
-- 操作日志按钮
insert into brc_sys_menu
values ('1039', '操作查询', 'B', '500', '#', null, null, '0', '1', '1', 'monitor:operlog:info', '0', '1', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1040', '操作删除', 'B', '500', '#', null, null, '0', '1', '1', 'monitor:operlog:delete', '0', '2', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1041', '日志导出', 'B', '500', '#', null, null, '0', '1', '1', 'monitor:operlog:export', '0', '3', '1', '1',
        'admin', sysdate(), null, null, null);
-- 登录日志按钮
insert into brc_sys_menu
values ('1042', '登录查询', 'B', '501', '#', null, null, '0', '1', '1', 'monitor:logininfor:info', '0', '1', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1043', '登录删除', 'B', '501', '#', null, null, '0', '1', '1', 'monitor:logininfor:delete', '0', '2', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1044', '日志导出', 'B', '501', '#', null, null, '0', '1', '1', 'monitor:logininfor:export', '0', '3', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1045', '账户解锁', 'B', '501', '#', null, null, '0', '1', '1', 'monitor:logininfor:unlock', '0', '4', '1', '1',
        'admin', sysdate(), null, null, null);
-- 在线用户按钮
insert into brc_sys_menu
values ('1046', '在线查询', 'B', '109', '#', null, null, '0', '1', '1', 'monitor:online:info', '0', '1', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1047', '批量强退', 'B', '109', '#', null, null, '0', '1', '1', 'monitor:online:batchLogout', '0', '2', '1',
        '1', 'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1048', '单条强退', 'B', '109', '#', null, null, '0', '1', '1', 'monitor:online:forceLogout', '0', '3', '1',
        '1', 'admin', sysdate(), null, null, null);
-- 定时任务按钮
insert into brc_sys_menu
values ('1049', '任务查询', 'B', '110', '#', null, null, '0', '1', '1', 'monitor:job:info', '0', '1', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1050', '任务新增', 'B', '110', '#', null, null, '0', '1', '1', 'monitor:job:add', '0', '2', '1', '1', 'admin',
        sysdate(), null, null, null);
insert into brc_sys_menu
values ('1051', '任务修改', 'B', '110', '#', null, null, '0', '1', '1', 'monitor:job:update', '0', '3', '1', '1', 'admin',
        sysdate(), null, null, null);
insert into brc_sys_menu
values ('1052', '任务删除', 'B', '110', '#', null, null, '0', '1', '1', 'monitor:job:delete', '0', '4', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1053', '状态修改', 'B', '110', '#', null, null, '0', '1', '1', 'monitor:job:changeStatus', '0', '5', '1', '1',
        'admin', sysdate(), null, null, null);
insert into brc_sys_menu
values ('1054', '任务导出', 'B', '110', '#', null, null, '0', '1', '1', 'monitor:job:export', '0', '6', '1', '1',
        'admin', sysdate(), null, null, null);
-- 代码生成按钮
insert into brc_sys_menu
values ('1055', '生成查询', 'B', '116', '#', null, null, '0', '1', '1', 'tool:gen:info', '0', '1', '1', '1', 'admin',
        sysdate(), null, null, null);
insert into brc_sys_menu
values ('1056', '生成修改', 'B', '116', '#', null, null, '0', '1', '1', 'tool:gen:update', '0', '2', '1', '1', 'admin',
        sysdate(), null, null, null);
insert into brc_sys_menu
values ('1057', '生成删除', 'B', '116', '#', null, null, '0', '1', '1', 'tool:gen:delete', '0', '3', '1', '1', 'admin',
        sysdate(), null, null, null);
insert into brc_sys_menu
values ('1058', '导入代码', 'B', '116', '#', null, null, '0', '1', '1', 'tool:gen:import', '0', '4', '1', '1', 'admin',
        sysdate(), null, null, null);
insert into brc_sys_menu
values ('1059', '预览代码', 'B', '116', '#', null, null, '0', '1', '1', 'tool:gen:preview', '0', '5', '1', '1', 'admin',
        sysdate(), null, null, null);
insert into brc_sys_menu
values ('1060', '生成代码', 'B', '116', '#', null, null, '0', '1', '1', 'tool:gen:code', '0', '6', '1', '1', 'admin',
        sysdate(), null, null, null);

-- 6、系统用户与角色关联表
drop table if exists brc_sys_user_role;
create table brc_sys_user_role
(
    user_id bigint not null comment '用户ID',
    role_id bigint not null comment '角色ID',
    primary key (user_id, role_id)
) engine InnoDB
  default charset utf8mb4 comment '系统用户与角色关联表';

-- 初始化-系统用户与角色关联表数据
insert into brc_sys_user_role
values ('1', '1');
insert into brc_sys_user_role
values ('2', '2');

-- 7、系统角色与菜单关联表
drop table if exists brc_sys_role_menu;
create table brc_sys_role_menu
(
    role_id bigint not null comment '角色ID',
    menu_id bigint not null comment '菜单ID',
    primary key (role_id, menu_id)
) engine InnoDB
  default charset utf8mb4 comment '系统角色与菜单关联表';

-- 初始化-系统角色与菜单关联表数据
insert into brc_sys_role_menu
values ('1', '1');
insert into brc_sys_role_menu
values ('1', '100');
insert into brc_sys_role_menu
values ('1', '1001');
insert into brc_sys_role_menu
values ('2', '1');
insert into brc_sys_role_menu
values ('2', '2');
insert into brc_sys_role_menu
values ('2', '3');
insert into brc_sys_role_menu
values ('2', '4');
insert into brc_sys_role_menu
values ('2', '100');
insert into brc_sys_role_menu
values ('2', '101');
insert into brc_sys_role_menu
values ('2', '102');
insert into brc_sys_role_menu
values ('2', '103');
insert into brc_sys_role_menu
values ('2', '104');
insert into brc_sys_role_menu
values ('2', '105');
insert into brc_sys_role_menu
values ('2', '106');
insert into brc_sys_role_menu
values ('2', '107');
insert into brc_sys_role_menu
values ('2', '108');
insert into brc_sys_role_menu
values ('2', '109');
insert into brc_sys_role_menu
values ('2', '110');
insert into brc_sys_role_menu
values ('2', '111');
insert into brc_sys_role_menu
values ('2', '112');
insert into brc_sys_role_menu
values ('2', '113');
insert into brc_sys_role_menu
values ('2', '114');
insert into brc_sys_role_menu
values ('2', '115');
insert into brc_sys_role_menu
values ('2', '116');
insert into brc_sys_role_menu
values ('2', '117');
insert into brc_sys_role_menu
values ('2', '500');
insert into brc_sys_role_menu
values ('2', '501');
insert into brc_sys_role_menu
values ('2', '1000');
insert into brc_sys_role_menu
values ('2', '1001');
insert into brc_sys_role_menu
values ('2', '1002');
insert into brc_sys_role_menu
values ('2', '1003');
insert into brc_sys_role_menu
values ('2', '1004');
insert into brc_sys_role_menu
values ('2', '1005');
insert into brc_sys_role_menu
values ('2', '1006');
insert into brc_sys_role_menu
values ('2', '1007');
insert into brc_sys_role_menu
values ('2', '1008');
insert into brc_sys_role_menu
values ('2', '1009');
insert into brc_sys_role_menu
values ('2', '1010');
insert into brc_sys_role_menu
values ('2', '1011');
insert into brc_sys_role_menu
values ('2', '1012');
insert into brc_sys_role_menu
values ('2', '1013');
insert into brc_sys_role_menu
values ('2', '1014');
insert into brc_sys_role_menu
values ('2', '1015');
insert into brc_sys_role_menu
values ('2', '1016');
insert into brc_sys_role_menu
values ('2', '1017');
insert into brc_sys_role_menu
values ('2', '1018');
insert into brc_sys_role_menu
values ('2', '1019');
insert into brc_sys_role_menu
values ('2', '1020');
insert into brc_sys_role_menu
values ('2', '1021');
insert into brc_sys_role_menu
values ('2', '1022');
insert into brc_sys_role_menu
values ('2', '1023');
insert into brc_sys_role_menu
values ('2', '1024');
insert into brc_sys_role_menu
values ('2', '1025');
insert into brc_sys_role_menu
values ('2', '1026');
insert into brc_sys_role_menu
values ('2', '1027');
insert into brc_sys_role_menu
values ('2', '1028');
insert into brc_sys_role_menu
values ('2', '1029');
insert into brc_sys_role_menu
values ('2', '1030');
insert into brc_sys_role_menu
values ('2', '1031');
insert into brc_sys_role_menu
values ('2', '1032');
insert into brc_sys_role_menu
values ('2', '1033');
insert into brc_sys_role_menu
values ('2', '1034');
insert into brc_sys_role_menu
values ('2', '1035');
insert into brc_sys_role_menu
values ('2', '1036');
insert into brc_sys_role_menu
values ('2', '1037');
insert into brc_sys_role_menu
values ('2', '1038');
insert into brc_sys_role_menu
values ('2', '1039');
insert into brc_sys_role_menu
values ('2', '1040');
insert into brc_sys_role_menu
values ('2', '1041');
insert into brc_sys_role_menu
values ('2', '1042');
insert into brc_sys_role_menu
values ('2', '1043');
insert into brc_sys_role_menu
values ('2', '1044');
insert into brc_sys_role_menu
values ('2', '1045');
insert into brc_sys_role_menu
values ('2', '1046');
insert into brc_sys_role_menu
values ('2', '1047');
insert into brc_sys_role_menu
values ('2', '1048');
insert into brc_sys_role_menu
values ('2', '1049');
insert into brc_sys_role_menu
values ('2', '1050');
insert into brc_sys_role_menu
values ('2', '1051');
insert into brc_sys_role_menu
values ('2', '1052');
insert into brc_sys_role_menu
values ('2', '1053');
insert into brc_sys_role_menu
values ('2', '1054');
insert into brc_sys_role_menu
values ('2', '1055');
insert into brc_sys_role_menu
values ('2', '1056');
insert into brc_sys_role_menu
values ('2', '1057');
insert into brc_sys_role_menu
values ('2', '1058');
insert into brc_sys_role_menu
values ('2', '1059');
insert into brc_sys_role_menu
values ('2', '1060');

-- 8、系统角色与部门关联表
drop table if exists brc_sys_role_dept;
create table brc_sys_role_dept
(
    role_id varchar(36) not null comment '角色ID',
    dept_id varchar(36) not null comment '部门ID',
    primary key (role_id, dept_id)
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
    user_id varchar(36) not null comment '用户ID',
    post_id varchar(36) not null comment '岗位ID',
    primary key (user_id, post_id)
) engine InnoDB
  default charset utf8mb4 comment '系统用户与岗位关联表';

-- 初始化-系统用户与岗位关联表数据
insert into brc_sys_user_post
values ('1', '1');
insert into brc_sys_user_post
values ('2', '2');

-- 10、系统操作日志表
drop table if exists brc_sys_operation_log;
create table brc_sys_operation_log
(
    id                 varchar(36) not null comment 'ID',
    title              varchar(50)   default '' comment '模块标题',
    business_type      char(1)    default '0' comment '业务类型（0：其它，1：新增，2：修改，3：删除）',
    method             varchar(100)  default '' comment '方法名称',
    request_method     varchar(10)   default '' comment '请求方式',
    operator_type      tinyint(1)    default '3' comment '操作类别（0：后台用户，1：手机端用户，2：其它）',
    operation_user_id  varchar(50)   default '' comment '操作人员ID',
    operation_username varchar(50)   default '' comment '操作人员账号',
    dept_name          varchar(50)   default '' comment '部门名称',
    operation_url      varchar(2048)  default '' comment '请求URL',
    operation_ip       varchar(128)  default '' comment '主机地址',
    operation_location varchar(255)  default '' comment '操作地点',
    operation_param    varchar(2000) default '' comment '请求参数',
    operation_time     datetime      default null comment '操作时间',
    duration           int           default null comment '执行时长（毫秒）',
    operation_status   tinyint(1)    default '0' comment '操作状态（0：正常，1：异常）',
    json_result        varchar(2000) default '' comment '返回参数',
    error_message      varchar(2000) default '' comment '错误消息',
    primary key (id)
) engine InnoDB
  default charset utf8mb4 comment '系统操作日志表';

-- 11、系统字典类型表
drop table if exists brc_sys_dict_type;
create table brc_sys_dict_type
(
    id              varchar(36) not null comment '字典主键',
    dict_name       varchar(100)    default '' comment '字典名称',
    dict_type       varchar(100)    default '' comment '字典类型',
    status          tinyint(1)      default '1' comment '状态（0：停用，1：正常）',
    create_user_id  varchar(36) default null comment '创建人ID',
    create_username varchar(50)     default '' comment '创建人账号',
    create_time     datetime        default null comment '创建时间',
    update_user_id  varchar(36) default null comment '修改人ID',
    update_time     datetime        default null comment '修改时间',
    remark          varchar(500)    default null comment '备注',
    primary key (id),
    constraint brc_sys_dict_type_dict_type_unique unique index (dict_type)
) engine InnoDB
  default charset utf8mb4 comment '系统字典类型表';

-- 初始化-系统字典类型表数据
insert into brc_sys_dict_type
values (1, '用户性别', 'sys_user_gender', '1', '1', 'admin', sysdate(), null, null, '用户性别列表');
insert into brc_sys_dict_type
values (2, '菜单状态', 'sys_show_hide', '1', '1', 'admin', sysdate(), null, null, '菜单状态列表');
insert into brc_sys_dict_type
values (3, '系统开关', 'sys_normal_disable', '1', '1', 'admin', sysdate(), null, null, '系统开关列表');
insert into brc_sys_dict_type
values (4, '任务状态', 'sys_job_status', '1', '1', 'admin', sysdate(), null, null, '任务状态列表');
insert into brc_sys_dict_type
values (5, '任务分组', 'sys_job_group', '1', '1', 'admin', sysdate(), null, null, '任务分组列表');
insert into brc_sys_dict_type
values (6, '系统是否', 'sys_yes_no', '1', '1', 'admin', sysdate(), null, null, '系统是否列表');
insert into brc_sys_dict_type
values (7, '通知类型', 'sys_notice_type', '1', '1', 'admin', sysdate(), null, null, '通知类型列表');
insert into brc_sys_dict_type
values (8, '通知状态', 'sys_notice_status', '1', '1', 'admin', sysdate(), null, null, '通知状态列表');
insert into brc_sys_dict_type
values (9, '操作类型', 'sys_operation_type', '1', '1', 'admin', sysdate(), null, null, '操作类型列表');
insert into brc_sys_dict_type
values (10, '系统状态', 'sys_common_status', '1', '1', 'admin', sysdate(), null, null, '登录状态列表');

-- 12、系统字典数据表
drop table if exists brc_sys_dict_data;
create table brc_sys_dict_data
(
    id              varchar(36) not null comment 'ID',
    dict_label      varchar(100)    default '' comment '字典标签',
    dict_value      varchar(100)    default '' comment '字典值',
    dict_type       varchar(100)    default '' comment '字典类型',
    css_class       varchar(100)    default null comment '样式属性（其他样式扩展）',
    list_class      varchar(100)    default null comment '表格回显样式',
    is_default      tinyint(1)      default '0' comment '是否默认（1：是，0：否）',
    sort            integer         default '0' comment '显示顺序',
    status          tinyint(1)      default '1' comment '状态（0：停用，1：正常）',
    create_user_id  varchar(36) default null comment '创建人ID',
    create_username varchar(50)     default '' comment '创建人账号',
    create_time     datetime        default null comment '创建时间',
    update_user_id  varchar(36) default null comment '修改人ID',
    update_time     datetime        default null comment '修改时间',
    remark          varchar(500)    default null comment '备注',
    primary key (id)
) engine InnoDB
  default charset utf8mb4 comment '系统字典数据表';

-- 初始化-系统字典数据表数据
insert into brc_sys_dict_data
values (1, '男', '0', 'sys_user_sex', '', '', '1', 1, '1', '1', 'admin', sysdate(), null, null, '性别男');
insert into brc_sys_dict_data
values (2, '女', '1', 'sys_user_sex', '', '', '0', 2, '1', '1', 'admin', sysdate(), null, null, '性别女');
insert into brc_sys_dict_data
values (3, '未知', '2', 'sys_user_sex', '', '', '0', 3, '1', '1', 'admin', sysdate(), null, null, '性别未知');
insert into brc_sys_dict_data
values (4, '显示', '0', 'sys_show_hide', '', 'primary', '1', 1, '1', '1', 'admin', sysdate(), null, null, '显示菜单');
insert into brc_sys_dict_data
values (5, '隐藏', '1', 'sys_show_hide', '', 'danger', '0', 2, '1', '1', 'admin', sysdate(), null, null, '隐藏菜单');
insert into brc_sys_dict_data
values (6, '正常', '0', 'sys_normal_disable', '', 'primary', '1', 1, '1', '1', 'admin', sysdate(), null, null, '正常状态');
insert into brc_sys_dict_data
values (7, '停用', '1', 'sys_normal_disable', '', 'danger', '0', 2, '1', '1', 'admin', sysdate(), null, null, '停用状态');
insert into brc_sys_dict_data
values (8, '正常', '0', 'sys_job_status', '', 'primary', '1', 1, '1', '1', 'admin', sysdate(), null, null, '正常状态');
insert into brc_sys_dict_data
values (9, '暂停', '1', 'sys_job_status', '', 'danger', '0', 2, '1', '1', 'admin', sysdate(), null, null, '停用状态');
insert into brc_sys_dict_data
values (10, '默认', 'DEFAULT', 'sys_job_group', '', '', '1', 1, '1', '1', 'admin', sysdate(), null, null, '默认分组');
insert into brc_sys_dict_data
values (11, '系统', 'SYSTEM', 'sys_job_group', '', '', '0', 2, '1', '1', 'admin', sysdate(), null, null, '系统分组');
insert into brc_sys_dict_data
values (12, '是', 'Y', 'sys_yes_no', '', 'primary', '1', 1, '1', '1', 'admin', sysdate(), null, null, '系统默认是');
insert into brc_sys_dict_data
values (13, '否', 'N', 'sys_yes_no', '', 'danger', '0', 2, '1', '1', 'admin', sysdate(), null, null, '系统默认否');
insert into brc_sys_dict_data
values (14, '通知', '1', 'sys_notice_type', '', 'warning', '1', 1, '1', '1', 'admin', sysdate(), null, null, '通知');
insert into brc_sys_dict_data
values (15, '公告', '2', 'sys_notice_type', '', 'success', '0', 2, '1', '1', 'admin', sysdate(), null, null, '公告');
insert into brc_sys_dict_data
values (16, '正常', '0', 'sys_notice_status', '', 'primary', '1', 1, '1', '1', 'admin', sysdate(), null, null, '正常状态');
insert into brc_sys_dict_data
values (17, '关闭', '1', 'sys_notice_status', '', 'danger', '0', 2, '1', '1', 'admin', sysdate(), null, null, '关闭状态');
insert into brc_sys_dict_data
values (18, '其他', '0', 'sys_operation_type', '', 'info', '0', 99, '1', '1', 'admin', sysdate(), null, null, '其他操作');
insert into brc_sys_dict_data
values (19, '新增', '1', 'sys_operation_type', '', 'info', '0', 1, '1', '1', 'admin', sysdate(), null, null, '新增操作');
insert into brc_sys_dict_data
values (20, '修改', '2', 'sys_operation_type', '', 'info', '0', 2, '1', '1', 'admin', sysdate(), null, null, '修改操作');
insert into brc_sys_dict_data
values (21, '删除', '3', 'sys_operation_type', '', 'danger', '0', 3, '1', '1', 'admin', sysdate(), null, null, '删除操作');
insert into brc_sys_dict_data
values (22, '授权', '4', 'sys_operation_type', '', 'primary', '0', 4, '1', '1', 'admin', sysdate(), null, null, '授权操作');
insert into brc_sys_dict_data
values (23, '导出', '5', 'sys_operation_type', '', 'warning', '0', 5, '1', '1', 'admin', sysdate(), null, null, '导出操作');
insert into brc_sys_dict_data
values (24, '导入', '6', 'sys_operation_type', '', 'warning', '0', 6, '1', '1', 'admin', sysdate(), null, null, '导入操作');
insert into brc_sys_dict_data
values (25, '强退', '7', 'sys_operation_type', '', 'danger', '0', 7, '1', '1', 'admin', sysdate(), null, null, '强退操作');
insert into brc_sys_dict_data
values (26, '生成代码', '8', 'sys_operation_type', '', 'warning', '0', 8, '1', '1', 'admin', sysdate(), null, null, '生成操作');
insert into brc_sys_dict_data
values (27, '清空数据', '9', 'sys_operation_type', '', 'danger', '0', 9, '1', '1', 'admin', sysdate(), null, null, '清空操作');
insert into brc_sys_dict_data
values (28, '成功', '0', 'sys_common_status', '', 'primary', '0', 1, '1', '1', 'admin', sysdate(), null, null, '正常状态');
insert into brc_sys_dict_data
values (29, '失败', '1', 'sys_common_status', '', 'danger', '0', 2, '1', '1', 'admin', sysdate(), null, null, '停用状态');

-- 13、系统配置表
drop table if exists brc_sys_config;
create table brc_sys_config
(
    id              varchar(36) not null comment 'ID',
    config_name     varchar(100)    default null comment '配置名称',
    config_key      varchar(100)    default null comment '配置键',
    config_value    varchar(1000)   default '' comment '配置值',
    config_type     tinyint(1)      default '0' comment '是否系统内置（1：是，0：否）',
    create_user_id  varchar(36) default null comment '创建人ID',
    create_username varchar(50)     default '' comment '创建人账号',
    create_time     datetime        default null comment '创建时间',
    update_user_id  varchar(36) default null comment '修改人ID',
    update_time     datetime        default null comment '修改时间',
    remark          varchar(500)    default null comment '备注',
    primary key (id),
    constraint brc_sys_config_config_key_unique unique index (config_key),
    constraint brc_sys_config_config_type_check check (config_type in ('0', '1'))
) engine InnoDB
  default charset utf8mb4 comment '系统配置表';

-- 初始化-系统配置表数据
insert into brc_sys_config
values (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', '1', '1', 'admin', sysdate(), null, null,
        '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
insert into brc_sys_config
values (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', '1', '1', 'admin', sysdate(), null, null, '初始化密码 123456');
insert into brc_sys_config
values (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', '1', '1', 'admin', sysdate(), null, null,
        '深色主题theme-dark，浅色主题theme-light');
insert into brc_sys_config
values (4, '账号自助-图片验证码开关', 'sys.account.captchaEnabled', 'true', '1', '1', 'admin', sysdate(), null, null,
        '是否开启图片验证码功能（true开启，false关闭）');
insert into brc_sys_config
values (5, '账号自助-短信验证码开关', 'sys.account.smsEnabled', 'true', '1', '1', 'admin', sysdate(), null, null,
        '是否开启短信验证码功能（优先级高于图片验证码，true开启，false关闭）');
insert into brc_sys_config
values (6, '账号自助-用户注册功能开关', 'sys.account.registerEnabled', 'false', '1', '1', 'admin', sysdate(), null, null,
        '是否开启注册用户功能（true开启，false关闭）');

-- 14、系统登录信息表
drop table if exists brc_sys_login_info;
create table brc_sys_login_info
(
    id             varchar(36) not null comment 'ID',
    username       varchar(50)  default null comment '用户账号',
    ipaddr         varchar(128) default '' comment '登录IP地址',
    login_location varchar(255) default '' comment '登录地点',
    browser        varchar(50)  default '' comment '浏览器类型',
    os             varchar(50)  default '' comment '操作系统',
    status         tinyint(1)   default '0' comment '登录状态（0：失败，1：成功）',
    message        varchar(255) default '' comment '提示消息',
    login_time     datetime     default null comment '访问时间',
    primary key (id)
) engine InnoDB
  default charset utf8mb4 comment '系统登录信息表';

-- 15、系统定时任务调度表
drop table if exists brc_sys_job;
create table brc_sys_job
(
    id                   varchar(36) not null comment 'ID',
    job_name             varchar(50)     default '' comment '任务名称',
    job_group            varchar(50)     default 'DEFAULT' comment '任务组名',
    invoke_target        varchar(500)    not null comment '调用目标字符串',
    cron_expression      varchar(255)    default '' comment 'cron执行表达式',
    execute_error_policy tinyint(1)      default '3' comment '计划执行错误策略（1：立即执行，2：执行一次，3：放弃执行）',
    is_concurrent        tinyint(1)      default '0' comment '是否并发执行（0：否，1：是）',
    sort                 integer         default '0' comment '显示顺序',
    status               tinyint(1)      default '1' comment '状态（0：停用，1：正常）',
    create_user_id       varchar(36) default null comment '创建人ID',
    create_username      varchar(50)     default '' comment '创建人账号',
    create_time          datetime        default null comment '创建时间',
    update_user_id       varchar(36) default null comment '修改人ID',
    update_time          datetime        default null comment '修改时间',
    remark               varchar(500)    default null comment '备注',
    primary key (id)
) engine InnoDB
  default charset utf8mb4 comment '系统定时任务调度表';

-- 初始化-系统定时任务调度表数据
insert into brc_sys_job
values (1, '系统默认（无参）', 'DEFAULT', 'brcTask.brcNoParams', '0/10 * * * * ?', '3', '0', '1', '0', '1', 'admin', sysdate(),
        null, null, '');
insert into brc_sys_job
values (2, '系统默认（有参）', 'DEFAULT', 'brcTask.brcParams(\'brc\')', '0/15 * * * * ?', '3', '0', '2', '0', '1', 'admin',
        sysdate(), null, null, '');
insert into brc_sys_job
values (3, '系统默认（多参）', 'DEFAULT', 'brcTask.brcMultipleParams(\'brc\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?',
        '3', '0', '3', '0', '1', 'admin', sysdate(), null, null, '');

-- 16、系统定时任务调度日志表
drop table if exists brc_sys_job_log;
create table brc_sys_job_log
(
    id             varchar(36) not null comment 'ID',
    job_name       varchar(64)     not null comment '任务名称',
    job_group      varchar(64)     not null comment '任务组名',
    invoke_target  varchar(500)    not null comment '调用目标字符串',
    job_message    varchar(500) comment '日志信息',
    execute_status tinyint(1)    default '0' comment '执行状态（0：失败，1：正常）',
    exception_info varchar(2000) default '' comment '异常信息',
    create_time    datetime      default null comment '创建时间',
    primary key (id)
) engine InnoDB
  default charset utf8mb4 comment '系统定时任务调度日志表';

-- 17、系统通知公告表
drop table if exists brc_sys_notice;
create table brc_sys_notice
(
    id              varchar(36) not null comment 'ID',
    notice_title    varchar(50)     not null comment '公告标题',
    notice_content  longtext        default null comment '公告内容',
    notice_type     tinyint(1)      not null comment '公告类型（0：通知，1：公告）',
    status          tinyint(1)      default '1' comment '状态（0：关闭，1：正常）',
    create_user_id  varchar(36) default null comment '创建人ID',
    create_username varchar(50)     default '' comment '创建人账号',
    create_time     datetime        default null comment '创建时间',
    update_user_id  varchar(36) default null comment '修改人ID',
    update_time     datetime        default null comment '修改时间',
    remark          varchar(500)    default null comment '备注',
    primary key (id)
) engine InnoDB
  default charset utf8mb4 comment '系统通知公告表';

-- 初始化-公告信息表数据
insert into brc_sys_notice
values ('1', '温馨提醒：2022-10-01 Bryce新版本发布啦', '新版本内容', '2', '1', '1', 'admin', sysdate(), null, null, '管理员');
insert into brc_sys_notice
values ('2', '维护通知：2022-10-01 Bryce系统凌晨维护', '维护内容', '1', '1', '1', 'admin', sysdate(), null, null, '管理员');

-- 18、代码生成业务表
drop table if exists brc_gen_table;
create table brc_gen_table
(
    id                varchar(36) not null comment 'ID',
    table_name        varchar(200)    default '' comment '表名称',
    table_comment     varchar(500)    default '' comment '表描述',
    sub_table_name    varchar(64)     default null comment '关联子表的表名',
    sub_table_fk_name varchar(64)     default null comment '子表关联的外键名',
    class_name        varchar(100)    default '' comment '实体类名称',
    template_category varchar(10)     default 'crud' comment '使用的模板（crud：单表操作，tree：树表操作）',
    package_name      varchar(100)    default null comment '生成包路径',
    module_name       varchar(30)     default null comment '生成模块名',
    business_name     varchar(30)     default null comment '生成业务名',
    function_name     varchar(50)     default null comment '生成功能名',
    function_author   varchar(50)     default null comment '生成功能作者',
    generate_type     tinyint(1)      default '0' comment '生成代码方式（0：zip压缩包，1：自定义路径）',
    generate_path     varchar(200)    default '/' comment '生成路径（不填默认项目路径）',
    options           varchar(1000)   default null comment '其它生成选项',
    create_user_id    varchar(36) default null comment '创建人ID',
    create_username   varchar(50)     default '' comment '创建人账号',
    create_time       datetime        default null comment '创建时间',
    update_user_id    varchar(36) default null comment '修改人ID',
    update_time       datetime        default null comment '修改时间',
    remark            varchar(500)    default null comment '备注',
    primary key (id)
) engine InnoDB
  default charset utf8mb4 comment '代码生成业务表';

-- 19、代码生成业务表字段
drop table if exists brc_gen_table_column;
create table brc_gen_table_column
(
    id              varchar(36) not null comment 'ID',
    table_id        varchar(64)     default null comment '归属表编号',
    column_name     varchar(200)    default null comment '列名称',
    column_comment  varchar(500)    default null comment '列描述',
    column_type     varchar(100)    default null comment '列类型',
    java_type       varchar(500)    default null comment 'Java类型',
    java_field      varchar(200)    default null comment 'Java字段名',
    is_pk           tinyint(1)      default null comment '是否主键（0：否，1：是）',
    is_increment    tinyint(1)      default null comment '是否自增（0：否，1：是）',
    is_required     tinyint(1)      default null comment '是否必填（0：否，1：是）',
    is_insert       tinyint(1)      default null comment '是否为插入字段（0：否，1：是）',
    is_edit         tinyint(1)      default null comment '是否编辑字段（0：否，1：是）',
    is_list         tinyint(1)      default null comment '是否列表字段（0：否，1：是）',
    is_query        tinyint(1)      default null comment '是否查询字段（0：否，1：是）',
    query_type      varchar(200)    default null default 'EQ' comment '查询方式（EQ：等于、NE：不等于、GT：大于、LT：小于、BETWEEN：范围，LIKE：模糊查询）',
    html_type       varchar(200)    default null comment '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
    dict_type       varchar(200)    default '' comment '字典类型',
    sort            integer         default '0' comment '显示顺序',
    create_user_id  varchar(36) default null comment '创建人ID',
    create_username varchar(50)     default '' comment '创建人账号',
    create_time     datetime        default null comment '创建时间',
    update_user_id  varchar(36) default null comment '修改人ID',
    update_time     datetime        default null comment '修改时间',
    primary key (id)
) engine InnoDB
  default charset utf8mb4 comment '代码生成业务表字段';
-- 20、租户表
drop table if exists brc_tenant;
create table brc_tenant
(
    id              varchar(36)     not null comment 'ID',
    name            varchar(100)    not null comment '租户名称',
    site_domain     varchar(200)    default null comment '租户域名',
    site_url        varchar(100)    default null comment '租户网址',
    site_logo       varchar(200)    default null comment '租户网址logo',
    site_config     text            default null comment '租户网址配置',
    admin_id        varchar(36)     not null comment '管理员ID',
    create_user_id  varchar(36)     default null comment '创建人ID',
    create_time     datetime        default null comment '创建时间',
    update_user_id  varchar(36)     default null comment '修改人ID',
    update_time     datetime        default null comment '修改时间',
    primary key (id),
    unique key unique_site_domain (site_domain)
) engine InnoDB default charset utf8mb4 comment '租户表';

-- 21、附件表
drop table if exists brc_sys_upload_file;
CREATE TABLE `brc_sys_upload_file`
(
    `id`            varchar(36) not null comment 'ID',
    `old_name`      varchar(100) DEFAULT null comment '文件原始名称',
    `new_path`      varchar(255) DEFAULT null comment '文件路径',
    `file_type`     varchar(50)  DEFAULT null comment '文件类型',
    `suffix`        varchar(10)  DEFAULT null comment '文件名后缀',
    `hash`          varchar(255) DEFAULT null comment '哈希码',
    `size`          bigint       DEFAULT null comment '文件大小（单位字节）',
    `width`         int          DEFAULT '0' comment '宽',
    `height`        int          DEFAULT '0' comment '高',
    `lng`           varchar(30)  DEFAULT null comment '经度',
    `lat`           varchar(30)  DEFAULT null comment '纬度',
    `tags`          varchar(255) DEFAULT null comment '标签',
    `sort`          int          DEFAULT '0' comment '排序',
    `version`       int          DEFAULT 1 comment '版本',
    create_user_id  bigint comment '创建用户ID',
    create_time     datetime comment '创建时间',
    primary key (id),
    key bryce_file_sort_index (sort) comment '排序索引',
    key bryce_file_create_user_id_index (create_user_id) comment '创建用户ID索引',
    key bryce_file_create_time_index (create_time) comment '创建时间索引'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_general_ci comment '上传文件表';

-- 文件上传
drop table if exists brc_sys_oss;
create table brc_sys_oss
(
    id              bigint not null comment 'ID',
    url             varchar(200) comment 'URL地址',
    create_user_id  bigint comment '创建人ID',
    create_username varchar(50) comment '创建用户账号',
    create_time     datetime comment '创建时间',
    primary key (id)
) engine InnoDB
  default charset utf8mb4 comment '文件上传表';






