-- form
CREATE TABLE `brc_form`
(
    id                   bigint unsigned not null comment 'ID',
    `category_id`        bigint unsigned          DEFAULT '' COMMENT '分类id',
    `parent_id`          bigint unsigned          DEFAULT NULL COMMENT '父级Id',
    -- `mirror_id` char(36) DEFAULT NULL COMMENT '镜像id',
    -- `site_id` int(11) unsigned DEFAULT '0' COMMENT '站点id',
    `name`               varchar(64)              DEFAULT NULL COMMENT '表单名称',
    `code`               varchar(50)              DEFAULT NULL COMMENT '表单编码',
    `icon`               varchar(255)             DEFAULT NULL COMMENT '图标',
    `type`               varchar(30)     NOT NULL DEFAULT 'normal' COMMENT '表单类型',
    `version`            smallint(5) unsigned     DEFAULT '1' COMMENT '版本',
    `config`             text COMMENT '表单配置',
    `form_fields_config` text COMMENT '表单域配置',
    `public`             tinyint(1)      NOT NULL DEFAULT '0',
    `fill_end_time`      datetime                 DEFAULT NULL COMMENT '填报时间限制',
    `edit_end_time`      datetime                 DEFAULT NULL COMMENT '修改时间限制',
    `audit_end_time`     datetime                 DEFAULT NULL COMMENT '审核时间限制',

    `deleted_time`       datetime                 DEFAULT NULL COMMENT '删除时间',
    deleted_flag         tinyint(1)               default '0' comment '状态（0：正式数据，1：删除）',
    sort                 integer                  default '0' comment '显示顺序',
    enabled              tinyint(1)               default '1' comment '状态（0：停用，1：启用）',
    created_user_id      bigint unsigned          default null comment '创建人ID',
    created_username     varchar(50)              default '' comment '创建人账号',
    created_time         datetime                 default null comment '创建时间',
    updated_user_id      bigint unsigned          default null comment '修改人ID',
    updated_time         datetime                 default null comment '修改时间',
    remark               varchar(500)             default null comment '备注',
    primary key (id)
) engine InnoDB
  default charset utf8mb4 comment '自定义表单表';

CREATE TABLE brc_form_field
(
    id               bigint unsigned not null comment 'ID',
    `name`           varchar(64)     DEFAULT NULL COMMENT '字段名称',
    `code`           varchar(50)     NOT NULL COMMENT '字段代码',
    `form_id`        char(36)        NOT NULL COMMENT '关联表ID',
    `type`           varchar(32)     DEFAULT NULL COMMENT '字段类型',
    `description`    varchar(1000)   DEFAULT NULL COMMENT '介绍',
    `placeholder`    varchar(1000)   DEFAULT NULL COMMENT '占位符',
    `default`        varchar(255)    DEFAULT NULL COMMENT '默认值',
    `dict_id`        varchar(36)     DEFAULT NULL COMMENT '字典id',
    `rules`          varchar(255)    DEFAULT NULL COMMENT '校验规则',
    `config`         text            default null COMMENT '表单配置信息',
    order_number     integer         default '0' comment '显示顺序',
    enabled          tinyint(1)      default '1' comment '启用状态（0：停用，1：启用）',
    created_user_id  bigint unsigned default null comment '创建人ID',
    created_username varchar(50)     default null comment '创建人账号',
    created_time     datetime        default null comment '创建时间',
    updated_user_id  bigint unsigned default null comment '修改人ID',
    updated_time     datetime        default null comment '修改时间',
    remark           varchar(500)    default null comment '备注',
    primary key (id),
    UNIQUE KEY `uniq_form_code` (`form_id`, `code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



