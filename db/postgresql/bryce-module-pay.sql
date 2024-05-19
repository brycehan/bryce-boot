/**
  -- 清除表数据
    delete from brc_pay_order;
    delete from brc_pay_payment;
    delete from brc_pay_product;
    delete from brc_pay_refund;
 */

-- 1、订单表
create table brc_pay_order
(
    id              bigint                not null primary key,
    title           varchar(256),
    order_no        varchar(50),
    payment_type    varchar(10),
    user_id         bigint,
    product_id      bigint,
    total_fee       integer,
    code_url        varchar(50),
    order_status    varchar(10),
    remark          varchar(500),
    version         integer               not null,
    deleted         boolean default false not null,
    created_user_id bigint,
    created_time    timestamp,
    updated_user_id bigint,
    updated_time    timestamp
);

comment on table brc_pay_order is '订单表';

comment on column brc_pay_order.id              is '订单ID';
comment on column brc_pay_order.title           is '订单标题';
comment on column brc_pay_order.order_no        is '商户订单编号';
comment on column brc_pay_order.payment_type    is '支付类型';
comment on column brc_pay_order.user_id         is '用户id';
comment on column brc_pay_order.product_id      is '支付产品id';
comment on column brc_pay_order.total_fee       is '订单金额(分)';
comment on column brc_pay_order.code_url        is '订单二维码连接';
comment on column brc_pay_order.order_status    is '订单状态';
comment on column brc_pay_order.remark          is '备注';
comment on column brc_pay_order.version         is '版本号';
comment on column brc_pay_order.deleted         is '删除标识（0：存在，1：已删除）';
comment on column brc_pay_order.created_user_id is '创建者ID';
comment on column brc_pay_order.created_time    is '创建时间';
comment on column brc_pay_order.updated_user_id is '修改者ID';
comment on column brc_pay_order.updated_time    is '修改时间';

-- 2、支付记录表
create table brc_pay_payment
(
    id              bigint                not null primary key,
    order_no        varchar(50),
    transaction_id  varchar(50),
    payment_type    varchar(20),
    trade_type      varchar(20),
    trade_state     varchar(50),
    payer_total     integer,
    content         text,
    version         integer               not null,
    deleted         boolean default false not null,
    created_user_id bigint,
    created_time    timestamp,
    updated_user_id bigint,
    updated_time    timestamp
);

comment on table brc_pay_payment is '支付记录表';

comment on column brc_pay_payment.id              is '支付记录ID';
comment on column brc_pay_payment.order_no        is '商户订单编号';
comment on column brc_pay_payment.transaction_id  is '支付系统交易编号';
comment on column brc_pay_payment.payment_type    is '支付类型';
comment on column brc_pay_payment.trade_type      is '交易类型';
comment on column brc_pay_payment.trade_state     is '交易状态';
comment on column brc_pay_payment.payer_total     is '支付金额(分)';
comment on column brc_pay_payment.content         is '通知参数';
comment on column brc_pay_payment.version         is '版本号';
comment on column brc_pay_payment.deleted         is '删除标识（0：存在，1：已删除）';
comment on column brc_pay_payment.created_user_id is '创建者ID';
comment on column brc_pay_payment.created_time    is '创建时间';
comment on column brc_pay_payment.updated_user_id is '修改者ID';
comment on column brc_pay_payment.updated_time    is '修改时间';

-- 3、商品表
create table brc_pay_product
(
    id              bigint                not null primary key,
    title           varchar(20),
    price           integer,
    version         integer               not null,
    deleted         boolean default false not null,
    created_user_id bigint,
    created_time    timestamp,
    updated_user_id bigint,
    updated_time    timestamp
);

comment on table brc_pay_product is '商品表';

comment on column brc_pay_product.id              is '商品ID';
comment on column brc_pay_product.title           is '商品名称';
comment on column brc_pay_product.price           is '价格（分）';
comment on column brc_pay_product.version         is '版本号';
comment on column brc_pay_product.deleted         is '删除标识（0：存在，1：已删除）';
comment on column brc_pay_product.created_user_id is '创建者ID';
comment on column brc_pay_product.created_time    is '创建时间';
comment on column brc_pay_product.updated_user_id is '修改者ID';
comment on column brc_pay_product.updated_time    is '修改时间';

-- 商品表数据
INSERT INTO brc_pay_product (id, title, price, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (1, 'Java课程', 1, 1, false, null, '2024-02-27 08:04:58', null, null);
INSERT INTO brc_pay_product (id, title, price, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (2, '大数据课程', 1, 1, false, null, '2024-02-27 08:04:58', null, null);
INSERT INTO brc_pay_product (id, title, price, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (3, '前端课程', 1, 1, false, null, '2024-02-27 08:04:58', null, null);
INSERT INTO brc_pay_product (id, title, price, version, deleted, created_user_id, created_time, updated_user_id, updated_time) VALUES (4, 'UI课程', 1, 1, false, null, '2024-02-27 08:04:58', null, null);

-- 4、退款单表
create table brc_pay_refund
(
    id              bigint                not null primary key,
    order_no        varchar(50),
    refund_no       varchar(50),
    refund_id       varchar(50),
    total_fee       integer,
    refund          integer,
    reason          varchar(50),
    refund_status   varchar(20),
    content_return  text,
    content_notify  text,
    version         integer               not null,
    deleted         boolean default false not null,
    created_user_id bigint,
    created_time    timestamp,
    updated_user_id bigint,
    updated_time    timestamp
);

comment on table brc_pay_refund is '退款单表';

comment on column brc_pay_refund.id              is '退款单ID';
comment on column brc_pay_refund.order_no        is '商户订单编号';
comment on column brc_pay_refund.refund_no       is '商户退款单编号';
comment on column brc_pay_refund.refund_id       is '支付系统退款单号';
comment on column brc_pay_refund.total_fee       is '原订单金额(分)';
comment on column brc_pay_refund.refund          is '退款金额(分)';
comment on column brc_pay_refund.reason          is '退款原因';
comment on column brc_pay_refund.refund_status   is '退款状态';
comment on column brc_pay_refund.content_return  is '申请退款返回参数';
comment on column brc_pay_refund.content_notify  is '退款结果通知参数';
comment on column brc_pay_refund.version         is '版本号';
comment on column brc_pay_refund.deleted         is '删除标识（0：存在，1：已删除）';
comment on column brc_pay_refund.created_user_id is '创建者ID';
comment on column brc_pay_refund.created_time    is '创建时间';
comment on column brc_pay_refund.updated_user_id is '修改者ID';
comment on column brc_pay_refund.updated_time    is '修改时间';
