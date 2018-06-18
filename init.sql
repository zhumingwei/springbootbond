CREATE DATABASE /*!32312 IF NOT EXISTS*/`bond` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `bond`;
-- 主账户表
DROP TABLE IF EXISTS account;
-- createdby  createddate   updateby updateddate
CREATE TABLE account (
  `id` bigint(20) not null AUTO_INCREMENT COMMENT '主账户ID',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(32) not NULL COMMENT '密码',
  `phonenumber` varchar(15) not NULL COMMENT '手机号',
  `avatar` varchar(100) NOT NULL COMMENT '头像',
  `uid` bigint(20) NOT NULL COMMENT '用户id外键连接用户表id',
  `createby` bigint(20) NOT NULL DEFAULT -1 COMMENT '用户表id',
  `createdate` datetime DEFAULT NOW() COMMENT '创建时间',
  `updateby` bigint(20) NOT NULL DEFAULT -1 COMMENT '用户id',
  `updatedate` datetime DEFAULT NOW() COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

-- 手机账户表 mobileuser  (mobilenumber,c_id （主账户id） )

-- 用户表 user
DROP TABLE IF EXISTS user;

CREATE TABLE user(
  `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '用户id' ,
  `realname` varchar(20) COMMENT '用户名称',
  `user_level` smallint NOT NUll DEFAULT 0 COMMENT '用户等级',
  `user_state` smallint NOT NUll DEFAULT 0 COMMENT '用户状态',
  `cid` bigint(20) not null COMMENT '公司id',
  `is_delete` tinyint NOT NULL DEFAULT 0  COMMENT '是否被删除',
  `createby` bigint(20) NOT NULL DEFAULT -1 COMMENT '用户表id',
  `createdate` datetime DEFAULT NOW() COMMENT '创建时间',
  `updateby` bigint(20) NOT NULL DEFAULT -1 COMMENT '用户id',
  `updatedate` datetime DEFAULT NOW() COMMENT '更新时间'

) ENGINE=MyISAM AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;


insert into user (`realname`,`user_level`,`user_state`,`cid`,`createby`,`updateby`)
    values("朱明伟",999,0,0,0,0);

insert into account(`username`,`password`,`phonenumber`,`avatar`,`uid`,`createby`,`updateby`)
    values("zhumingwei","8f742683af9c19138037998ad129dda2","17621791737","http://p9yjgmoug.bkt.clouddn.com/5000000025385-4KpbGt7bNRVkQcYvGRqV9VngvU_Z7rV_",101,101,101);