/*
Navicat MySQL Data Transfer

Source Server         : jy
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : data_manage_v2

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2019-03-21 16:36:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cs_user
-- ----------------------------
DROP TABLE IF EXISTS `cs_user`;
CREATE TABLE `cs_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `p_id` int(11) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `user` varchar(32) DEFAULT NULL,
  `pwd` varchar(128) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `gender` varchar(2) DEFAULT NULL,
  `mobile_phone` varchar(15) DEFAULT NULL,
  `member_code` varchar(20) DEFAULT NULL,
  `unit` varchar(128) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user` (`id`,`user`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=987 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cs_user
-- ----------------------------
INSERT INTO `cs_user` VALUES ('299', null, null, null, '杨宇', 'yangyu', '123456', null, null, null, null, null, null, null, '2018-12-24 10:07:03', null);
INSERT INTO `cs_user` VALUES ('300', null, null, null, '王伟', 'wangwei', '123456', null, null, null, null, null, null, null, '2018-12-24 10:07:19', null);
INSERT INTO `cs_user` VALUES ('986', null, null, null, 'jy', 'jy', '123456', null, null, null, null, null, null, null, '2019-02-26 11:06:13', null);

-- ----------------------------
-- Table structure for dm_configure
-- ----------------------------
DROP TABLE IF EXISTS `dm_configure`;
CREATE TABLE `dm_configure` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `data_type` varchar(50) DEFAULT NULL COMMENT '存储的数据类型',
  `server_store_dir` varchar(50) DEFAULT NULL COMMENT '数据在服务器存储位置',
  `creater` varchar(50) DEFAULT NULL COMMENT '创建的文件的',
  `query_target` varchar(500) DEFAULT NULL COMMENT '最终查询报表的地址',
  `execute_target` varchar(500) DEFAULT NULL COMMENT '执行上传文件的地址',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dm_configure
-- ----------------------------
INSERT INTO `dm_configure` VALUES ('2', '雷达', 'wangwei', '杨宇', 'http://10.10.1.121:9002/manage/src/gallery/formPreview/index.html?id=306', 'http://10.10.174.207:5086/iov/rest/dataprocessing/dataprocessing/start/18', '2018-12-24 20:35:27');
INSERT INTO `dm_configure` VALUES ('3', '飞天螳螂', 'test', '杨宇', 'http://10.10.1.121:9002/manage/src/gallery/formPreview/index.html?id=306', 'http://10.10.174.207:5086/iov/rest/dataprocessing/dataprocessing/start/18', '2018-12-24 20:51:01');
INSERT INTO `dm_configure` VALUES ('4', '隐形战机', 'hah', '杨宇', 'http://10.10.1.121:9002/manage/src/gallery/formPreview/index.html?id=306', 'http://10.10.174.207:5086/iov/rest/dataprocessing/dataprocessing/start/18', '2018-12-24 21:07:28');
INSERT INTO `dm_configure` VALUES ('7', 'GPS', '123', '王伟', 'http://10.10.1.121:9002/manage/src/gallery/formPreview/index.html?id=306', 'http://10.10.174.207:5086/iov/rest/dataprocessing/dataprocessing/start/18', '2018-12-28 14:18:49');

-- ----------------------------
-- Table structure for dm_upload_classify
-- ----------------------------
DROP TABLE IF EXISTS `dm_upload_classify`;
CREATE TABLE `dm_upload_classify` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL COMMENT '父级菜单id',
  `level` int(11) DEFAULT NULL COMMENT '菜单级别',
  `name` varchar(50) NOT NULL COMMENT '菜单显示名称',
  `description` varchar(255) DEFAULT NULL COMMENT '菜单描述',
  `item_order` int(11) DEFAULT NULL COMMENT '菜单项顺序',
  `icon` blob COMMENT '菜单项图标',
  `status` tinyint(255) DEFAULT NULL COMMENT '发布状态--0：未发布；1：已发布（发布之后面板本身无法编辑，取消发布需该资源无对外授权）',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dm_upload_classify
-- ----------------------------
INSERT INTO `dm_upload_classify` VALUES ('1', '0', '1', '课题1', '我是课题', null, null, null, '2018-12-24 21:55:25');
INSERT INTO `dm_upload_classify` VALUES ('2', '0', '1', '课题2', '我是2课题', '2', null, null, '2018-12-24 21:55:27');
INSERT INTO `dm_upload_classify` VALUES ('12', '1', null, '2018-12-04', null, null, null, null, '2018-12-25 09:29:30');
INSERT INTO `dm_upload_classify` VALUES ('15', '12', null, '试飞科目', null, null, null, null, '2018-12-25 11:27:24');
INSERT INTO `dm_upload_classify` VALUES ('16', '2', null, '2018-12-27', null, null, null, null, '2018-12-26 20:38:30');
INSERT INTO `dm_upload_classify` VALUES ('17', '16', null, '军体拳', null, null, null, null, '2018-12-26 20:38:53');
INSERT INTO `dm_upload_classify` VALUES ('18', '16', null, '111', null, null, null, null, '2018-12-28 14:16:39');
INSERT INTO `dm_upload_classify` VALUES ('19', '0', null, '课题3', null, null, null, null, '2018-12-28 14:19:32');
INSERT INTO `dm_upload_classify` VALUES ('20', '19', null, '2018-12-29', null, null, null, null, '2018-12-28 14:19:52');
INSERT INTO `dm_upload_classify` VALUES ('21', '20', null, '222', null, null, null, null, '2018-12-28 14:20:06');
INSERT INTO `dm_upload_classify` VALUES ('127', '0', null, 'kt1', null, null, null, null, '2019-02-22 09:11:15');
INSERT INTO `dm_upload_classify` VALUES ('128', '127', null, '2019-02-22', null, null, null, null, '2019-02-22 09:11:15');
INSERT INTO `dm_upload_classify` VALUES ('129', '128', null, 'km1', null, null, null, null, '2019-02-22 09:11:16');
INSERT INTO `dm_upload_classify` VALUES ('136', '0', null, '11', null, null, null, null, '2019-02-22 18:32:14');
INSERT INTO `dm_upload_classify` VALUES ('137', '136', null, '2019-02-22', null, null, null, null, '2019-02-22 18:32:14');
INSERT INTO `dm_upload_classify` VALUES ('138', '137', null, '11', null, null, null, null, '2019-02-22 18:32:15');
INSERT INTO `dm_upload_classify` VALUES ('139', '0', null, 'kt2', null, null, null, null, '2019-02-25 14:35:39');
INSERT INTO `dm_upload_classify` VALUES ('140', '139', null, '2019-02-25', null, null, null, null, '2019-02-25 14:35:41');
INSERT INTO `dm_upload_classify` VALUES ('141', '140', null, 'km2', null, null, null, null, '2019-02-25 14:35:41');
INSERT INTO `dm_upload_classify` VALUES ('143', '0', null, 'kt6', null, null, null, null, '2019-02-26 16:19:48');
INSERT INTO `dm_upload_classify` VALUES ('144', '143', null, '2019-02-26', null, null, null, null, '2019-02-26 16:19:49');
INSERT INTO `dm_upload_classify` VALUES ('145', '144', null, 'km6', null, null, null, null, '2019-02-26 16:19:49');
INSERT INTO `dm_upload_classify` VALUES ('146', '20', null, '333', null, null, null, null, '2019-02-28 08:36:20');
INSERT INTO `dm_upload_classify` VALUES ('156', '0', null, 'we', null, null, null, null, '2019-02-28 12:01:11');
INSERT INTO `dm_upload_classify` VALUES ('157', '156', null, '2019-02-28', null, null, null, null, '2019-02-28 12:01:11');
INSERT INTO `dm_upload_classify` VALUES ('158', '157', null, 're', null, null, null, null, '2019-02-28 12:01:11');

-- ----------------------------
-- Table structure for dm_upload_file_item
-- ----------------------------
DROP TABLE IF EXISTS `dm_upload_file_item`;
CREATE TABLE `dm_upload_file_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `upload_classify_id_1` int(11) NOT NULL,
  `upload_classify_id_2` int(11) NOT NULL,
  `upload_classify_id` int(11) NOT NULL COMMENT '左侧3级树Id',
  `data_type_id` int(11) NOT NULL COMMENT '数据项id',
  `data_type_name` varchar(50) NOT NULL COMMENT '数据项名称',
  `file_name` varchar(50) NOT NULL COMMENT '文件名称',
  `file_path` varchar(50) DEFAULT NULL COMMENT '文件实际存储地址',
  `file_size` varchar(50) DEFAULT NULL COMMENT '文件大小',
  `importer` varchar(50) DEFAULT NULL COMMENT '文件导入人',
  `import_time` varchar(50) DEFAULT NULL COMMENT '导入时间',
  `batch_number` varchar(255) DEFAULT '0',
  `analytic_progress` varchar(50) DEFAULT NULL COMMENT '解析进度',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dm_upload_file_item
-- ----------------------------
INSERT INTO `dm_upload_file_item` VALUES ('23', '1', '12', '15', '4', '隐形战机', 'Tulips.jpg', 'E://tmp/hah/', '0.59Mb', '杨宇', '2018-12-26 22:02:31', '0', '正在解析', '2018-12-26 22:02:34');
INSERT INTO `dm_upload_file_item` VALUES ('24', '1', '12', '15', '4', '隐形战机', 'dim_day2.csv', 'E://tmp/hah/', '0.09Mb', '王伟', '2018-12-27 10:19:43', '0', '正在解析', '2018-12-27 10:19:46');
INSERT INTO `dm_upload_file_item` VALUES ('25', '2', '16', '17', '2', '雷达', '雷达王.txt', 'E://tmp/wangwei/', '0.00Mb', '王伟', '2018-12-27 14:04:07', '0', null, '2018-12-27 14:04:10');
INSERT INTO `dm_upload_file_item` VALUES ('26', '2', '16', '17', '2', '雷达', '2雷达.txt', 'E://tmp/wangwei/', '0.00Mb', '王伟', '2018-12-27 14:04:07', '0', null, '2018-12-27 14:04:10');
INSERT INTO `dm_upload_file_item` VALUES ('27', '1', '12', '15', '4', '隐形战机', 'Desert.jpg', 'E://tmp/hah/', '0.81Mb', '杨宇', '2018-12-27 16:58:09', '0', '正在解析', '2018-12-27 16:58:12');
INSERT INTO `dm_upload_file_item` VALUES ('28', '1', '12', '15', '4', '隐形战机', 'Lighthouse.jpg', 'E://tmp/hah/', '0.54Mb', '杨宇', '2018-12-27 23:27:54', null, '正在解析', '2018-12-27 23:27:57');
INSERT INTO `dm_upload_file_item` VALUES ('29', '2', '16', '17', '4', '隐形战机', 'zkui.tar.gz', 'E://tmp/hah/', '8.75Mb', '王伟', '2018-12-28 12:46:16', '20181228124643578', '正在解析', '2018-12-28 12:46:19');
INSERT INTO `dm_upload_file_item` VALUES ('30', '2', '16', '18', '4', '隐形战机', 'README.txt', 'E://tmp/hah/', '0.00Mb', '王伟', '2018-12-28 14:17:10', null, '未开始', '2018-12-28 14:17:12');
INSERT INTO `dm_upload_file_item` VALUES ('31', '2', '16', '18', '2', '雷达', 'README.txt', 'E://tmp/wangwei/', '0.00Mb', '王伟', '2018-12-28 14:17:10', null, '未开始', '2018-12-28 14:17:12');
INSERT INTO `dm_upload_file_item` VALUES ('32', '2', '16', '18', '4', '隐形战机', 'NOTICE.txt', 'E://tmp/hah/', '0.01Mb', '王伟', '2018-12-28 14:17:10', null, '未开始', '2018-12-28 14:17:12');
INSERT INTO `dm_upload_file_item` VALUES ('33', '2', '16', '18', '3', '飞天螳螂', 'LICENSE.txt', 'E://tmp/test/', '0.08Mb', '王伟', '2018-12-28 14:17:10', null, '未开始', '2018-12-28 14:17:12');
INSERT INTO `dm_upload_file_item` VALUES ('34', '2', '16', '18', '4', '隐形战机', 'LICENSE.txt', 'E://tmp/hah/', '0.08Mb', '王伟', '2018-12-28 14:17:10', null, '未开始', '2018-12-28 14:17:12');
INSERT INTO `dm_upload_file_item` VALUES ('35', '19', '20', '21', '7', 'GPS', 'test.svg', 'E://tmp/123/', '0.00Mb', '王伟', '2018-12-28 14:20:43', '20181228142804335', '正在解析', '2018-12-28 14:20:46');
INSERT INTO `dm_upload_file_item` VALUES ('37', '19', '20', '21', '7', 'GPS', 'nginx-1.14.0.tar.gz', 'E://tmp/123/', '0.97Mb', '王伟', '2018-12-28 14:20:43', null, '正在解析', '2018-12-28 14:20:46');
INSERT INTO `dm_upload_file_item` VALUES ('38', '19', '20', '21', '7', 'GPS', '20190110.txt', 'E://tmp/123/', '0.00Mb', '杨宇', '2019-01-10 14:09:57', null, '未开始', '2019-01-10 14:09:57');
INSERT INTO `dm_upload_file_item` VALUES ('39', '19', '20', '21', '7', 'GPS', '20190110-1.txt', 'E://tmp/123/', '0.00Mb', '杨宇', '2019-01-10 14:26:27', null, '正在解析', '2019-01-10 14:26:27');
INSERT INTO `dm_upload_file_item` VALUES ('41', '136', '137', '138', '7', 'GPS', '新建文本文档.txt', 'E://tmp/123/', '0.00Mb', '杨宇', '2019-02-22 18:32:16', null, '未开始', '2019-02-22 18:32:16');
INSERT INTO `dm_upload_file_item` VALUES ('42', '139', '140', '141', '7', 'GPS', '新建文本文档 (2).txt', 'E://tmp/123/', '0.00Mb', '杨宇', '2019-02-25 14:35:45', null, '未开始', '2019-02-25 14:35:45');
INSERT INTO `dm_upload_file_item` VALUES ('43', '143', '144', '145', '7', 'GPS', 'test20190226.txt', 'E://tmp/123/', '0.00Mb', '杨宇', '2019-02-26 16:19:52', null, '未开始', '2019-02-26 16:19:52');
INSERT INTO `dm_upload_file_item` VALUES ('44', '19', '20', '146', '7', 'GPS', 'BugReport.txt', 'E://tmp/123/', '0.00Mb', '杨宇', '2019-02-28 11:41:13', null, '未开始', '2019-02-28 11:41:13');
INSERT INTO `dm_upload_file_item` VALUES ('45', '19', '20', '146', '4', '隐形战机', '20190228-1.txt', 'E://tmp/hah/', '0.00Mb', '杨宇', '2019-02-28 11:45:28', null, '未开始', '2019-02-28 11:45:28');
INSERT INTO `dm_upload_file_item` VALUES ('49', '156', '157', '158', '7', 'GPS', '20190228-4.txt', 'E://tmp/123/', '0.00Mb', '杨宇', '2019-02-28 12:01:13', null, '未开始', '2019-02-28 12:01:13');
