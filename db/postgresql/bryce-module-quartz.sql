-- 初始化-系统字典类型表数据
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (10, '任务分组', 'quartz_job_group', 0, true, '任务分组列表', null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_type (id, dict_name, dict_type, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (11, '任务状态', 'quartz_job_status', 0, true, '任务状态列表', null, 1, false, 1, now(), null, null);

-- 初始化-系统字典数据表数据
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (101, '默认', 'DEFAULT', 10, 'success', 1, true, null, null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (102, '系统', 'SYSTEM', 10, '', 2, true, null, null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (111, '正常', true, 11, 'primary', 1, true, null, null, 1, false, 1, now(), null, null);
INSERT INTO brc_sys_dict_data (id, dict_label, dict_value, dict_type_id, label_class, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (112, '暂停', false, 11, 'danger', 2, true, null, null, 1, false, 1, now(), null, null);

-- 初始化-菜单数据
-- 二级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (111, '定时任务', 'M', 2, 'quartz/job/index', 'quartz:job:page', 'icon-reloadtime', false, 1, '定时任务菜单', true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (143, '定时任务日志', 'M', 5, 'quartz/jobLog/index', 'quartz:jobLog:page', 'icon-menu', false, 3, '定时任务日志菜单', true, null, false, 1, now(), null, null);
-- 三级菜单
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1111, '定时任务新增', 'B', 111, null, 'quartz:job:save', '', false, 1, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1112, '定时任务修改', 'B', 111, null, 'quartz:job:update', '', false, 2, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1113, '定时任务删除', 'B', 111, null, 'quartz:job:delete', '', false, 3, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1114, '定时任务详情', 'B', 111, null, 'quartz:job:info', '', false, 4, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1115, '定时任务导出', 'B', 111, null, 'quartz:job:export', '', false, 5, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1116, '定时任务导入', 'B', 111, null, 'quartz:job:import', '', false, 6, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1117, '定时任务立即执行', 'B', 111, null, 'quartz:job:run', '', false, 7, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1431, '定时任务日志新增', 'B', 143, null, 'quartz:jobLog:save', '', false, 1, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1432, '定时任务日志修改', 'B', 143, null, 'quartz:jobLog:update', '', false, 2, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1433, '定时任务日志删除', 'B', 143, null, 'quartz:jobLog:delete', '', false, 3, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1434, '定时任务日志详情', 'B', 143, null, 'quartz:jobLog:info', '', false, 4, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1435, '定时任务日志导出', 'B', 143, null, 'quartz:jobLog:export', '', false, 5, null, true, null, false, 1, now(), null, null);
INSERT INTO brc_sys_menu (id, name, type, parent_id, url, authority, icon, open_style, sort, remark, status, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1436, '定时任务日志导入', 'B', 143, null, 'quartz:jobLog:import', '', false, 6, null, true, null, false, 1, now(), null, null);

-- 1、quartz 定时任务调度表
drop table if exists brc_quartz_job;
create table brc_quartz_job
(
    id              bigint       not null primary key,
    job_name        varchar(50)  not null,
    job_group       varchar(50) default 'DEFAULT',
    bean_name       varchar(200) not null,
    method          varchar(100) not null,
    params          varchar(500),
    cron_expression varchar(255),
    concurrent      char        default 'N',
    sort            integer     default 0,
    status          boolean     default true,
    remark          varchar(500),
    tenant_id       bigint,
    version         integer,
    deleted         boolean     default false,
    created_user_id bigint,
    created_time    timestamp,
    updated_user_id bigint,
    updated_time    timestamp
);

comment on table brc_quartz_job is 'quartz 定时任务调度表';
comment on column brc_quartz_job.id is 'ID';
comment on column brc_quartz_job.job_name is '任务名称';
comment on column brc_quartz_job.job_group is '任务组名';
comment on column brc_quartz_job.bean_name is 'Spring Bean 名称';
comment on column brc_quartz_job.method is '执行方法';
comment on column brc_quartz_job.params is '参数';
comment on column brc_quartz_job.cron_expression is 'cron 表达式';
comment on column brc_quartz_job.concurrent is '是否并发执行（N：否，Y：是）';
comment on column brc_quartz_job.sort is '显示顺序';
comment on column brc_quartz_job.status is '状态（0：暂停，1：正常）';
comment on column brc_quartz_job.remark is '备注';
comment on column brc_quartz_job.tenant_id is '租户ID';
comment on column brc_quartz_job.version is '版本号';
comment on column brc_quartz_job.deleted is '删除标识（0：存在，1：已删除）';
comment on column brc_quartz_job.created_user_id is '创建者ID';
comment on column brc_quartz_job.created_time is '创建时间';
comment on column brc_quartz_job.updated_user_id is '修改者ID';
comment on column brc_quartz_job.updated_time is '修改时间';

-- 初始化- quartz 定时任务调度表数据
INSERT INTO brc_quartz_job (id, job_name, job_group, bean_name, method, params, cron_expression, concurrent, sort, status, remark, tenant_id, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, '测试任务', 'SYSTEM', 'testTaskServiceImpl', 'run', 'test', '0 * * * * ? *', 'N', 0, false, '', null, 1, false, 1, now(), null, null);

-- 2、quartz 定时任务调度日志表
drop table if exists brc_quartz_job_log;
create table brc_quartz_job_log
(
    id             bigint       not null,
    job_id         bigint       not null,
    job_name       varchar(50)  not null,
    job_group      varchar(50)  not null,
    bean_name      varchar(200) not null,
    method         varchar(100) not null,
    params         varchar(500),
    execute_status boolean default false,
    duration       integer      not null,
    error_info     varchar(3000),
    created_time   timestamp
);

comment on table brc_quartz_job_log is 'quartz 定时任务调度日志表';
comment on column brc_quartz_job_log.id is 'ID';
comment on column brc_quartz_job_log.job_id is '任务ID';
comment on column brc_quartz_job_log.job_name is '任务名称';
comment on column brc_quartz_job_log.job_group is '任务组名';
comment on column brc_quartz_job_log.bean_name is 'Spring Bean 名称';
comment on column brc_quartz_job_log.method is '执行方法';
comment on column brc_quartz_job_log.params is '参数';
comment on column brc_quartz_job_log.execute_status is '执行状态（0：失败，1：成功）';
comment on column brc_quartz_job_log.duration is '执行时长（毫秒）';
comment on column brc_quartz_job_log.error_info is '异常信息';
comment on column brc_quartz_job_log.created_time is '创建时间';

-- 3、quartz 框架自带的表结构
drop table if exists qrtz_fired_triggers;
drop table if exists qrtz_paused_trigger_grps;
drop table if exists qrtz_scheduler_state;
drop table if exists qrtz_locks;
drop table if exists qrtz_simple_triggers;
drop table if exists qrtz_simprop_triggers;
drop table if exists qrtz_cron_triggers;
drop table if exists qrtz_blob_triggers;
drop table if exists qrtz_triggers;
drop table if exists qrtz_job_details;
drop table if exists qrtz_calendars;

create table qrtz_job_details
(
    sched_name        varchar(120) not null,
    job_name          varchar(200) not null,
    job_group         varchar(200) not null,
    description       varchar(250),
    job_class_name    varchar(250) not null,
    is_durable        boolean      not null,
    is_nonconcurrent  boolean      not null,
    is_update_data    boolean      not null,
    requests_recovery boolean      not null,
    job_data          bytea,
    primary key (sched_name, job_name, job_group)
);

comment on table qrtz_job_details is '任务详细信息表';
comment on column qrtz_job_details.sched_name is '调度名称';
comment on column qrtz_job_details.job_name is '任务名称';
comment on column qrtz_job_details.job_group is '任务组名';
comment on column qrtz_job_details.description is '相关介绍';
comment on column qrtz_job_details.job_class_name is '执行任务类名称';
comment on column qrtz_job_details.is_durable is '是否持久化';
comment on column qrtz_job_details.is_nonconcurrent is '是否并发';
comment on column qrtz_job_details.is_update_data is '是否更新数据';
comment on column qrtz_job_details.requests_recovery is '是否接受恢复执行';
comment on column qrtz_job_details.job_data is '存放持久化 job 对象';

create table qrtz_triggers
(
    sched_name     varchar(120) not null,
    trigger_name   varchar(200) not null,
    trigger_group  varchar(200) not null,
    job_name       varchar(200) not null,
    job_group      varchar(200) not null,
    description    varchar(250),
    next_fire_time bigint,
    prev_fire_time bigint,
    priority       integer,
    trigger_state  varchar(16)  not null,
    trigger_type   varchar(8)   not null,
    start_time     bigint       not null,
    end_time       bigint,
    calendar_name  varchar(200),
    misfire_instr  smallint,
    job_data       bytea,
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, job_name, job_group)
        references qrtz_job_details (sched_name, job_name, job_group)
);

comment on table qrtz_triggers is '触发器详细信息表';
comment on column qrtz_triggers.sched_name is '调度名称';
comment on column qrtz_triggers.trigger_name is '触发器名称';
comment on column qrtz_triggers.trigger_group is '触发器所属组名称';
comment on column qrtz_triggers.job_name is 'qrtz_job_details 表 job_name 的外键';
comment on column qrtz_triggers.job_group is 'qrtz_job_details 表 job_group 的外键';
comment on column qrtz_triggers.description is '相关介绍';
comment on column qrtz_triggers.next_fire_time is '下一次触发时间（默认为 -1 表示不触发）';
comment on column qrtz_triggers.prev_fire_time is '上一次触发时间（单位：毫秒）';
comment on column qrtz_triggers.priority is '优先级';
comment on column qrtz_triggers.trigger_state is '触发器状态';
comment on column qrtz_triggers.trigger_type is '触发器的类型';
comment on column qrtz_triggers.start_time is '开始时间';
comment on column qrtz_triggers.end_time is '结束时间';
comment on column qrtz_triggers.calendar_name is '日程表名称';
comment on column qrtz_triggers.misfire_instr is '补偿执行的策略';
comment on column qrtz_triggers.job_data is '存放持久化job对象';

create table qrtz_simple_triggers
(
    sched_name      varchar(120) not null,
    trigger_name    varchar(200) not null,
    trigger_group   varchar(200) not null,
    repeat_count    bigint       not null,
    repeat_interval bigint       not null,
    times_triggered bigint       not null,
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group)
        references qrtz_triggers (sched_name, trigger_name, trigger_group)
);

comment on table qrtz_simple_triggers is '简单触发器的信息表';
comment on column qrtz_simple_triggers.sched_name is '调度名称';
comment on column qrtz_simple_triggers.trigger_name is 'qrtz_triggers 表 trigger_name 的外键';
comment on column qrtz_simple_triggers.trigger_group is 'qrtz_triggers 表 trigger_group 的外键';
comment on column qrtz_simple_triggers.repeat_count is '重复的次数统计';
comment on column qrtz_simple_triggers.repeat_interval is '重复的间隔时间';
comment on column qrtz_simple_triggers.times_triggered is '已经触发的次数';

create table qrtz_cron_triggers
(
    sched_name      varchar(120) not null,
    trigger_name    varchar(200) not null,
    trigger_group   varchar(200) not null,
    cron_expression varchar(200) not null,
    time_zone_id    varchar(80),
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group)
        references qrtz_triggers (sched_name, trigger_name, trigger_group)
);

comment on table qrtz_cron_triggers is 'Cron 类型的触发器表';
comment on column qrtz_cron_triggers.sched_name is '调度名称';
comment on column qrtz_cron_triggers.trigger_name is 'qrtz_triggers 表 trigger_name 的外键';
comment on column qrtz_cron_triggers.trigger_group is 'qrtz_triggers 表 trigger_group 的外键';
comment on column qrtz_cron_triggers.cron_expression is 'cron 表达式';
comment on column qrtz_cron_triggers.time_zone_id is '时区';

create table qrtz_simprop_triggers
(
    sched_name    varchar(120) not null,
    trigger_name  varchar(200) not null,
    trigger_group varchar(200) not null,
    str_prop_1    varchar(512),
    str_prop_2    varchar(512),
    str_prop_3    varchar(512),
    int_prop_1    integer,
    int_prop_2    integer,
    long_prop_1   bigint,
    long_prop_2   bigint,
    dec_prop_1    numeric(13, 4),
    dec_prop_2    numeric(13, 4),
    bool_prop_1   boolean,
    bool_prop_2   boolean,
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group)
        references qrtz_triggers (sched_name, trigger_name, trigger_group)
);

comment on table qrtz_simprop_triggers is '同步机制的行锁表';
comment on column qrtz_simprop_triggers.sched_name is '调度名称';
comment on column qrtz_simprop_triggers.trigger_name is 'qrtz_triggers 表 trigger_name 的外键';
comment on column qrtz_simprop_triggers.trigger_group is 'qrtz_triggers 表 trigger_group 的外键';
comment on column qrtz_simprop_triggers.str_prop_1 is 'String 类型的 trigger 的第一个参数';
comment on column qrtz_simprop_triggers.str_prop_2 is 'String 类型的 trigger 的第二个参数';
comment on column qrtz_simprop_triggers.str_prop_3 is 'String 类型的 trigger 的第三个参数';
comment on column qrtz_simprop_triggers.int_prop_1 is 'int 类型的 trigger 的第一个参数';
comment on column qrtz_simprop_triggers.int_prop_2 is 'String 类型的 trigger 的第二个参数';
comment on column qrtz_simprop_triggers.long_prop_1 is 'long 类型的 trigger 的第一个参数';
comment on column qrtz_simprop_triggers.long_prop_2 is 'long 类型的 trigger 的第二个参数';
comment on column qrtz_simprop_triggers.dec_prop_1 is 'decimal 类型的 trigger 的第一个参数';
comment on column qrtz_simprop_triggers.dec_prop_2 is 'decimal 类型的 trigger 的第二个参数';
comment on column qrtz_simprop_triggers.bool_prop_1 is 'Boolean 类型的 trigger 的第一个参数';
comment on column qrtz_simprop_triggers.bool_prop_2 is 'Boolean 类型的 trigger 的第二个参数';

create table qrtz_blob_triggers
(
    sched_name    varchar(120) not null,
    trigger_name  varchar(200) not null,
    trigger_group varchar(200) not null,
    blob_data     bytea,
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group)
        references qrtz_triggers (sched_name, trigger_name, trigger_group)
);

