/*
Navicat MySQL Data Transfer

Source Server         : 192.168.112.17
Source Server Version : 50723
Source Host           : 192.168.112.17:3306
Source Database       : mutest

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2020-05-31 10:26:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info` (
  `id` varchar(32) NOT NULL COMMENT '文件md5',
  `content_type` varchar(128) NOT NULL,
  `size` int(11) NOT NULL,
  `path` varchar(255) NOT NULL COMMENT '物理路径',
  `uploader` varchar(255) DEFAULT NULL,
  `url` varchar(1024) NOT NULL,
  `type` int(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of file_info
-- ----------------------------
INSERT INTO `file_info` VALUES ('6fe16b15c88ba6ecc82b236b10edea5e', 'image/jpeg', '1777610', 'd:/files/2020/04/27/6fe16b15c88ba6ecc82b236b10edea5e.jpg', '穆国政', '/2020/04/27/6fe16b15c88ba6ecc82b236b10edea5e.jpg', '1', '测试', '2020-04-27 17:29:52', '2020-05-11 20:31:33');
INSERT INTO `file_info` VALUES ('975b3c4a9995068921a3670300f556e8', 'image/jpeg', '728080', 'd:/files/2020/04/27/975b3c4a9995068921a3670300f556e8.jpg', '管理员', '/2020/04/27/975b3c4a9995068921a3670300f556e8.jpg', '1', '测试2', '2020-04-27 17:00:30', '2020-05-23 09:52:30');
INSERT INTO `file_info` VALUES ('9b0025241a4ff1285686c8e6e8e4cd93', 'image/jpeg', '679712', 'd:/files/2020/05/21/9b0025241a4ff1285686c8e6e8e4cd93.jpg', '管理员', '/2020/05/21/9b0025241a4ff1285686c8e6e8e4cd93.jpg', '1', '管理员的头像', '2020-05-21 18:25:02', '2020-05-21 18:25:02');
INSERT INTO `file_info` VALUES ('aba45a72bc9b32e5387dda53de1b4790', 'image/jpeg', '2643397', 'd:/files/2020/05/02/aba45a72bc9b32e5387dda53de1b4790.jpg', '管理员', '/2020/05/02/aba45a72bc9b32e5387dda53de1b4790.jpg', '1', '管理员的头像', '2020-05-02 11:39:57', '2020-05-02 11:39:57');

-- ----------------------------
-- Table structure for interface_case
-- ----------------------------
DROP TABLE IF EXISTS `interface_case`;
CREATE TABLE `interface_case` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `interface_id` int(11) DEFAULT NULL COMMENT '接口id',
  `delay` int(10) DEFAULT NULL COMMENT '用例执行前的延迟时间，单位：ms',
  `header_data` varchar(2040) DEFAULT NULL COMMENT '请求头',
  `body_data` varchar(2040) DEFAULT NULL COMMENT '请求体',
  `creator` varchar(255) DEFAULT NULL COMMENT '创建人',
  `description` varchar(2040) DEFAULT NULL COMMENT '说明',
  `result_demo` varchar(8160) DEFAULT NULL COMMENT '响应结果示例',
  `assertion` varchar(510) DEFAULT NULL COMMENT '断言信息',
  `shell_status` varchar(8) DEFAULT NULL,
  `assertion_shell` varchar(4080) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `correlation` varchar(1020) DEFAULT NULL COMMENT '关联处理',
  `case_type` varchar(255) DEFAULT NULL COMMENT '用例类型：正常、异常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of interface_case
-- ----------------------------

-- ----------------------------
-- Table structure for interface_case_result
-- ----------------------------
DROP TABLE IF EXISTS `interface_case_result`;
CREATE TABLE `interface_case_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `execute_id` varchar(22) DEFAULT NULL,
  `case_id` int(22) DEFAULT NULL,
  `interface_name` varchar(255) DEFAULT NULL,
  `case_request` varchar(255) DEFAULT NULL,
  `header_data` varchar(255) DEFAULT NULL,
  `body_data` varchar(255) DEFAULT NULL,
  `case_response` varchar(8000) DEFAULT NULL,
  `case_assertion` varchar(255) DEFAULT NULL,
  `assertion_detail` varchar(4000) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of interface_case_result
-- ----------------------------

-- ----------------------------
-- Table structure for interface_group
-- ----------------------------
DROP TABLE IF EXISTS `interface_group`;
CREATE TABLE `interface_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(255) DEFAULT NULL,
  `group_leader` varchar(22) DEFAULT NULL COMMENT '组负责人',
  `description` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_name` (`group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of interface_group
-- ----------------------------

-- ----------------------------
-- Table structure for interface_group_project
-- ----------------------------
DROP TABLE IF EXISTS `interface_group_project`;
CREATE TABLE `interface_group_project` (
  `group_id` int(11) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of interface_group_project
-- ----------------------------

-- ----------------------------
-- Table structure for interface_group_user
-- ----------------------------
DROP TABLE IF EXISTS `interface_group_user`;
CREATE TABLE `interface_group_user` (
  `group_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  UNIQUE KEY `group_id` (`group_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of interface_group_user
-- ----------------------------

-- ----------------------------
-- Table structure for interface_list
-- ----------------------------
DROP TABLE IF EXISTS `interface_list`;
CREATE TABLE `interface_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module_id` int(11) DEFAULT NULL COMMENT '模块ID',
  `interface_name` varchar(255) DEFAULT NULL COMMENT '接口',
  `path` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `param_type` varchar(255) DEFAULT NULL COMMENT '参数类型，包括form格式、json格式',
  `creator` varchar(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of interface_list
-- ----------------------------

-- ----------------------------
-- Table structure for interface_module
-- ----------------------------
DROP TABLE IF EXISTS `interface_module`;
CREATE TABLE `interface_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` varchar(255) DEFAULT NULL,
  `module_name` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of interface_module
-- ----------------------------

-- ----------------------------
-- Table structure for interface_project
-- ----------------------------
DROP TABLE IF EXISTS `interface_project`;
CREATE TABLE `interface_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL COMMENT '创建者',
  `creator` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of interface_project
-- ----------------------------

-- ----------------------------
-- Table structure for interface_set_result
-- ----------------------------
DROP TABLE IF EXISTS `interface_set_result`;
CREATE TABLE `interface_set_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `test_set_id` int(11) DEFAULT NULL COMMENT '场景id',
  `execute_id` varchar(22) DEFAULT NULL,
  `case_count` varchar(255) DEFAULT NULL COMMENT '场景包含的用例数',
  `failed_count` varchar(255) DEFAULT NULL COMMENT '失败用例数',
  `failed_list` varchar(255) DEFAULT NULL COMMENT '失败用例列表',
  `executor` varchar(255) DEFAULT NULL COMMENT '测试场景执行人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of interface_set_result
-- ----------------------------

-- ----------------------------
-- Table structure for interface_test_set
-- ----------------------------
DROP TABLE IF EXISTS `interface_test_set`;
CREATE TABLE `interface_test_set` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) DEFAULT NULL,
  `case_id_queue` varchar(255) DEFAULT NULL COMMENT '用例队列，格式：1001-1004',
  `case_name_queue` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of interface_test_set
-- ----------------------------

-- ----------------------------
-- Table structure for sys_logs
-- ----------------------------
DROP TABLE IF EXISTS `sys_logs`;
CREATE TABLE `sys_logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `module` varchar(50) DEFAULT NULL COMMENT '模块名',
  `flag` tinyint(4) NOT NULL DEFAULT '1' COMMENT '成功失败',
  `remark` text COMMENT '备注',
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `userId` (`userId`) USING BTREE,
  KEY `createTime` (`createTime`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_logs
-- ----------------------------

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `css` varchar(30) DEFAULT NULL,
  `href` varchar(1000) DEFAULT NULL,
  `type` tinyint(1) NOT NULL,
  `permission` varchar(50) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', '0', '接口自动化', '', '', '1', '', '1');
INSERT INTO `sys_permission` VALUES ('2', '1', '项目管理', 'fa-sitemap', '', '1', '', '12');
INSERT INTO `sys_permission` VALUES ('3', '1', '接口列表', 'fa-puzzle-piece', 'pages/interface/interfaceList.html', '1', '', '13');
INSERT INTO `sys_permission` VALUES ('4', '1', '用例管理', 'fa-tasks', 'pages/interface/caseList.html', '1', '', '14');
INSERT INTO `sys_permission` VALUES ('5', '1', '测试集合', 'fa-chain', 'pages/interface/testSetList.html', '1', '', '15');
INSERT INTO `sys_permission` VALUES ('6', '1', '测试结果', 'fa-bar-chart-o', 'pages/interface/testSetResult.html', '1', '', '16');
INSERT INTO `sys_permission` VALUES ('7', '0', 'UI自动化', '', '', '1', '', '2');
INSERT INTO `sys_permission` VALUES ('8', '7', '字典管理', 'fa-pencil-square-o', '', '1', '', '21');
INSERT INTO `sys_permission` VALUES ('9', '7', '元素管理', 'fa-gears', '', '1', '', '22');
INSERT INTO `sys_permission` VALUES ('10', '7', '用例管理', 'fa-cog', '', '1', '', '23');
INSERT INTO `sys_permission` VALUES ('11', '7', '测试集合', '', '', '1', '', '24');
INSERT INTO `sys_permission` VALUES ('12', '7', '测试结果', '', '', '1', '', '25');
INSERT INTO `sys_permission` VALUES ('13', '0', '系统管理', '', '', '1', '', '3');
INSERT INTO `sys_permission` VALUES ('14', '13', '角色管理', 'fa-user-secret', 'pages/role/roleList.html', '1', '', '32');
INSERT INTO `sys_permission` VALUES ('15', '14', '查询', '', '', '2', 'sys:role:query', '100');
INSERT INTO `sys_permission` VALUES ('16', '14', '新增', '', '', '2', 'sys:role:add', '100');
INSERT INTO `sys_permission` VALUES ('17', '14', '删除', '', '', '2', 'sys:role:del', '100');
INSERT INTO `sys_permission` VALUES ('18', '13', '人员管理', 'fa-users', 'pages/user/userList.html', '1', '', '33');
INSERT INTO `sys_permission` VALUES ('19', '18', '查询', '', '', '2', 'sys:user:query', '100');
INSERT INTO `sys_permission` VALUES ('20', '18', '新增', '', '', '2', 'sys:user:add', '100');
INSERT INTO `sys_permission` VALUES ('21', '18', '删除', '', '', '2', 'sys:user:del', '100');
INSERT INTO `sys_permission` VALUES ('22', '13', '文件管理', 'fa-folder-open', 'pages/file/fileList.html', '1', '', '33');
INSERT INTO `sys_permission` VALUES ('23', '22', '查询', '', '', '2', 'sys:file:query', '100');
INSERT INTO `sys_permission` VALUES ('24', '22', '新增', '', '', '2', 'sys:file:add', '100');
INSERT INTO `sys_permission` VALUES ('25', '22', '删除', '', '', '2', 'sys:file:del', '100');
INSERT INTO `sys_permission` VALUES ('26', '13', '菜单管理', 'fa-cog', 'pages/menu/menuList.html', '1', '', '34');
INSERT INTO `sys_permission` VALUES ('27', '26', '查询', '', '', '2', 'sys:menu:query', '100');
INSERT INTO `sys_permission` VALUES ('28', '26', '新增', '', '', '2', 'sys:menu:add', '100');
INSERT INTO `sys_permission` VALUES ('29', '26', '删除', '', '', '2', 'sys:menu:del', '100');
INSERT INTO `sys_permission` VALUES ('30', '2', '项目列表', 'fa-sitemap', 'pages/interface/projectList.html', '1', '', '100');
INSERT INTO `sys_permission` VALUES ('31', '2', '模块列表', '', 'pages/interface/moduleList.html', '1', '', '101');
INSERT INTO `sys_permission` VALUES ('32', '1', '权限管理', 'fa-wrench', 'pages/interface/group.html', '1', '', '11');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `nickname` varchar(20) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE,
  UNIQUE KEY `nickname` (`nickname`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'ADMIN', '管理员', '管理员', '2017-05-01 13:25:39', '2020-05-18 18:20:50');
INSERT INTO `sys_role` VALUES ('2', 'USER', '用户', '用户', '2017-08-01 21:47:31', '2020-05-18 11:41:38');
INSERT INTO `sys_role` VALUES ('3', 'CHIEF', '总监', '总监', '2020-05-14 16:02:06', '2020-05-14 16:02:09');
INSERT INTO `sys_role` VALUES ('4', 'MANAGER', '部门经理', '经理', '2020-05-14 16:02:36', '2020-05-14 16:02:38');
INSERT INTO `sys_role` VALUES ('5', 'GROUPLEADER', '组长', '组长', '2020-05-14 16:04:07', '2020-05-14 16:04:10');
INSERT INTO `sys_role` VALUES ('6', 'MEMBER', '成员', '成员', '2020-05-14 16:04:44', '2020-05-14 16:04:46');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `roleId` int(11) NOT NULL,
  `permissionId` int(11) NOT NULL,
  PRIMARY KEY (`roleId`,`permissionId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1', '1');
INSERT INTO `sys_role_permission` VALUES ('1', '2');
INSERT INTO `sys_role_permission` VALUES ('1', '3');
INSERT INTO `sys_role_permission` VALUES ('1', '4');
INSERT INTO `sys_role_permission` VALUES ('1', '5');
INSERT INTO `sys_role_permission` VALUES ('1', '6');
INSERT INTO `sys_role_permission` VALUES ('1', '7');
INSERT INTO `sys_role_permission` VALUES ('1', '8');
INSERT INTO `sys_role_permission` VALUES ('1', '9');
INSERT INTO `sys_role_permission` VALUES ('1', '10');
INSERT INTO `sys_role_permission` VALUES ('1', '11');
INSERT INTO `sys_role_permission` VALUES ('1', '12');
INSERT INTO `sys_role_permission` VALUES ('1', '13');
INSERT INTO `sys_role_permission` VALUES ('1', '14');
INSERT INTO `sys_role_permission` VALUES ('1', '15');
INSERT INTO `sys_role_permission` VALUES ('1', '16');
INSERT INTO `sys_role_permission` VALUES ('1', '18');
INSERT INTO `sys_role_permission` VALUES ('1', '19');
INSERT INTO `sys_role_permission` VALUES ('1', '20');
INSERT INTO `sys_role_permission` VALUES ('1', '21');
INSERT INTO `sys_role_permission` VALUES ('1', '22');
INSERT INTO `sys_role_permission` VALUES ('1', '23');
INSERT INTO `sys_role_permission` VALUES ('1', '24');
INSERT INTO `sys_role_permission` VALUES ('1', '25');
INSERT INTO `sys_role_permission` VALUES ('1', '26');
INSERT INTO `sys_role_permission` VALUES ('1', '27');
INSERT INTO `sys_role_permission` VALUES ('1', '28');
INSERT INTO `sys_role_permission` VALUES ('1', '29');
INSERT INTO `sys_role_permission` VALUES ('1', '30');
INSERT INTO `sys_role_permission` VALUES ('1', '31');
INSERT INTO `sys_role_permission` VALUES ('1', '32');
INSERT INTO `sys_role_permission` VALUES ('2', '1');
INSERT INTO `sys_role_permission` VALUES ('2', '2');
INSERT INTO `sys_role_permission` VALUES ('2', '3');
INSERT INTO `sys_role_permission` VALUES ('2', '4');
INSERT INTO `sys_role_permission` VALUES ('2', '5');
INSERT INTO `sys_role_permission` VALUES ('2', '6');
INSERT INTO `sys_role_permission` VALUES ('2', '7');
INSERT INTO `sys_role_permission` VALUES ('2', '8');
INSERT INTO `sys_role_permission` VALUES ('2', '9');
INSERT INTO `sys_role_permission` VALUES ('2', '10');
INSERT INTO `sys_role_permission` VALUES ('2', '11');
INSERT INTO `sys_role_permission` VALUES ('2', '12');
INSERT INTO `sys_role_permission` VALUES ('2', '13');
INSERT INTO `sys_role_permission` VALUES ('2', '14');
INSERT INTO `sys_role_permission` VALUES ('2', '15');
INSERT INTO `sys_role_permission` VALUES ('2', '16');
INSERT INTO `sys_role_permission` VALUES ('2', '17');
INSERT INTO `sys_role_permission` VALUES ('2', '18');
INSERT INTO `sys_role_permission` VALUES ('2', '19');
INSERT INTO `sys_role_permission` VALUES ('2', '20');
INSERT INTO `sys_role_permission` VALUES ('2', '21');
INSERT INTO `sys_role_permission` VALUES ('2', '22');
INSERT INTO `sys_role_permission` VALUES ('2', '23');
INSERT INTO `sys_role_permission` VALUES ('2', '24');
INSERT INTO `sys_role_permission` VALUES ('2', '25');
INSERT INTO `sys_role_permission` VALUES ('2', '26');
INSERT INTO `sys_role_permission` VALUES ('2', '27');
INSERT INTO `sys_role_permission` VALUES ('2', '28');
INSERT INTO `sys_role_permission` VALUES ('2', '29');
INSERT INTO `sys_role_permission` VALUES ('2', '30');
INSERT INTO `sys_role_permission` VALUES ('2', '31');

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `userId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`userId`,`roleId`) USING BTREE,
  UNIQUE KEY `userId` (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES ('1', '1');
INSERT INTO `sys_role_user` VALUES ('2', '4');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(60) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `headImgUrl` varchar(255) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `telephone` varchar(30) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `sex` tinyint(1) DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `description` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '$2a$10$MkTZgOu0mSAeIIEhosvMpuWL0OolYBiGvzkpOMkZL7wWtY.HNe/pi', '管理员', '平台', '管理员', '/2020/05/21/9b0025241a4ff1285686c8e6e8e4cd93.jpg', '15510174257', '', '', '1998-06-27', '1', '1', '管理员', '2020-05-21 16:03:59', '2020-05-23 11:20:51');

-- ----------------------------
-- Table structure for t_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(16) NOT NULL,
  `k` varchar(16) NOT NULL,
  `val` varchar(64) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `type` (`type`,`k`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_dict
-- ----------------------------
INSERT INTO `t_dict` VALUES ('1', 'sex', '0', '女', '2017-11-17 09:58:24', '2017-11-18 14:21:05');
INSERT INTO `t_dict` VALUES ('2', 'sex', '1', '男', '2017-11-17 10:03:46', '2017-11-17 10:03:46');
INSERT INTO `t_dict` VALUES ('3', 'userStatus', '0', '无效', '2017-11-17 16:26:06', '2017-11-17 16:26:09');
INSERT INTO `t_dict` VALUES ('4', 'userStatus', '1', '正常', '2017-11-17 16:26:06', '2017-11-17 16:26:09');
INSERT INTO `t_dict` VALUES ('5', 'userStatus', '2', '锁定', '2017-11-17 16:26:06', '2017-11-17 16:26:09');
INSERT INTO `t_dict` VALUES ('6', 'noticeStatus', '0', '草稿', '2017-11-17 16:26:06', '2017-11-17 16:26:09');
INSERT INTO `t_dict` VALUES ('7', 'noticeStatus', '1', '发布', '2017-11-17 16:26:06', '2017-11-17 16:26:09');
INSERT INTO `t_dict` VALUES ('8', 'isRead', '0', '未读', '2017-11-17 16:26:06', '2017-11-17 16:26:09');
INSERT INTO `t_dict` VALUES ('9', 'isRead', '1', '已读', '2017-11-17 16:26:06', '2017-11-17 16:26:09');
INSERT INTO `t_dict` VALUES ('10', 'department', '0', '平台', '2020-05-21 16:14:24', '2020-05-21 16:14:28');
INSERT INTO `t_dict` VALUES ('11', 'department', '1', '测试部', '2020-05-21 16:15:09', '2020-05-21 16:15:12');
INSERT INTO `t_dict` VALUES ('12', 'department', '2', '产品部', '2020-05-21 16:15:53', '2020-05-21 16:15:55');
INSERT INTO `t_dict` VALUES ('13', 'department', '3', '后端开发部', '2020-05-21 16:16:12', '2020-05-21 16:16:15');
INSERT INTO `t_dict` VALUES ('14', 'department', '4', 'web开发部', '2020-05-21 16:16:41', '2020-05-21 16:16:44');
INSERT INTO `t_dict` VALUES ('15', 'department', '5', 'IOS开发部', '2020-05-21 16:16:58', '2020-05-21 16:17:01');
INSERT INTO `t_dict` VALUES ('16', 'department', '6', '安卓开发部', '2020-05-21 16:17:15', '2020-05-21 16:17:17');
INSERT INTO `t_dict` VALUES ('17', 'department', '7', 'UI设计部', '2020-05-21 16:17:36', '2020-05-21 16:17:39');

-- ----------------------------
-- Table structure for t_job
-- ----------------------------
DROP TABLE IF EXISTS `t_job`;
CREATE TABLE `t_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jobName` varchar(64) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `cron` varchar(64) NOT NULL,
  `springBeanName` varchar(64) NOT NULL COMMENT 'springBean名',
  `methodName` varchar(64) NOT NULL COMMENT '方法名',
  `isSysJob` tinyint(1) NOT NULL COMMENT '是否是系统job',
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `jobName` (`jobName`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_job
-- ----------------------------

-- ----------------------------
-- Table structure for t_mail
-- ----------------------------
DROP TABLE IF EXISTS `t_mail`;
CREATE TABLE `t_mail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '发送人',
  `subject` varchar(255) NOT NULL COMMENT '标题',
  `content` longtext NOT NULL COMMENT '正文',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_mail
-- ----------------------------

-- ----------------------------
-- Table structure for t_mail_to
-- ----------------------------
DROP TABLE IF EXISTS `t_mail_to`;
CREATE TABLE `t_mail_to` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mailId` int(11) NOT NULL,
  `toUser` varchar(128) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1成功，0失败',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_mail_to
-- ----------------------------

-- ----------------------------
-- Table structure for t_token
-- ----------------------------
DROP TABLE IF EXISTS `t_token`;
CREATE TABLE `t_token` (
  `id` varchar(36) NOT NULL COMMENT 'token',
  `val` text NOT NULL COMMENT 'LoginUser的json串',
  `expireTime` datetime NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_token
-- ----------------------------

-- ----------------------------
-- Procedure structure for insert_account
-- ----------------------------
DROP PROCEDURE IF EXISTS `insert_account`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `insert_account`()
BEGIN 
	DECLARE y INT DEFAULT 1001;
	WHILE (y<=2000) DO
		INSERT INTO `user_base_info`(`id`,`uid`,`parent_id`,`tenant_id`,`user_type`,`is_default`,`email`,`nick_name`,`login_pwd`,`trade_pwd`,`pwd_flag`,`nation`,`language`,`mobile`,`google_key`,`status_flag`,`config_flag`,`user_level`,`channel`,`inviter_id`,`transfer_target_id`,`remark`,`status`,`register_time`,`update_time`)
		VALUES(y,CONCAT(y,'1'),'100043','100005','1','0','','58coin@58coin.com','E4EAE961ED9D4A41ED1A9CF96729D81D','123456','1','211','0',CONCAT('30000000',y),'','8','2','0','2','100043','0','','0',now(),now());
	SET y=y+1; 
	END WHILE;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for insert_charge
-- ----------------------------
DROP PROCEDURE IF EXISTS `insert_charge`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `insert_charge`()
BEGIN 
	DECLARE y INT DEFAULT 1001;
	WHILE (y<=2000) DO
		INSERT INTO `tb_account_deposit_coin`(`user_id`,`currency_id`,`address`,`amount`,`height`,`txid`,`status`)VALUES(y,'8','a','999999999','0', RAND(),'0');
	SET y=y+1; 
	END WHILE;  
END
;;
DELIMITER ;
