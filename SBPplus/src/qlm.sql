-- 删除arm数据库
DROP DATABASE IF EXISTS qlm ;

-- 创建arm数据库
CREATE DATABASE qlm CHARACTER SET UTF8 ;

-- 使用arm数据库
USE qlm ;

DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (
  `clientid` int(11) NOT NULL AUTO_INCREMENT,
  `claim` text,
  `phone` varchar(15) DEFAULT NULL,
  `budget` varchar(10) DEFAULT NULL,
  `name` varchar(10) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `company` varchar(50) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `pubdate` datetime DEFAULT NULL,
  PRIMARY KEY (`clientid`)
) ENGINE=InnoDB;

drop table if exists action;

drop table if exists member;

drop table if exists member_role;

drop table if exists role;

drop table if exists role_action;

/*==============================================================*/
/* Table: action                                                */
/*==============================================================*/
create table action
(
  actionid             int not null AUTO_INCREMENT,
  title                varchar(50),
  flag                 VARCHAR(50),
  primary key (actionid)
)ENGINE=InnoDB;

/*==============================================================*/
/* Table: member                                                */
/*==============================================================*/
create table member
(
  memberid             varchar(50) not null,
  name                 varchar(50),
  photo                varchar(32),
  password             varchar(32),
  sflag                int,
  age                  int,
  sex                  varchar(2),
  phone                varchar(20),
  note                 text,
  eflag                int,
  primary key (memberid)
)ENGINE=InnoDB;

/*==============================================================*/
/* Table: member_role                                           */
/*==============================================================*/
create table member_role
(
  roleid               int,
  memberid             varchar(50)
)ENGINE=InnoDB;

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table role
(
  roleid               int not null AUTO_INCREMENT,
  title                varchar(50),
  flag                 VARCHAR(50),
  primary key (roleid)
)ENGINE=InnoDB;

/*==============================================================*/
/* Table: role_action                                           */
/*==============================================================*/
create table role_action
(
  roleid               int,
  actionid             int
)ENGINE=InnoDB;

alter table member_role add constraint FK_Reference_2 foreign key (roleid)
references role (roleid) on delete restrict on update restrict;

alter table member_role add constraint FK_Reference_5 foreign key (memberid)
references member (memberid) on delete restrict on update restrict;

alter table role_action add constraint FK_Reference_3 foreign key (roleid)
references role (roleid) on delete restrict on update restrict;

alter table role_action add constraint FK_Reference_4 foreign key (actionid)
references action (actionid) on delete restrict on update restrict;
# 增加角色信息
INSERT INTO role(title, flag) VALUE ('管理员','member');
# 增加权限
INSERT INTO action(title, flag)VALUE ('增加管理员','member:add');
INSERT INTO action(title, flag)VALUE ('管理员列表','member:list');
# 增加角色权限关系

INSERT INTO role_action (roleid, actionid) VALUES (1,1);
INSERT INTO role_action (roleid, actionid) VALUES (1,2);
# 增加管理员和角色
insert into member(memberid,password,name,sflag)VALUES ('lh','151001lhaijj13','lihang','0');
INSERT INTO member_role(memberid,roleid)VALUES ('lh',1);