comment on table qrtz_blob_triggers is 'Blob 类型的触发器表';
comment on column qrtz_blob_triggers.sched_name is '调度名称';
comment on column qrtz_blob_triggers.trigger_name is 'qrtz_triggers 表 trigger_name 的外键';
comment on column qrtz_blob_triggers.trigger_group is 'qrtz_triggers 表 trigger_group 的外键';
comment on column qrtz_blob_triggers.blob_data is '存放持久化 Trigger 对象';

create table qrtz_calendars
(
    sched_name    varchar(120) not null,
    calendar_name varchar(200) not null,
    calendar      bytea        not null,
    primary key (sched_name, calendar_name)
);

comment on table qrtz_calendars is '日历信息表';
comment on column qrtz_calendars.sched_name is '调度名称';
comment on column qrtz_calendars.calendar_name is '日历名称';
comment on column qrtz_calendars.calendar is '存放持久化 calendar 对象';

create table qrtz_paused_trigger_grps
(
    sched_name    varchar(120) not null,
    trigger_group varchar(200) not null,
    primary key (sched_name, trigger_group)
);

comment on table qrtz_paused_trigger_grps is '暂停的触发器表';
comment on column qrtz_paused_trigger_grps.sched_name is '调度名称';
comment on column qrtz_paused_trigger_grps.trigger_group is 'qrtz_triggers 表 trigger_group 的外键';

