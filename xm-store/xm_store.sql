#----小米商城数据库详情----#
#-------创建数据库：xm_store-------#
CREATE DATABASE xm_store CHARACTER SET UTF8;

USE xm_store;

#-------1. 创建用户表：xm_user------#
CREATE TABLE xm_user (
		`uid` INT AUTO_INCREMENT COMMENT '用户id',
 		`username` VARCHAR(50) UNIQUE NOT NULL COMMENT '用户名',
	  	`password` CHAR(36) NOT NULL COMMENT '密码',
	  	`salt` CHAR(36)  COMMENT '盐值',
	  	`gender` INT  COMMENT '性别，0-女，1-男',
	  	`phone` VARCHAR(20) UNIQUE NOT NULL COMMENT '手机号码',
	  	`avatar` VARCHAR(100) COMMENT '头像',
	  	`is_delete` INT NOT NULL COMMENT '是否删除，0-未删除，1-已删除',
	  	`created_user` VARCHAR(50)  COMMENT '创建人',
	  	`created_time` DATETIME  COMMENT '创建时间',  	
	  	`modified_user` VARCHAR(50)  COMMENT '最后修改人',
	  	`modified_time` DATETIME  COMMENT '最后修改时间',
  		PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#-------2. 创建收货地址表：xm_address------#
CREATE TABLE xm_address(
	`aid` INT AUTO_INCREMENT COMMENT '收货地址id',
	`uid` INT COMMENT '用户id',
	`name` VARCHAR(50) COMMENT '收货人姓名',
	`province_code` CHAR(6) COMMENT '省代号',
	`province_name` VARCHAR(50) COMMENT '省名称',
	`city_code` CHAR(6) COMMENT '市代号',
	`city_name` VARCHAR(50) COMMENT '市名称',
	`area_code` CHAR(6) COMMENT '区代号',
	`area_name` VARCHAR(50) COMMENT '区名称',
	`zip` CHAR(6) COMMENT '邮编',
	`address` VARCHAR(150) COMMENT '详细地址',
	`phone` VARCHAR(20) COMMENT '手机',
	`tel` VARCHAR(20) COMMENT '固话',
	`tag` VARCHAR(30) COMMENT '地址类型',
	`is_default` INT COMMENT '是否默认,0-非默认,1-默认',
	`created_user` VARCHAR(50) COMMENT '创建人',
	`created_time` DATETIME COMMENT '创建时间',
	`modified_user` VARCHAR(50) COMMENT '最后修改人',
	`modified_time` DATETIME COMMENT '最后修改时间',
	PRIMARY KEY(`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#-----3. 创建商品表t_product-------#
CREATE TABLE `t_product` (
  `pid` int(20) NOT NULL COMMENT '商品id',
  `category_id` int(20) DEFAULT NULL COMMENT '分类id',
  `item_type` varchar(100) DEFAULT NULL COMMENT '商品系列',
  `title` varchar(100) DEFAULT NULL COMMENT '商品标题',
  `sell_point` varchar(150) DEFAULT NULL COMMENT '商品卖点',
  `price` bigint(20) DEFAULT NULL COMMENT '商品单价',
  `num` int(10) DEFAULT NULL COMMENT '库存数量',
  `image` varchar(500) DEFAULT NULL COMMENT '图片路径',
  `status` int(1) DEFAULT '1' COMMENT '商品状态  1：上架   2：下架   3：删除',
  `priority` int(10) DEFAULT NULL COMMENT '显示优先级',
  `created_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modified_user` varchar(50) DEFAULT NULL COMMENT '最后修改人',
  `modified_time` datetime DEFAULT NULL COMMENT '最后修改时间',
 
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#---插入商品表的测试数据----#
INSERT INTO t_product VALUES
(100001, 1, "手机", "小米MIX", "5月9日-21日享花呗12期分期免息", 3499, 9999, 
"/sahgdagdhaghbc1/", 1, 10, "超级管理员", now(), "超级管理员", now()),
(100002, 2, "电视", "小米电视3s 55英寸", "5月9日，下单立减200元", 3999, 9999, 
"/sahgdagdhaghbc2/", 1, 8, "超级管理员", now(), "超级管理员", now()),
(100003, 1, "手机", "小米5s", "5月9日-10日，下单立减200元", 1999, 9999, 
"/sahgdagdhaghbc1/", 1, 8, "超级管理员", now(), "超级管理员", now()),
(100004, 3, "电脑", "小米笔记本", "更轻更薄，像杂志一样随身携带", 3599, 9999, 
"/sahgdagdhaghbc2/", 1, 9, "超级管理员", now(), "超级管理员", now()),
(100005, 1, "小米手机", "小米手机5 64GB", "5月9日-10日，下单立减100元", 1799, 9999, 
"/sahgdagdhaghbc5/", 1, 10, "超级管理员", now(), "超级管理员", now());






































