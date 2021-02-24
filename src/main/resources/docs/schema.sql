CREATE DATABASE bryce_boot DEFAULT CHARSET utf8mb4;
use bryce_boot;

CREATE TABLE bryce_user(
  id BIGINT NOT NULL COMMENT '主键',
  name VARCHAR(100) COMMENT '姓名',
  username VARCHAR(50) not null COMMENT '账号',
  password VARCHAR(256) not null COMMENT '密码',
  email VARCHAR(50) COMMENT '邮箱',
  phone VARCHAR(20) COMMENT '手机',
  sex CHAR(1) check (sex in ('M', 'F')) COMMENT '性别',
  remark VARCHAR(500) COMMENT '备注',
  create_time DATETIME COMMENT '创建时间',
  create_user_id BIGINT COMMENT '创建人主键',
  update_time DATETIME COMMENT '修改时间',
  update_user_id BIGINT COMMENT '修改人主键',
  primary key(id)
) ENGINE InnoDB COMMENT '用户表';

CREATE TABLE bryce_dept(
  id BIGINT NOT NULL COMMENT '主键',
  name VARCHAR(100) NOT NULL COMMENT '部门名称',
  code VARCHAR(30) COMMENT '部门编码',
  remark VARCHAR(500) COMMENT '备注',
  create_time DATETIME COMMENT '创建时间',
  create_user_id BIGINT COMMENT '创建人主键',
  update_time DATETIME COMMENT '修改时间',
  update_user_id BIGINT COMMENT '修改人主键',
  primary key(id)
) ENGINE InnoDB COMMENT '部门表';