create table qrtz_fired_triggers
(
    sched_name        varchar(120) not null,
    entry_id          varchar(95)  not null,
    trigger_name      varchar(200) not null,
    trigger_group     varchar(200) not null,
    instance_name     varchar(200) not null,
    fired_time        bigint       not null,
    sched_time        bigint       not null,
    priority          integer      not null,
    state             varchar(16)  not null,
    job_name          varchar(200),
    job_group         varchar(200),
    is_nonconcurrent  boolean,
    requests_recovery boolean,
    primary key (sched_name, entry_id)
);

comment on table qrtz_fired_triggers is '已触发的触发器表';
comment on column qrtz_fired_triggers.sched_name is '调度名称';
comment on column qrtz_fired_triggers.entry_id is '调度器实例ID';
comment on column qrtz_fired_triggers.trigger_name is 'qrtz_triggers 表 trigger_name 的外键';
comment on column qrtz_fired_triggers.trigger_group is 'qrtz_triggers 表 trigger_group 的外键';
comment on column qrtz_fired_triggers.instance_name is '调度器实例名';
comment on column qrtz_fired_triggers.fired_time is '触发的时间';
comment on column qrtz_fired_triggers.sched_time is '定时器制定的时间';
comment on column qrtz_fired_triggers.priority is '优先级';
comment on column qrtz_fired_triggers.state is '状态';
comment on column qrtz_fired_triggers.job_name is '任务名称';
comment on column qrtz_fired_triggers.job_group is '任务组名';
comment on column qrtz_fired_triggers.is_nonconcurrent is '是否并发';
comment on column qrtz_fired_triggers.requests_recovery is '是否接受恢复执行';

