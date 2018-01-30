-- 删除arm数据库
DROP DATABASE IF EXISTS lizuo ;

-- 创建arm数据库
CREATE DATABASE lizuo CHARACTER SET UTF8 ;

-- 使用arm数据库
USE lizuo ;

DROP TABLE IF EXISTS `action`;
CREATE TABLE `action` (
  `actionid` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `flag` varchar(50) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `sflag` int(11) DEFAULT NULL COMMENT '1为后台列表显示，0是不显示',
  PRIMARY KEY (`actionid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of action
-- ----------------------------
INSERT INTO `action` VALUES ('1', '增加用户', 'member:add', '/pages/back/member/add', '0');
INSERT INTO `action` VALUES ('2', '增加用户', 'member:addPre', '/pages/back/member/addPre', '1');
INSERT INTO `action` VALUES ('3', '用户列表', 'member:list', '/pages/back/member/list', '1');
INSERT INTO `action` VALUES ('4', '密码修改', 'member:editPasswordPre', '/pages/back/member/editPasswordPre', '1');
INSERT INTO `action` VALUES ('5', '锁定或激活', 'member:lockOrUnlock', '/pages/back/member/lockOrUnlock', '1');
INSERT INTO `action` VALUES ('6', '修改用户', 'member:editMember', '/pages/back/member/editMember', '0');
INSERT INTO `action` VALUES ('7', '修改用户', 'member:editMemberPre', '/pages/back/member/editMemberPre', '1');
INSERT INTO `action` VALUES ('8', '密码修改', 'member:editPassword', '/pages/back/member/editPassword', '0');
INSERT INTO `action` VALUES ('9', '成本记录', 'cost:addPre', '/pages/back/cost/addPre', '1');
INSERT INTO `action` VALUES ('10', '成本记录', 'cost:add', '/pages/back/cost/add', '0');
INSERT INTO `action` VALUES ('11', '成本列表', 'cost:list', '/pages/back/cost/list', '1');
INSERT INTO `action` VALUES ('12', '成本修改', 'cost:editPre', '/pages/back/cost/editPre', '1');
INSERT INTO `action` VALUES ('13', '成本修改', 'cost:edit', '/pages/back/cost/edit', '0');
INSERT INTO `action` VALUES ('14', '成本删除', 'cost:delete', '/pages/back/cost/delete', '1');

-- ----------------------------
-- Table structure for client
-- ----------------------------
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of client
-- ----------------------------

-- ----------------------------
-- Table structure for cost
-- ----------------------------
DROP TABLE IF EXISTS `cost`;
CREATE TABLE `cost` (
  `costid` int(11) NOT NULL AUTO_INCREMENT,
  `memberid` varchar(50) DEFAULT NULL COMMENT '是谁花的钱',
  `time` datetime DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL COMMENT '成本费用标题',
  `dflag` int(11) unsigned zerofill DEFAULT NULL COMMENT '正常是0，删除是1',
  PRIMARY KEY (`costid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cost
-- ----------------------------
INSERT INTO `cost` VALUES ('1', null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `memberid` varchar(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `sflag` int(11) DEFAULT NULL COMMENT '是否是超级管理员0是超级管理员，1是普通管理员，999是锁定用户',
  `age` int(11) DEFAULT NULL,
  `sex` varchar(2) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `note` text,
  `regdate` datetime DEFAULT NULL COMMENT '注册日期',
  `eflag` int(11) DEFAULT NULL COMMENT '错误标记次数',
  PRIMARY KEY (`memberid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('hh', '黄浩', '/upload/member/nophoto.png', '1', '1', '26', '男', '15151515155', null, null, null);
INSERT INTO `member` VALUES ('lh', '黎杭', '/upload/member/d6ecbb19-ea47-44af-8328-3936e2661ea3.jpeg', '151001lhaijj13', '0', '26', '男', '18223170162', null, null, '0');

-- ----------------------------
-- Table structure for member_role
-- ----------------------------
DROP TABLE IF EXISTS `member_role`;
CREATE TABLE `member_role` (
  `roleid` int(11) DEFAULT NULL,
  `memberid` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of member_role
-- ----------------------------
INSERT INTO `member_role` VALUES ('1', 'lh');
INSERT INTO `member_role` VALUES ('2', 'lh');
INSERT INTO `member_role` VALUES ('2', 'hh');
INSERT INTO `member_role` VALUES ('3', 'lh');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `roleid` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `flag` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理员管理', 'member');
INSERT INTO `role` VALUES ('2', '部门管理', 'dept');
INSERT INTO `role` VALUES ('3', '财务管理', 'cost');

-- ----------------------------
-- Table structure for role_action
-- ----------------------------
DROP TABLE IF EXISTS `role_action`;
CREATE TABLE `role_action` (
  `roleid` int(11) DEFAULT NULL,
  `actionid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_action
-- ----------------------------
INSERT INTO `role_action` VALUES ('1', '1');
INSERT INTO `role_action` VALUES ('1', '2');
INSERT INTO `role_action` VALUES ('1', '3');
INSERT INTO `role_action` VALUES ('1', '4');
INSERT INTO `role_action` VALUES ('1', '5');
INSERT INTO `role_action` VALUES ('1', '6');
INSERT INTO `role_action` VALUES ('1', '7');
INSERT INTO `role_action` VALUES ('1', '8');
INSERT INTO `role_action` VALUES ('3', '9');
INSERT INTO `role_action` VALUES ('3', '13');
INSERT INTO `role_action` VALUES ('3', '12');
INSERT INTO `role_action` VALUES ('3', '11');
INSERT INTO `role_action` VALUES ('3', '10');
INSERT INTO `role_action` VALUES ('3', '14');
