/**
  -- 清除表数据
    delete from brc_pay_order;
    delete from brc_pay_payment;
    delete from brc_pay_refund;
 */
-- 1、订单表
create table brc_pay_order
(
    id              bigint                             primary key comment '订单ID',
    title           varchar(256)                       null comment '订单标题',
    order_no        varchar(50)                        null comment '商户订单编号',
    payment_type    varchar(10)                        null comment '支付类型',
    user_id         bigint                             null comment '用户id',
    product_id      bigint                             null comment '支付产品id',
    total_fee       int                                null comment '订单金额(分)',
    code_url        varchar(50)                        null comment '订单二维码连接',
    order_status    varchar(10)                        null comment '订单状态',
    remark          varchar(500)                       null comment '备注',
    version         int                                not null comment '版本号',
    deleted         tinyint  default 0                 not null comment '删除标识（0：存在，1：已删除）',
    created_user_id bigint                             null comment '创建者ID',
    created_time    datetime                           null comment '创建时间',
    updated_user_id bigint                             null comment '修改者ID',
    updated_time    datetime                           null comment '修改时间'
)  comment '订单表';

-- 2、支付记录表
create table brc_pay_payment
(
    id              bigint                             primary key comment '支付记录ID',
    order_no        varchar(50)                        null comment '商户订单编号',
    transaction_id  varchar(50)                        null comment '支付系统交易编号',
    payment_type    varchar(20)                        null comment '支付类型',
    trade_type      varchar(20)                        null comment '交易类型',
    trade_state     varchar(50)                        null comment '交易状态',
    payer_total     int                                null comment '支付金额(分)',
    content         text                               null comment '通知参数',
    version         int                                not null comment '版本号',
    deleted         tinyint  default 0                 not null comment '删除标识（0：存在，1：已删除）',
    created_user_id bigint                             null comment '创建者ID',
    created_time    datetime                           null comment '创建时间',
    updated_user_id bigint                             null comment '修改者ID',
    updated_time    datetime                           null comment '修改时间'
) comment '支付记录表';

-- 3、商品表
create table brc_pay_product
(
    id              bigint                             primary key comment '商品ID',
    title           varchar(20)                        null comment '商品名称',
    price           int                                null comment '价格（分）',
    version         int                                not null comment '版本号',
    deleted         tinyint  default 0                 not null comment '删除标识（0：存在，1：已删除）',
    created_user_id bigint                             null comment '创建者ID',
    created_time    datetime                           null comment '创建时间',
    updated_user_id bigint                             null comment '修改者ID',
    updated_time    datetime                           null comment '修改时间'
) comment '商品表';

-- 商品表数据
INSERT INTO brc_pay_product (id, title, price, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, 'Java课程', 1, 1, 0, null, '2024-02-27 08:04:58', null, null);
INSERT INTO brc_pay_product (id, title, price, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '大数据课程', 1, 1, 0, null, '2024-02-27 08:04:58', null, null);
INSERT INTO brc_pay_product (id, title, price, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (3, '前端课程', 1, 1, 0, null, '2024-02-27 08:04:58', null, null);
INSERT INTO brc_pay_product (id, title, price, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (4, 'UI课程', 1, 1, 0, null, '2024-02-27 08:04:58', null, null);

-- 4、退款单表
create table brc_pay_refund
(
    id              bigint                             primary key comment '退款单ID',
    order_no        varchar(50)                        null comment '商户订单编号',
    refund_no       varchar(50)                        null comment '商户退款单编号',
    refund_id       varchar(50)                        null comment '支付系统退款单号',
    total_fee       int                                null comment '原订单金额(分)',
    refund          int                                null comment '退款金额(分)',
    reason          varchar(50)                        null comment '退款原因',
    refund_status   varchar(20)                        null comment '退款状态',
    content_return  text                               null comment '申请退款返回参数',
    content_notify  text                               null comment '退款结果通知参数',
    version         int                                not null comment '版本号',
    deleted         tinyint  default 0                 not null comment '删除标识（0：存在，1：已删除）',
    created_user_id bigint                             null comment '创建者ID',
    created_time    datetime                           null comment '创建时间',
    updated_user_id bigint                             null comment '修改者ID',
    updated_time    datetime                           null comment '修改时间'
) comment '退款单表';