create table qrtz_scheduler_state
(
    sched_name        varchar(120) not null,
    instance_name     varchar(200) not null,
    last_checkin_time bigint       not null,
    checkin_interval  bigint       not null,
    primary key (sched_name, instance_name)
);

comment on table qrtz_scheduler_state is '调度器状态表';
comment on column qrtz_scheduler_state.sched_name is '调度名称';
comment on column qrtz_scheduler_state.instance_name is '实例名称';
comment on column qrtz_scheduler_state.last_checkin_time is '上次检查时间';
comment on column qrtz_scheduler_state.checkin_interval is '检查间隔时间';

create table qrtz_locks
(
    sched_name varchar(120) not null,
    lock_name  varchar(40)  not null,
    primary key (sched_name, lock_name)
);

comment on table qrtz_locks is '存储的悲观锁信息表';
comment on column qrtz_locks.sched_name is '调度名称';
comment on column qrtz_locks.lock_name is '悲观锁名称';

create index idx_qrtz_j_req_recovery on qrtz_job_details(sched_name,requests_recovery);
create index idx_qrtz_j_grp on qrtz_job_details(sched_name,job_group);

create index idx_qrtz_t_j on qrtz_triggers(sched_name,job_name,job_group);
create index idx_qrtz_t_jg on qrtz_triggers(sched_name,job_group);
create index idx_qrtz_t_c on qrtz_triggers(sched_name,calendar_name);
create index idx_qrtz_t_g on qrtz_triggers(sched_name,trigger_group);
create index idx_qrtz_t_state on qrtz_triggers(sched_name,trigger_state);
create index idx_qrtz_t_n_state on qrtz_triggers(sched_name,trigger_name,trigger_group,trigger_state);
create index idx_qrtz_t_n_g_state on qrtz_triggers(sched_name,trigger_group,trigger_state);
create index idx_qrtz_t_next_fire_time on qrtz_triggers(sched_name,next_fire_time);
create index idx_qrtz_t_nft_st on qrtz_triggers(sched_name,trigger_state,next_fire_time);
create index idx_qrtz_t_nft_misfire on qrtz_triggers(sched_name,misfire_instr,next_fire_time);
create index idx_qrtz_t_nft_st_misfire on qrtz_triggers(sched_name,misfire_instr,next_fire_time,trigger_state);
create index idx_qrtz_t_nft_st_misfire_grp on qrtz_triggers(sched_name,misfire_instr,next_fire_time,trigger_group,trigger_state);

create index idx_qrtz_ft_trig_inst_name on qrtz_fired_triggers(sched_name,instance_name);
create index idx_qrtz_ft_inst_job_req_rcvry on qrtz_fired_triggers(sched_name,instance_name,requests_recovery);
create index idx_qrtz_ft_j_g on qrtz_fired_triggers(sched_name,job_name,job_group);
create index idx_qrtz_ft_jg on qrtz_fired_triggers(sched_name,job_group);
create index idx_qrtz_ft_t_g on qrtz_fired_triggers(sched_name,trigger_name,trigger_group);
create index idx_qrtz_ft_tg on qrtz_fired_triggers(sched_name,trigger_group);

commit;
