/*

Date: 2018-09-11 10:09:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for DS_ANALYSIS_PANEL
-- ----------------------------
DROP TABLE IF EXISTS `DS_ANALYSIS_PANEL`;
CREATE TABLE `DS_ANALYSIS_PANEL` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL COMMENT '父级菜单id',
  `level` int(11) NOT NULL COMMENT '菜单级别',
  `name` varchar(50) NOT NULL COMMENT '菜单显示名称',
  `url` varchar(128) NOT NULL COMMENT '菜单对应页面url',
  `description` varchar(255) DEFAULT NULL COMMENT '菜单描述',
  `item_order` int(11) NOT NULL COMMENT '菜单项顺序',
  `icon` blob COMMENT '菜单项图标',
  `status` tinyint(255) DEFAULT NULL COMMENT '发布状态--0：未发布；1：已发布（发布之后面板本身无法编辑，取消发布需该资源无对外授权）',
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for DS_COLLECT
-- ----------------------------
DROP TABLE IF EXISTS `DS_COLLECT`;
CREATE TABLE `DS_COLLECT` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `analysis_panel_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `content` text NOT NULL,
  `share_flag` tinyint(1) DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for DS_PAGE
-- ----------------------------
DROP TABLE IF EXISTS `DS_PAGE`;
CREATE TABLE `DS_PAGE` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `template_id` int(11) DEFAULT NULL,
  `content` text,
  `analysis_panel_id` int(11) DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `menu_id` (`analysis_panel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for DS_PANEL_DEPARTMENT_AUTH
-- ----------------------------
DROP TABLE IF EXISTS `DS_PANEL_DEPARTMENT_AUTH`;
CREATE TABLE `DS_PANEL_DEPARTMENT_AUTH` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `analysis_panel_id` int(11) NOT NULL,
  `department_id` int(11) NOT NULL,
  `share_flag` tinyint(1) NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for DS_PANEL_RESOURCE
-- ----------------------------
DROP TABLE IF EXISTS `DS_PANEL_RESOURCE`;
CREATE TABLE `DS_PANEL_RESOURCE` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `analysis_panel_id` int(11) NOT NULL,
  `type_code` varchar(255) NOT NULL COMMENT '资源类型——04：图表资源；08：报表资源；09：面板资源',
  `resource_id` int(11) NOT NULL,
  `div_model_id` varchar(25) NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `widget_id` (`resource_id`),
  KEY `menu_id` (`analysis_panel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for DS_PANEL_USER_AUTH
-- ----------------------------
DROP TABLE IF EXISTS `DS_PANEL_USER_AUTH`;
CREATE TABLE `DS_PANEL_USER_AUTH` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `analysis_panel_id` int(11) NOT NULL,
  `department_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `share_flag` tinyint(1) NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for DS_SHARE
-- ----------------------------
DROP TABLE IF EXISTS `DS_SHARE`;
CREATE TABLE `DS_SHARE` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `analysis_panel_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `receive_user_id` int(11) DEFAULT NULL,
  `receive_department_id` int(11) DEFAULT NULL,
  `content` text NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for DS_SYSLOG
-- ----------------------------
DROP TABLE IF EXISTS `DS_SYSLOG`;
CREATE TABLE `DS_SYSLOG` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `address` varchar(255) NOT NULL,
  `params` text,
  `status` varchar(25) NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for DS_TEMPLATE
-- ----------------------------
DROP TABLE IF EXISTS `DS_TEMPLATE`;
CREATE TABLE `DS_TEMPLATE` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `content` text,
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
