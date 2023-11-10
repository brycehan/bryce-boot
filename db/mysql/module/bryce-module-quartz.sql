
-- 初始化-系统字典类型表数据
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (10, '任务分组', 'quartz_job_group', 0, 1, '任务分组列表', null, 1, 0, 1, now(), null, null);
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (11, '任务状态', 'quartz_job_status', 0, 1, '任务状态列表', null, 1, 0, 1, now(), null, null);

-- 初始化-系统字典数据表数据
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (101, '默认', 'DEFAULT', 10, 'success', 1, 1, null, null, 1, 0, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (102, '系统', 'SYSTEM', 10, '', 2, 1, null, null, 1, 0, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (111, '正常', true, 11, 'primary', 1, 1, null, null, 1, 0, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (112, '暂停', true, 11, 'danger', 2, 1, null, null, 1, 0, 1, now(), null, null);

-- 初始化-菜单数据
-- 二级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (111, '定时任务', 'M', 2, 'quartz/job/index', 'quartz:job:page', 'icon-reloadtime', 0, 1, '定时任务菜单', 1, null, 0, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (143, '定时任务日志', 'M', 5, 'quartz/jobLog/index', 'quartz:jobLog:page', 'icon-menu', 0, 3, '定时任务日志菜单', 1, null, 0, 1, now(), null, null);
-- 三级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1111, '定时任务新增', 'B', 111, null, 'quartz:job:save', '', 0, 1, null, 1, null, 0, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1112, '定时任务修改', 'B', 111, null, 'quartz:job:update', '', 0, 2, null, 1, null, 0, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1113, '定时任务删除', 'B', 111, null, 'quartz:job:delete', '', 0, 3, null, 1, null, 0, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1114, '定时任务详情', 'B', 111, null, 'quartz:job:info', '', 0, 4, null, 1, null, 0, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1115, '定时任务导出', 'B', 111, null, 'quartz:job:export', '', 0, 5, null, 1, null, 0, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1116, '定时任务导入', 'B', 111, null, 'quartz:job:import', '', 0, 6, null, 1, null, 0, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1117, '定时任务立即执行', 'B', 111, null, 'quartz:job:run', '', 0, 7, null, 1, null, 0, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1431, '定时任务日志新增', 'B', 143, null, 'quartz:jobLog:save', '', 0, 1, null, 1, null, 0, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1432, '定时任务日志修改', 'B', 143, null, 'quartz:jobLog:update', '', 0, 2, null, 1, null, 0, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1433, '定时任务日志删除', 'B', 143, null, 'quartz:jobLog:delete', '', 0, 3, null, 1, null, 0, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1434, '定时任务日志详情', 'B', 143, null, 'quartz:jobLog:info', '', 0, 4, null, 1, null, 0, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1435, '定时任务日志导出', 'B', 143, null, 'quartz:jobLog:export', '', 0, 5, null, 1, null, 0, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1436, '定时任务日志导入', 'B', 143, null, 'quartz:jobLog:import', '', 0, 6, null, 1, null, 0, 1, now(), null, null);

-- 1、quartz 定时任务调度表
drop table if exists brc_quartz_job;
create table brc_quartz_job
(
    id              bigint                        not null comment 'ID',
    job_name        varchar(50)                   not null comment '任务名称',
    job_group       varchar(50) default 'DEFAULT' null comment '任务组名',
    bean_name       varchar(200)                  not null comment 'Spring Bean 名称',
    method          varchar(100)                  not null comment '执行方法',
    params          varchar(500)                  null comment '参数',
    cron_expression varchar(255)                  null comment 'cron 表达式',
    concurrent      char     default 'N'       null comment '是否并发执行（N：否，Y：是）',
    sort            int         default 0         null comment '显示顺序',
    status          tinyint     default 1         null comment '状态（0：暂停，1：正常）',
    remark          varchar(500)                  null comment '备注',
    tenant_id       bigint                        null comment '租户ID',
    version         int                           null comment '版本号',
    deleted         tinyint     default 0         null comment '删除标识（0：存在，1：已删除）',
    created_user_id bigint                        null comment '创建者ID',
    created_time    datetime                      null comment '创建时间',
    updated_user_id bigint                        null comment '修改者ID',
    updated_time    datetime                      null comment '修改时间',
    primary key (id)
) engine InnoDB comment 'quartz 定时任务调度表';

-- 初始化- quartz 定时任务调度表数据
INSERT INTO brc_quartz_job (id, job_name, job_group, bean_name, method, params, cron_expression, concurrent, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '测试任务', 'SYSTEM', 'testTaskServiceImpl', 'run', 'test', '0 * * * * ? *', 'N', 0, 0, '', null, 1, 0, 1, now(), null, null);


-- 2、quartz 定时任务调度日志表
drop table if exists brc_quartz_job_log;
create table brc_quartz_job_log
(
    id             bigint            not null comment 'ID',
    job_id         bigint            not null comment '任务ID',
    job_name       varchar(50)       not null comment '任务名称',
    job_group      varchar(50)       not null comment '任务组名',
    bean_name      varchar(200)      not null comment 'Spring Bean 名称',
    method         varchar(100)      not null comment '执行方法',
    params         varchar(500)      null comment '参数',
    execute_status tinyint default 0 null comment '执行状态（0：失败，1：成功）',
    duration       int               not null comment '执行时长（毫秒）',
    error_info     varchar(3000)     null comment '异常信息',
    created_time   datetime          null comment '创建时间'
) engine InnoDB comment 'quartz 定时任务调度日志表';

-- 3、quartz 框架自带的表结构

DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
DROP TABLE IF EXISTS QRTZ_LOCKS;
DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
DROP TABLE IF EXISTS QRTZ_CALENDARS;

create table QRTZ_JOB_DETAILS
(
    SCHED_NAME        varchar(120) not null comment '调度名称',
    JOB_NAME          varchar(200) not null comment '任务名称',
    JOB_GROUP         varchar(200) not null comment '任务组名',
    DESCRIPTION       varchar(250) null comment '相关介绍',
    JOB_CLASS_NAME    varchar(250) not null comment '执行任务类名称',
    IS_DURABLE        varchar(1)   not null comment '是否持久化',
    IS_NONCONCURRENT  varchar(1)   not null comment '是否并发',
    IS_UPDATE_DATA    varchar(1)   not null comment '是否更新数据',
    REQUESTS_RECOVERY varchar(1)   not null comment '是否接受恢复执行',
    JOB_DATA          blob         null comment '存放持久化 job 对象',
    primary key (SCHED_NAME, JOB_NAME, JOB_GROUP)
) engine InnoDB comment '任务详细信息表';

create table QRTZ_TRIGGERS
(
    SCHED_NAME     varchar(120) not null comment '调度名称',
    TRIGGER_NAME   varchar(200) not null comment '触发器名称',
    TRIGGER_GROUP  varchar(200) not null comment '触发器所属组名称',
    JOB_NAME       varchar(200) not null comment 'qrtz_job_details 表 job_name 的外键',
    JOB_GROUP      varchar(200) not null comment 'qrtz_job_details 表 job_group 的外键',
    DESCRIPTION    varchar(250) null comment '相关介绍',
    NEXT_FIRE_TIME bigint       null comment '下一次触发时间（默认为 -1 表示不触发）',
    PREV_FIRE_TIME bigint       null comment '上一次触发时间（单位：毫秒）',
    PRIORITY       int          null comment '优先级',
    TRIGGER_STATE  varchar(16)  not null comment '触发器状态',
    TRIGGER_TYPE   varchar(8)   not null comment '触发器的类型',
    START_TIME     bigint       not null comment '开始时间',
    END_TIME       bigint       null comment '结束时间',
    CALENDAR_NAME  varchar(200) null comment '日程表名称',
    MISFIRE_INSTR  smallint     null comment '补偿执行的策略',
    JOB_DATA       blob         null comment '存放持久化job对象',
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    foreign key (SCHED_NAME, JOB_NAME, JOB_GROUP)
        references QRTZ_JOB_DETAILS (SCHED_NAME, JOB_NAME, JOB_GROUP)
) engine InnoDB comment '触发器详细信息表';

create table QRTZ_SIMPLE_TRIGGERS
(
    SCHED_NAME      varchar(120) not null comment '调度名称',
    TRIGGER_NAME    varchar(200) not null comment 'qrtz_triggers 表 trigger_name 的外键',
    TRIGGER_GROUP   varchar(200) not null comment 'qrtz_triggers 表 trigger_group 的外键',
    REPEAT_COUNT    bigint       not null comment '重复的次数统计',
    REPEAT_INTERVAL bigint       not null comment '重复的间隔时间',
    TIMES_TRIGGERED bigint       not null comment '已经触发的次数',
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        references QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
) engine InnoDB comment '简单触发器的信息表';

create table QRTZ_CRON_TRIGGERS
(
    SCHED_NAME      varchar(120) not null comment '调度名称',
    TRIGGER_NAME    varchar(200) not null comment 'qrtz_triggers 表 trigger_name 的外键',
    TRIGGER_GROUP   varchar(200) not null comment 'qrtz_triggers 表 trigger_group 的外键',
    CRON_EXPRESSION varchar(200) not null comment 'cron 表达式',
    TIME_ZONE_ID    varchar(80)  null comment '时区',
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        references QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
) engine InnoDB comment 'Cron 类型的触发器表';

create table QRTZ_SIMPROP_TRIGGERS
(
    SCHED_NAME    varchar(120)   not null comment '调度名称',
    TRIGGER_NAME  varchar(200)   not null comment 'qrtz_triggers 表 trigger_name 的外键',
    TRIGGER_GROUP varchar(200)   not null comment 'qrtz_triggers 表 trigger_group 的外键',
    STR_PROP_1    varchar(512)   null comment 'String 类型的 trigger 的第一个参数',
    STR_PROP_2    varchar(512)   null comment 'String 类型的 trigger 的第二个参数',
    STR_PROP_3    varchar(512)   null comment 'String 类型的 trigger 的第三个参数',
    INT_PROP_1    int            null comment 'int 类型的 trigger 的第一个参数',
    INT_PROP_2    int            null comment 'String 类型的 trigger 的第二个参数',
    LONG_PROP_1   bigint         null comment 'long 类型的 trigger 的第一个参数',
    LONG_PROP_2   bigint         null comment 'long 类型的 trigger 的第二个参数',
    DEC_PROP_1    decimal(13, 4) null comment 'decimal 类型的 trigger 的第一个参数',
    DEC_PROP_2    decimal(13, 4) null comment 'decimal 类型的 trigger 的第二个参数',
    BOOL_PROP_1   varchar(1)     null comment 'Boolean 类型的 trigger 的第一个参数',
    BOOL_PROP_2   varchar(1)     null comment 'Boolean 类型的 trigger 的第二个参数',
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        references QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
) engine InnoDB comment '同步机制的行锁表';

create table QRTZ_BLOB_TRIGGERS
(
    SCHED_NAME    varchar(120) not null comment '调度名称',
    TRIGGER_NAME  varchar(200) not null comment 'qrtz_triggers 表 trigger_name 的外键',
    TRIGGER_GROUP varchar(200) not null comment 'qrtz_triggers 表 trigger_group 的外键',
    BLOB_DATA     blob         null comment '存放持久化 Trigger 对象',
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        references QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
) engine InnoDB comment 'Blob 类型的触发器表';

create table QRTZ_CALENDARS
(
    SCHED_NAME    varchar(120) not null comment '调度名称',
    CALENDAR_NAME varchar(200) not null comment '日历名称',
    CALENDAR      blob         not null comment '存放持久化 calendar 对象',
    primary key (SCHED_NAME, CALENDAR_NAME)
) engine InnoDB comment '日历信息表';

create table QRTZ_PAUSED_TRIGGER_GRPS
(
    SCHED_NAME    varchar(120) not null comment '调度名称',
    TRIGGER_GROUP varchar(200) not null comment 'qrtz_triggers 表 trigger_group 的外键',
    primary key (SCHED_NAME, TRIGGER_GROUP)
) engine InnoDB comment '暂停的触发器表';

create table QRTZ_FIRED_TRIGGERS
(
    SCHED_NAME        varchar(120) not null comment '调度名称',
    ENTRY_ID          varchar(95)  not null comment '调度器实例ID',
    TRIGGER_NAME      varchar(200) not null comment 'qrtz_triggers 表 trigger_name 的外键',
    TRIGGER_GROUP     varchar(200) not null comment 'qrtz_triggers 表 trigger_group 的外键',
    INSTANCE_NAME     varchar(200) not null comment '调度器实例名',
    FIRED_TIME        bigint       not null comment '触发的时间',
    SCHED_TIME        bigint       not null comment '定时器制定的时间',
    PRIORITY          int          not null comment '优先级',
    STATE             varchar(16)  not null comment '状态',
    JOB_NAME          varchar(200) null comment '任务名称',
    JOB_GROUP         varchar(200) null comment '任务组名',
    IS_NONCONCURRENT  varchar(1)   null comment '是否并发',
    REQUESTS_RECOVERY varchar(1)   null comment '是否接受恢复执行',
    primary key (SCHED_NAME, ENTRY_ID)
) engine InnoDB comment '已触发的触发器表';

create table QRTZ_SCHEDULER_STATE
(
    SCHED_NAME        varchar(120) not null comment '调度名称',
    INSTANCE_NAME     varchar(200) not null comment '实例名称',
    LAST_CHECKIN_TIME bigint       not null comment '上次检查时间',
    CHECKIN_INTERVAL  bigint       not null comment '检查间隔时间',
    primary key (SCHED_NAME, INSTANCE_NAME)
) engine InnoDB comment '调度器状态表';

create table QRTZ_LOCKS
(
    SCHED_NAME varchar(120) not null comment '调度名称',
    LOCK_NAME  varchar(40)  not null comment '悲观锁名称',
    primary key (SCHED_NAME, LOCK_NAME)
) engine InnoDB comment '存储的悲观锁信息表';

CREATE INDEX IDX_QRTZ_J_REQ_RECOVERY ON QRTZ_JOB_DETAILS(SCHED_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_J_GRP ON QRTZ_JOB_DETAILS(SCHED_NAME,JOB_GROUP);

CREATE INDEX IDX_QRTZ_T_J ON QRTZ_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_JG ON QRTZ_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_C ON QRTZ_TRIGGERS(SCHED_NAME,CALENDAR_NAME);
CREATE INDEX IDX_QRTZ_T_G ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_T_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_G_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NEXT_FIRE_TIME ON QRTZ_TRIGGERS(SCHED_NAME,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE_GRP ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

CREATE INDEX IDX_QRTZ_FT_TRIG_INST_NAME ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME);
CREATE INDEX IDX_QRTZ_FT_INST_JOB_REQ_RCVRY ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_FT_J_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_JG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_T_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_FT_TG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);
