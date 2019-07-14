#------学子商城数据库详情------#

#--------创建数据库tedu_store--------#
CREATE DATABASE tedu_store character set utf8;
USE tedu_store;


#-------创建用户表t_user-------#
CREATE TABLE t_user( 
	uid INT AUTO_INCREMENT COMMENT '用户名id',
	username VARCHAR(50) UNIQUE NOT NULL COMMENT '用户名',
	password CHAR(32) NOT NULL COMMENT '密码',
	salt CHAR(36)  COMMENT '盐值',
	gender INT COMMENT '性别，0-女，1-男',
	phone VARCHAR(20) COMMENT '手机号码',
	email VARCHAR(50) COMMENT '电子邮箱',
	avatar VARCHAR(100) COMMENT '头像',
	is_delete INT COMMENT '是否删除，0-未删除，1-已删除',
	created_user VARCHAR(50) COMMENT '创建人',
	created_time DATETIME COMMENT '创建时间',
	modified_user VARCHAR(50) COMMENT '最后修改人',
	modified_time DATETIME COMMENT '最后修改时间',
	PRIMARY KEY(uid)
) DEFAULT CHARSET=UTF8;


#-------创建收货地址表t_address-------#
CREATE TABLE t_address (
		aid INT AUTO_INCREMENT COMMENT '收货地址id',
		uid INT COMMENT '用户id',
		name VARCHAR(50) COMMENT '收货人姓名',
		province_code CHAR(6) COMMENT '省代号',
		province_name VARCHAR(50) COMMENT '省名称',
		city_code CHAR(6) COMMENT '市代号',
		city_name VARCHAR(50) COMMENT '市名称',
		area_code CHAR(6) COMMENT '区代号',
		area_name VARCHAR(50) COMMENT '区名称',
		zip CHAR(6) COMMENT '邮编',
		address VARCHAR(100) COMMENT '详细地址',
		phone VARCHAR(20) COMMENT '手机',
		tel VARCHAR(20) COMMENT '固话',
		tag VARCHAR(30) COMMENT '地址类型',
		is_default INT COMMENT '是否默认，0-非默认，1-默认',
		created_user VARCHAR(50) COMMENT '创建人',
		created_time DATETIME COMMENT '创建时间',
		modified_user VARCHAR(50) COMMENT '最后修改人',
		modified_time DATETIME COMMENT '最后修改时间',
		PRIMARY KEY (aid)
	) DEFAULT CHARSET=UTF8;


#-------创建购物车表t_cart-------#
CREATE TABLE t_cart (
	cid INT AUTO_INCREMENT COMMENT '购物车数据id',
	uid INT NOT NULL COMMENT '用户id',
	pid INT NOT NULL COMMENT '商品id',
	num INT NOT NULL COMMENT '数量',
	price BIGINT NOT NULL COMMENT '商品单价',
	created_user VARCHAR(50) COMMENT '创建人',
	created_time DATETIME COMMENT '创建时间',
	modified_user VARCHAR(50) COMMENT '最后修改人',
	modified_time DATETIME COMMENT '最后修改时间',
	PRIMARY KEY (cid)
) DEFAULT CHARSET=UTF8;


#-------创建订单表t_order-------#
CREATE TABLE t_order (
		oid INT AUTO_INCREMENT COMMENT '订单id',
		uid INT COMMENT '用户id',
		recv_name VARCHAR(50) COMMENT '收货人receiver姓名',
		recv_phone VARCHAR(20) COMMENT '收货人电话',
		recv_province VARCHAR(50) COMMENT '收货地址所在省',
		recv_city VARCHAR(50) COMMENT '收货地址所在市',
		recv_area VARCHAR(50) COMMENT '收货地址所在区',
		recv_address VARCHAR(100) COMMENT '详细收货地址',
		total_price BIGINT COMMENT '总价',
		status INT COMMENT '状态：0-未支付，1-已支付，2-已取消',
		order_time DATETIME COMMENT '下单时间',
		pay_time DATETIME COMMENT '支付时间',
		created_user VARCHAR(50) COMMENT '创建人',
		created_time DATETIME COMMENT '创建时间',
		modified_user VARCHAR(50) COMMENT '最后修改人',
		modified_time DATETIME COMMENT '最后修改时间',
		PRIMARY KEY (oid)
	) DEFAULT CHARSET=UTF8;


#-------创建订单商品表t_order_item-------#
CREATE TABLE t_order_item (
		id INT AUTO_INCREMENT COMMENT 'id',
		oid INT COMMENT '归属的订单id',
		pid INT COMMENT '商品id',
		title VARCHAR(100) COMMENT '商品标题',
		image VARCHAR(500) COMMENT '商品图片',
		price BIGINT COMMENT '商品单价',
		num INT COMMENT '购买数量',
		created_user VARCHAR(50) COMMENT '创建人',
		created_time DATETIME COMMENT '创建时间',
		modified_user VARCHAR(50) COMMENT '最后修改人',
		modified_time DATETIME COMMENT '最后修改时间',
		PRIMARY KEY (id)
	) DEFAULT CHARSET=UTF8;










