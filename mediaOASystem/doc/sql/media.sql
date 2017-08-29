DROP TABLE IF EXISTS `tb_attachments`;
CREATE TABLE `tb_attachments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `attach_table` varchar(50) NOT NULL COMMENT '附件所属表(不同表的附件根据表进行筛选)',
  `save_file_name` varchar(255) NOT NULL COMMENT '附件保存名',
  `original_file_name` varchar(255) DEFAULT NULL COMMENT '附件原上传名',
  `ext_name` varchar(255) DEFAULT NULL COMMENT '后缀名',
  `mime_type` varchar(255) DEFAULT NULL COMMENT '文件类型',
  `save_path` varchar(255) NOT NULL COMMENT '保存路径',
  `file_size` float NOT NULL COMMENT '文件大小',
  `user_id` bigint(20) NOT NULL COMMENT '上传附件用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8 COMMENT='上传附件表';

DROP TABLE IF EXISTS `tb_department`;
CREATE TABLE `tb_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `department_name` varchar(255) NOT NULL COMMENT '部门/科组名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='部门科组表';

DROP TABLE IF EXISTS `tb_department_awards`;
CREATE TABLE `tb_department_awards` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `awards_time` datetime DEFAULT NULL COMMENT '参评时间',
  `awards_name` varchar(50) NOT NULL COMMENT '奖项名称',
  `awards_works` varchar(50) NOT NULL COMMENT '参评作品',
  `awards_leader_user_id` bigint(20) NOT NULL COMMENT '参评项目负责人ID',
  `awards_department_ids` text COMMENT '参加科组(部门/科组ID，多个部门/科组用逗号分隔)',
  `awards_user_ids` text COMMENT '参评人员(用户ID，多个用户用逗号分隔)',
  `awards_result` varchar(50) DEFAULT NULL COMMENT '评奖结果',
  `awards_bonus` decimal(12,2) DEFAULT NULL COMMENT '奖金（元）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '修改者ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='部门评奖表';

DROP TABLE IF EXISTS `tb_dispatch_unit`;
CREATE TABLE `tb_dispatch_unit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `dispatch_unit_name` varchar(50) NOT NULL COMMENT '发文单位名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='设备表';

DROP TABLE IF EXISTS `tb_duty`;
CREATE TABLE `tb_duty` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `duty_name` varchar(255) NOT NULL COMMENT '岗位名称',
  `department_id` bigint(20) NOT NULL COMMENT '部门/科组ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='岗位表';

DROP TABLE IF EXISTS `tb_duty_res`;
CREATE TABLE `tb_duty_res` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `duty_id` bigint(20) NOT NULL COMMENT '岗位ID',
  `res_id` bigint(20) NOT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='岗位资源表';

DROP TABLE IF EXISTS `tb_equipment`;
CREATE TABLE `tb_equipment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `equipment_name` varchar(50) NOT NULL COMMENT '设备名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='设备表';

DROP TABLE IF EXISTS `tb_event_apply_form`;
CREATE TABLE `tb_event_apply_form` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `project_name` varchar(50) NOT NULL COMMENT '项目名称',
  `project_code` varchar(50) DEFAULT NULL COMMENT '编号',
  `apply_date` datetime DEFAULT NULL COMMENT '申请日期',
  `budget_amount` decimal(12,2) DEFAULT NULL COMMENT '预算金额',
  `apply_user_ids` text COMMENT '事项申请人(多个用户用逗号分隔)',
  `apply_department_ids` text COMMENT '申请科组(多个科组用逗号分隔)',
  `leader_user_id` bigint(20) NOT NULL COMMENT '负责人ID',
  `agent_user_name` varchar(50) DEFAULT NULL COMMENT '经办人姓名',
  `cost_company_id` bigint(20) NOT NULL COMMENT '费用支出公司(1.中视前卫、2.国际视通、3.成都索贝)',
  `cost_contract` varchar(50) DEFAULT NULL COMMENT '费用所属合同',
  `cost_contract_amount` decimal(12,2) DEFAULT NULL COMMENT '所属合同金额',
  `attach_ids` text COMMENT '上传附件(多个附件用逗号分隔)',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '修改者ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='事项申请表';

DROP TABLE IF EXISTS `tb_fixed_assets`;
CREATE TABLE `tb_fixed_assets` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `equipment_lifetime_number` varchar(50) NOT NULL COMMENT '设备终生码',
  `equipment_id` bigint(20) NOT NULL COMMENT '设备名称ID',
  `equipment_model` varchar(50) NOT NULL COMMENT '型号',
  `serial_number` varchar(50) NOT NULL COMMENT '序列号',
  `unit_price` decimal(12,2) NOT NULL COMMENT '单价/台',
  `manufacturer_name` varchar(50) NOT NULL COMMENT '生产厂家',
  `user_id` bigint(20) NOT NULL COMMENT '使用人',
  `user_card_number` varchar(50) DEFAULT NULL COMMENT '工作证号',
  `user_company` varchar(50) DEFAULT NULL COMMENT '使用部门（只有一个新媒体新闻编辑部）',
  `equipment_place` varchar(50) DEFAULT NULL COMMENT '设备位置',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='固定资产表';

DROP TABLE IF EXISTS `tb_holiday_sms_remind`;
CREATE TABLE `tb_holiday_sms_remind` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `remind_name` varchar(50) NOT NULL COMMENT '提醒名称',
  `remind_type` tinyint(2) NOT NULL COMMENT '提醒类型: 0.节日短信 1.生日短信',
  `remind_content` varchar(255) NOT NULL COMMENT '提醒内容',
  `remind_time` datetime DEFAULT NULL COMMENT '提醒时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='节日短信问候表';

DROP TABLE IF EXISTS `tb_inside_income_dispatches`;
CREATE TABLE `tb_inside_income_dispatches` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `dispatch_unit_id` bigint(20) NOT NULL COMMENT '发文单位',
  `year_and_document_number` varchar(50) DEFAULT NULL COMMENT '年份和文号',
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `print_and_dispatch_time` datetime NOT NULL COMMENT '印发日期',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `attach_ids` text COMMENT '上传附件(多个附件用逗号分隔)',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建者ID',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '修改者ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='台内收文表';

DROP TABLE IF EXISTS `tb_outside_income_dispatches`;
CREATE TABLE `tb_outside_income_dispatches` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `dispatch_unit_name` varchar(50) NOT NULL COMMENT '发文单位名称',
  `year_and_document_number` varchar(50) DEFAULT NULL COMMENT '年份和文号',
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `income_dispatches_time` datetime NOT NULL COMMENT '收文日期',
  `print_and_dispatch_time` datetime NOT NULL COMMENT '印发日期',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `attach_ids` text COMMENT '上传附件(多个附件用逗号分隔)',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建者ID',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '修改者ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='台外收文表';

DROP TABLE IF EXISTS `tb_res`;
CREATE TABLE `tb_res` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `res_name` varchar(255) DEFAULT NULL COMMENT '资源名称',
  `res_url` varchar(255) DEFAULT NULL COMMENT '资源url',
  `rg_id` bigint(20) DEFAULT NULL COMMENT '资源组ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='资源表';

DROP TABLE IF EXISTS `tb_res_group`;
CREATE TABLE `tb_res_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `res_group_name` varchar(255) DEFAULT NULL COMMENT '资源组名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='资源组表';

DROP TABLE IF EXISTS `tb_schedule_sms_remind`;
CREATE TABLE `tb_schedule_sms_remind` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `user_id` bigint(20) NOT NULL COMMENT '被提醒人',
  `user_mobile` varchar(11) NOT NULL COMMENT '被提醒手机号',
  `remind_name` varchar(50) NOT NULL COMMENT '提醒名称',
  `remind_content` varchar(255) NOT NULL COMMENT '提醒内容',
  `remind_time` text NOT NULL COMMENT '提醒时间（多个，用逗号隔开）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='日程安排提醒表';

DROP TABLE IF EXISTS `tb_sms_remind_logs`;
CREATE TABLE `tb_sms_remind_logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `user_id` bigint(20) NOT NULL COMMENT '被提醒人',
  `log_name` varchar(50) NOT NULL COMMENT '日志名称',
  `log_info` text COMMENT '日志信息',
  `log_result` tinyint(2) DEFAULT NULL COMMENT '0：失败，1：成功',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信提醒日志表';

DROP TABLE IF EXISTS `tb_timer_logs`;
CREATE TABLE `tb_timer_logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `timer_log_name` varchar(50) NOT NULL COMMENT '日志名称',
  `beginTime` datetime NOT NULL COMMENT '创建时间',
  `endTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=505 DEFAULT CHARSET=utf8 COMMENT='定时任务日志表';

DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `mobile` varchar(11) NOT NULL COMMENT '手机号',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `true_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `card_number` varchar(50) DEFAULT NULL COMMENT '工作证号',
  `birthday` datetime NOT NULL COMMENT '生日',
  `tel_number` varchar(20) DEFAULT NULL COMMENT '座机',
  `user_relationship` tinyint(2) DEFAULT NULL COMMENT '员工关系：0.台聘, 1.企聘, 2.视通公司聘, 3外籍',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `department_id` bigint(20) DEFAULT NULL COMMENT '部门/科组ID',
  `duty_id` bigint(20) DEFAULT NULL COMMENT '岗位ID',
  `tempate_style` varchar(50) DEFAULT 'default' COMMENT '样式模板',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='用户表';

DROP TABLE IF EXISTS `tb_user_res`;
CREATE TABLE `tb_user_res` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `res_id` bigint(20) NOT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=548 DEFAULT CHARSET=utf8 COMMENT='用户资源表';




INSERT INTO `tb_department` VALUES ('1', '0', now(), '部门领导');
INSERT INTO `tb_department` VALUES ('2', '0', now(), '统筹组');
INSERT INTO `tb_department` VALUES ('3', '0', now(), '设计组');
INSERT INTO `tb_department` VALUES ('4', '0', now(), '视频组');
INSERT INTO `tb_department` VALUES ('5', '0', now(), '客户端组');
INSERT INTO `tb_department` VALUES ('6', '0', now(), '社交组');
INSERT INTO `tb_department` VALUES ('7', '0', now(), '策划组');
INSERT INTO `tb_department` VALUES ('8', '0', now(), '直播组');
INSERT INTO `tb_department` VALUES ('9', '0', now(), '产品研发组');
INSERT INTO `tb_department` VALUES ('10', '0', now(), '外专');

INSERT INTO `tb_duty` VALUES ('1', '0', now(), '主编', '2');
INSERT INTO `tb_duty` VALUES ('2', '0', now(), '办公室', '2');
INSERT INTO `tb_duty` VALUES ('3', '0', now(), '运营组', '2');
INSERT INTO `tb_duty` VALUES ('4', '0', now(), '制片人', '3');
INSERT INTO `tb_duty` VALUES ('5', '0', now(), '负责人', '4');
INSERT INTO `tb_duty` VALUES ('6', '0', now(), '网络原创', '4');
INSERT INTO `tb_duty` VALUES ('7', '0', now(), '新闻视频', '4');
INSERT INTO `tb_duty` VALUES ('8', '0', now(), '短视频', '4');
INSERT INTO `tb_duty` VALUES ('9', '0', now(), '责编', '5');
INSERT INTO `tb_duty` VALUES ('10', '0', now(), 'APP组', '5');
INSERT INTO `tb_duty` VALUES ('11', '0', now(), '突发新闻组', '5');
INSERT INTO `tb_duty` VALUES ('12', '0', now(), '主编', '7');
INSERT INTO `tb_duty` VALUES ('13', '0', now(), '责编', '8');
INSERT INTO `tb_duty` VALUES ('14', '0', now(), 'Head', '10');

INSERT INTO `tb_dispatch_unit` VALUES ('1', '0', now(), '台发人字');
INSERT INTO `tb_dispatch_unit` VALUES ('2', '0', now(), '台发技字');
INSERT INTO `tb_dispatch_unit` VALUES ('3', '0', now(), '台发纪字');
INSERT INTO `tb_dispatch_unit` VALUES ('4', '0', now(), '电视办字');
INSERT INTO `tb_dispatch_unit` VALUES ('5', '0', now(), '台发宣字');
INSERT INTO `tb_dispatch_unit` VALUES ('6', '0', now(), '分党组发');
INSERT INTO `tb_dispatch_unit` VALUES ('7', '0', now(), '台保密办');
INSERT INTO `tb_dispatch_unit` VALUES ('8', '0', now(), '台发办字');
INSERT INTO `tb_dispatch_unit` VALUES ('9', '0', now(), '台发财字');
INSERT INTO `tb_dispatch_unit` VALUES ('10', '0', now(), '电视财经');
INSERT INTO `tb_dispatch_unit` VALUES ('11', '0', now(), '电视人字');

INSERT INTO `tb_equipment` VALUES ('1', '0', now(), '台式一体机');
INSERT INTO `tb_equipment` VALUES ('2', '0', now(), '台式电脑');
INSERT INTO `tb_equipment` VALUES ('3', '0', now(), '打印机');
INSERT INTO `tb_equipment` VALUES ('4', '0', now(), '传真机');
INSERT INTO `tb_equipment` VALUES ('5', '0', now(), '液晶电视');
INSERT INTO `tb_equipment` VALUES ('6', '0', now(), '扫描仪');
INSERT INTO `tb_equipment` VALUES ('7', '0', now(), '多功能一体机');
INSERT INTO `tb_equipment` VALUES ('8', '0', now(), '保密机');
INSERT INTO `tb_equipment` VALUES ('9', '0', now(), '保密打印机');
INSERT INTO `tb_equipment` VALUES ('10', '0', now(), '碎纸机');
INSERT INTO `tb_equipment` VALUES ('11', '0', now(), '柜子');


INSERT INTO `tb_res_group` VALUES ('1', '0', now(), '日程安排提醒');
INSERT INTO `tb_res_group` VALUES ('2', '0', now(), '问候短信');
INSERT INTO `tb_res_group` VALUES ('3', '0', now(), '短信提醒记录');
INSERT INTO `tb_res_group` VALUES ('4', '0', now(), '固定资产管理');
INSERT INTO `tb_res_group` VALUES ('5', '0', now(), '台内收文管理');
INSERT INTO `tb_res_group` VALUES ('6', '0', now(), '台外收文管理');
INSERT INTO `tb_res_group` VALUES ('7', '0', now(), '事项申请表');
INSERT INTO `tb_res_group` VALUES ('8', '0', now(), '部门评奖');
INSERT INTO `tb_res_group` VALUES ('9', '0', now(), '人员档案');
INSERT INTO `tb_res_group` VALUES ('10', '0', now(), '组织机构');
INSERT INTO `tb_res_group` VALUES ('11', '0', now(), '发文单位管理');
INSERT INTO `tb_res_group` VALUES ('12', '0', now(), '设备管理');

INSERT INTO `tb_res` VALUES ('1', '0', now(), '日程安排提醒列表', '/admin/sms/schedule_sms_remind_list.htm*', '1');
INSERT INTO `tb_res` VALUES ('2', '0', now(), '查看', '/admin/sms/schedule_sms_remind_view.htm*', '1');
INSERT INTO `tb_res` VALUES ('3', '0', now(), '添加', '/admin/sms/schedule_sms_remind_add.htm*', '1');
INSERT INTO `tb_res` VALUES ('4', '0', now(), '启用/关闭', '/admin/sms/ssr_ajax_edit_save.htm*', '1');
INSERT INTO `tb_res` VALUES ('5', '0', now(), '编辑', '/admin/sms/schedule_sms_remind_edit.htm*', '1');
INSERT INTO `tb_res` VALUES ('6', '0', now(), '删除', '/admin/sms/schedule_sms_remind_delete.htm*', '1');

INSERT INTO `tb_res` VALUES ('7', '0', now(), '节日短信问候列表', '/admin/sms/holiday_sms_remind_list.htm*', '2');
INSERT INTO `tb_res` VALUES ('8', '0', now(), '查看', '/admin/sms/holiday_sms_remind_view.htm*', '2');
INSERT INTO `tb_res` VALUES ('9', '0', now(), '添加', '/admin/sms/holiday_sms_remind_add.htm*', '2');
INSERT INTO `tb_res` VALUES ('10', '0', now(), '启用/关闭', '/admin/sms/hsr_ajax_edit_save.htm*', '2');
INSERT INTO `tb_res` VALUES ('11', '0', now(), '编辑', '/admin/sms/holiday_sms_remind_edit.htm*', '2');
INSERT INTO `tb_res` VALUES ('12', '0', now(), '删除', '/admin/sms/holiday_sms_remind_delete.htm*', '2');

INSERT INTO `tb_res` VALUES ('13', '0', now(), '短信提醒记录列表', '/admin/sms/sms_remind_logs_list.htm*', '3');

INSERT INTO `tb_res` VALUES ('14', '0', now(), '固定资产列表', '/admin/office/fixed_assets_list.htm*', '4');
INSERT INTO `tb_res` VALUES ('15', '0', now(), '查看', '/admin/office/fixed_assets_view.htm*', '4');
INSERT INTO `tb_res` VALUES ('16', '0', now(), '添加', '/admin/office/fixed_assets_add.htm*', '4');
INSERT INTO `tb_res` VALUES ('17', '0', now(), '编辑', '/admin/office/fixed_assets_edit.htm*', '4');
INSERT INTO `tb_res` VALUES ('18', '0', now(), '删除', '/admin/office/fixed_assets_delete.htm*', '4');
INSERT INTO `tb_res` VALUES ('19', '0', now(), '导出Excel', '/admin/office/fixed_assets_exportExcel.htm*', '4');

INSERT INTO `tb_res` VALUES ('20', '0', now(), '台内收文列表', '/admin/office/inside_income_dispatches_list.htm*', '5');
INSERT INTO `tb_res` VALUES ('21', '0', now(), '查看', '/admin/office/inside_income_dispatches_view.htm*', '5');
INSERT INTO `tb_res` VALUES ('22', '0', now(), '添加', '/admin/office/inside_income_dispatches_add.htm*', '5');
INSERT INTO `tb_res` VALUES ('23', '0', now(), '编辑', '/admin/office/inside_income_dispatches_edit.htm*', '5');
INSERT INTO `tb_res` VALUES ('24', '0', now(), '删除', '/admin/office/inside_income_dispatches_delete.htm*', '5');
INSERT INTO `tb_res` VALUES ('25', '0', now(), '导出Excel', '/admin/office/inside_income_dispatches_exportExcel.htm*', '5');

INSERT INTO `tb_res` VALUES ('26', '0', now(), '台外收文列表', '/admin/office/outside_income_dispatches_list.htm*', '6');
INSERT INTO `tb_res` VALUES ('27', '0', now(), '查看', '/admin/office/outside_income_dispatches_view.htm*', '6');
INSERT INTO `tb_res` VALUES ('28', '0', now(), '添加', '/admin/office/outside_income_dispatches_add.htm*', '6');
INSERT INTO `tb_res` VALUES ('29', '0', now(), '编辑', '/admin/office/outside_income_dispatches_edit.htm*', '6');
INSERT INTO `tb_res` VALUES ('30', '0', now(), '删除', '/admin/office/outside_income_dispatches_delete.htm*', '6');
INSERT INTO `tb_res` VALUES ('31', '0', now(), '导出Excel', '/admin/office/outside_income_dispatches_exportExcel.htm*', '6');

INSERT INTO `tb_res` VALUES ('32', '0', now(), '事项申请表列表', '/admin/office/event_apply_form_list.htm*', '7');
INSERT INTO `tb_res` VALUES ('33', '0', now(), '查看', '/admin/office/event_apply_form_view.htm*', '7');
INSERT INTO `tb_res` VALUES ('34', '0', now(), '添加', '/admin/office/event_apply_form_add.htm*', '7');
INSERT INTO `tb_res` VALUES ('35', '0', now(), '编辑', '/admin/office/event_apply_form_edit.htm*', '7');
INSERT INTO `tb_res` VALUES ('36', '0', now(), '删除', '/admin/office/event_apply_form_delete.htm*', '7');
INSERT INTO `tb_res` VALUES ('37', '0', now(), '导出Excel', '/admin/office/event_apply_form_exportExcel.htm*', '7');

INSERT INTO `tb_res` VALUES ('38', '0', now(), '部门评奖列表', '/admin/office/department_awards_list.htm*', '8');
INSERT INTO `tb_res` VALUES ('39', '0', now(), '查看', '/admin/office/department_awards_view.htm*', '8');
INSERT INTO `tb_res` VALUES ('40', '0', now(), '添加', '/admin/office/department_awards_add.htm*', '8');
INSERT INTO `tb_res` VALUES ('41', '0', now(), '编辑', '/admin/office/department_awards_edit.htm*', '8');
INSERT INTO `tb_res` VALUES ('42', '0', now(), '删除', '/admin/office/department_awards_delete.htm*', '8');
INSERT INTO `tb_res` VALUES ('43', '0', now(), '导出Excel', '/admin/office/department_awards_exportExcel.htm*', '8');

INSERT INTO `tb_res` VALUES ('44', '0', now(), '员工列表', '/admin/user/user_list.htm*', '9');
INSERT INTO `tb_res` VALUES ('45', '0', now(), '查看', '/admin/user/user_view.htm*', '9');
INSERT INTO `tb_res` VALUES ('46', '0', now(), '添加', '/admin/user/user_add.htm*', '9');
INSERT INTO `tb_res` VALUES ('47', '0', now(), '编辑', '/admin/user/user_edit.htm*', '9');
INSERT INTO `tb_res` VALUES ('48', '0', now(), '删除', '/admin/user/user_delete.htm*', '9');
INSERT INTO `tb_res` VALUES ('49', '0', now(), '重置密码', '/admin/user/admin_reset_user_password.htm*', '9');

INSERT INTO `tb_res` VALUES ('50', '0', now(), '部门/科组列表', '/admin/user/department_list.htm*', '10');
INSERT INTO `tb_res` VALUES ('51', '0', now(), '部门/科组添加', '/admin/user/department_add.htm*', '10');
INSERT INTO `tb_res` VALUES ('52', '0', now(), '部门/科组编辑', '/admin/user/department_ajax_edit_save.htm*', '10');
INSERT INTO `tb_res` VALUES ('53', '0', now(), '部门/科组删除', '/admin/user/department_delete.htm*', '10');
INSERT INTO `tb_res` VALUES ('54', '0', now(), '岗位添加', '/admin/user/duty_add.htm*', '10');
INSERT INTO `tb_res` VALUES ('55', '0', now(), '岗位编辑', '/admin/user/duty_ajax_edit_save.htm*', '10');
INSERT INTO `tb_res` VALUES ('56', '0', now(), '岗位删除', '/admin/user/duty_delete.htm*', '10');

INSERT INTO `tb_res` VALUES ('57', '0', now(), '发文单位列表', '/admin/syscfg/dispatch_unit_list.htm*', '11');
INSERT INTO `tb_res` VALUES ('58', '0', now(), '添加', '/admin/syscfg/dispatch_unit_add.htm*', '11');
INSERT INTO `tb_res` VALUES ('59', '0', now(), '编辑', '/admin/syscfg/dispatch_unit_edit.htm*', '11');
INSERT INTO `tb_res` VALUES ('60', '0', now(), '删除', '/admin/syscfg/dispatch_unit_delete.htm*', '11');

INSERT INTO `tb_res` VALUES ('61', '0', now(), '设备管理列表', '/admin/syscfg/equipment_list.htm*', '12');
INSERT INTO `tb_res` VALUES ('62', '0', now(), '添加', '/admin/syscfg/equipment_add.htm*', '12');
INSERT INTO `tb_res` VALUES ('63', '0', now(), '编辑', '/admin/syscfg/equipment_edit.htm*', '12');
INSERT INTO `tb_res` VALUES ('64', '0', now(), '删除', '/admin/syscfg/equipment_delete.htm*', '12');


INSERT INTO `tb_user` VALUES ('1', '0', '2017-01-01 00:00:00', '13300001111', '21218cca77804d2ba1922c33e0151105', 'admin', '管理员', null, '1234', '2017-05-12 00:00:00', '000000', '0', '', '1', null, null);
INSERT INTO `tb_user` VALUES ('5', '0', '2017-01-01 00:00:00', '18701681970', '888888', '', '徐  健', '', '001335', '2017-01-01 00:00:00', '', '0', '', '2', '1', '');
INSERT INTO `tb_user` VALUES ('6', '0', '2017-01-01 00:00:00', '13801164146', '888888', '', '裴  建', '', '001345', '2017-01-01 00:00:00', '', '0', '', '2', '1', '');
INSERT INTO `tb_user` VALUES ('7', '0', '2017-01-01 00:00:00', '18511062191', '888888', '', '林东威', '', '002364', '2017-01-01 00:00:00', '', '0', '', '2', '1', '');
INSERT INTO `tb_user` VALUES ('8', '0', '2017-01-01 00:00:00', '13011007760', '888888', '', '秦小虎', '', '002566', '2017-01-01 00:00:00', '', '0', '', '2', '1', '');
INSERT INTO `tb_user` VALUES ('9', '0', '2017-01-01 00:00:00', '13681279753', '888888', '', '王德伟', '', '007253', '2017-01-01 00:00:00', '', '0', '', '2', '1', '');
INSERT INTO `tb_user` VALUES ('10', '0', '2017-01-01 00:00:00', '13811309904', '888888', '', '吕  琮', '', '214179', '2017-01-01 00:00:00', '85061519', '1', '', '2', '2', '');
INSERT INTO `tb_user` VALUES ('11', '0', '2017-01-01 00:00:00', '13436355018', '888888', '', '申鸿建', '', '311848', '2017-01-01 00:00:00', '85061558', '2', '', '2', '2', '');
INSERT INTO `tb_user` VALUES ('12', '0', '2017-01-01 00:00:00', '13810251113', '888888', '', '赵冉暄', '', '324457', '2017-01-01 00:00:00', '85061555', '2', '', '2', '2', '');
INSERT INTO `tb_user` VALUES ('13', '0', '2017-01-01 00:00:00', '18588236250', '888888', '', '王玉强', '', '326876', '2017-01-01 00:00:00', '85061410', '2', '', '2', '2', '');
INSERT INTO `tb_user` VALUES ('14', '0', '2017-01-01 00:00:00', '18611275937', '888888', '', '刘  薇', '', '327011', '2017-01-01 00:00:00', '85061541', '2', '', '2', '2', '');
INSERT INTO `tb_user` VALUES ('15', '0', '2017-01-01 00:00:00', '18010136410', '888888', '', '董匡远', '', '327016', '2017-01-01 00:00:00', '85061416', '2', '', '2', '2', '');
INSERT INTO `tb_user` VALUES ('16', '0', '2017-01-01 00:00:00', '13436756610', '888888', '', '高  堃', '', '327086', '2017-01-01 00:00:00', '85061417', '2', '', '2', '2', '');
INSERT INTO `tb_user` VALUES ('17', '0', '2017-01-01 00:00:00', '18911311123', '888888', '', '孙  涛', '', '', '2017-01-01 00:00:00', '85061411', '2', '', '2', '2', '');
INSERT INTO `tb_user` VALUES ('18', '0', '2017-01-01 00:00:00', '18601356575', '888888', '', '刘育淇', '', '304876', '2017-01-01 00:00:00', '85061543', '1', '', '2', '3', '');
INSERT INTO `tb_user` VALUES ('19', '0', '2017-01-01 00:00:00', '15010377535', '888888', '', '周  婧', '', '323434', '2017-01-01 00:00:00', '85061545', '2', '', '2', '3', '');
INSERT INTO `tb_user` VALUES ('20', '0', '2017-01-01 00:00:00', '13520921600', '888888', '', '赵媛婷', '', '325066', '2017-01-01 00:00:00', '85061546', '2', '', '2', '3', '');
INSERT INTO `tb_user` VALUES ('21', '0', '2017-01-01 00:00:00', '15711145905', '888888', '', '陈  霄', '', '311850', '2017-01-01 00:00:00', '85061542', '2', '', '2', '3', '');
INSERT INTO `tb_user` VALUES ('22', '0', '2017-01-01 00:00:00', '13718656453', '888888', '', '王  腾', '', '324456', '2017-01-01 00:00:00', '85061543', '2', '', '2', '3', '');
INSERT INTO `tb_user` VALUES ('23', '0', '2017-01-01 00:00:00', '13501330290', '888888', '', '王彦怡', '', '327308', '2017-01-01 00:00:00', '85061547', '2', '', '2', '3', '');
INSERT INTO `tb_user` VALUES ('24', '0', '2017-01-01 00:00:00', '18800110697', '888888', '', '李  晖', '', '', '2017-01-01 00:00:00', '', '2', '', '2', '3', '');
INSERT INTO `tb_user` VALUES ('25', '0', '2017-01-01 00:00:00', '13511057222', '888888', '', '吴海霞', '', '007330', '2017-01-01 00:00:00', '85061520', '0', '', '3', '4', '');
INSERT INTO `tb_user` VALUES ('26', '0', '2017-01-01 00:00:00', '13910839104', '888888', '', '张学成', '', '106310', '2017-01-01 00:00:00', '', '1', '', '3', null, '');
INSERT INTO `tb_user` VALUES ('27', '0', '2017-01-01 00:00:00', '18601140903', '888888', '', '马晓楠', '', '309563', '2017-01-01 00:00:00', '', '1', '', '3', null, '');
INSERT INTO `tb_user` VALUES ('28', '0', '2017-01-01 00:00:00', '13260202829', '888888', '', '范晨潇', '', '325065', '2017-01-01 00:00:00', '', '2', '', '3', null, '');
INSERT INTO `tb_user` VALUES ('29', '0', '2017-01-01 00:00:00', '15241170986', '888888', '', '高红梅', '', '326878', '2017-01-01 00:00:00', '', '2', '', '3', null, '');
INSERT INTO `tb_user` VALUES ('30', '0', '2017-01-01 00:00:00', '13522827957', '888888', '', '尹亚婷', '', '326691', '2017-01-01 00:00:00', '', '2', '', '3', null, '');
INSERT INTO `tb_user` VALUES ('31', '0', '2017-01-01 00:00:00', '15210256495', '888888', '', '贾洁琼', '', '326695', '2017-01-01 00:00:00', '', '2', '', '3', null, '');
INSERT INTO `tb_user` VALUES ('32', '0', '2017-01-01 00:00:00', '13826293321', '888888', '', '付  磊', '', '327022', '2017-01-01 00:00:00', '', '2', '', '3', null, '');
INSERT INTO `tb_user` VALUES ('33', '0', '2017-01-01 00:00:00', '13911700541', '888888', '', '冉博强', '', '327012', '2017-01-01 00:00:00', '', '2', '', '3', null, '');
INSERT INTO `tb_user` VALUES ('34', '0', '2017-01-01 00:00:00', '17316241603', '888888', '', '杜晨欣', '', '', '2017-01-01 00:00:00', '', '2', '', '3', null, '');
INSERT INTO `tb_user` VALUES ('35', '0', '2017-01-01 00:00:00', '13681225822', '888888', '', '于  鹏', '', '', '2017-01-01 00:00:00', '', '2', '', '3', null, '');
INSERT INTO `tb_user` VALUES ('36', '0', '2017-01-01 00:00:00', '13601294204', '888888', '', '张  韬', '', '', '2017-01-01 00:00:00', '', '2', '', '3', null, '');
INSERT INTO `tb_user` VALUES ('37', '0', '2017-01-01 00:00:00', '13811678732', '888888', '', '温雅茹', '', '311279', '2017-01-01 00:00:00', '85061502', '1', '', '4', '5', '');
INSERT INTO `tb_user` VALUES ('38', '0', '2017-01-01 00:00:00', '15210319634', '888888', '', '徐纪业', '', '320474', '2017-01-01 00:00:00', '', '2', '', '4', '6', '');
INSERT INTO `tb_user` VALUES ('39', '0', '2017-01-01 00:00:00', '15257103928', '888888', '', '高幸子', '', '324073', '2017-01-01 00:00:00', '', '2', '', '4', '6', '');
INSERT INTO `tb_user` VALUES ('40', '0', '2017-01-01 00:00:00', '15010767406', '888888', '', '张万宝', '', '326690', '2017-01-01 00:00:00', '', '2', '', '4', '6', '');
INSERT INTO `tb_user` VALUES ('41', '0', '2017-01-01 00:00:00', '18601032495', '888888', '', '张大禹', '', '326692', '2017-01-01 00:00:00', '', '2', '', '4', '6', '');
INSERT INTO `tb_user` VALUES ('42', '0', '2017-01-01 00:00:00', '15311559686', '888888', '', '齐建强', '', '326694', '2017-01-01 00:00:00', '', '2', '', '4', '6', '');
INSERT INTO `tb_user` VALUES ('43', '0', '2017-01-01 00:00:00', '13522248990', '888888', '', '张钰晨', '', '', '2017-01-01 00:00:00', '', '2', '', '4', '6', '');
INSERT INTO `tb_user` VALUES ('44', '0', '2017-01-01 00:00:00', '18518579342', '888888', '', '李天夫', '', '324739', '2017-01-01 00:00:00', '', '1', '', '4', '7', '');
INSERT INTO `tb_user` VALUES ('45', '0', '2017-01-01 00:00:00', '17701357727', '888888', '', '冀  欣', '', '326686', '2017-01-01 00:00:00', '', '2', '', '4', '7', '');
INSERT INTO `tb_user` VALUES ('46', '0', '2017-01-01 00:00:00', '18301204258', '888888', '', '王昕欣', '', '326500', '2017-01-01 00:00:00', '', '2', '', '4', '7', '');
INSERT INTO `tb_user` VALUES ('47', '0', '2017-01-01 00:00:00', '13466369176', '888888', '', '郑晨蕾', '', '326503', '2017-01-01 00:00:00', '', '2', '', '4', '7', '');
INSERT INTO `tb_user` VALUES ('48', '0', '2017-01-01 00:00:00', '18810996413', '888888', '', '李  翔', '', '327015', '2017-01-01 00:00:00', '', '2', '', '4', '7', '');
INSERT INTO `tb_user` VALUES ('49', '0', '2017-01-01 00:00:00', '13908250026', '888888', '', '徐  涵', '', '327009', '2017-01-01 00:00:00', '', '2', '', '4', '7', '');
INSERT INTO `tb_user` VALUES ('50', '0', '2017-01-01 00:00:00', '18519530131', '888888', '', '朱丹倪', '', '327018', '2017-01-01 00:00:00', '', '2', '', '4', '7', '');
INSERT INTO `tb_user` VALUES ('51', '0', '2017-01-01 00:00:00', '18627876406', '888888', '', '王凯琳', '', '327008', '2017-01-01 00:00:00', '', '2', '', '4', '7', '');
INSERT INTO `tb_user` VALUES ('52', '0', '2017-01-01 00:00:00', '18229479330', '888888', '', '周  锐', '', '', '2017-01-01 00:00:00', '', '2', '', '4', '7', '');
INSERT INTO `tb_user` VALUES ('53', '0', '2017-01-01 00:00:00', '18611700878', '888888', '', '杨欣萌', '', '', '2017-01-01 00:00:00', '', '2', '', '4', '7', '');
INSERT INTO `tb_user` VALUES ('54', '0', '2017-01-01 00:00:00', '13521595481', '888888', '', '陈  石', '', '', '2017-01-01 00:00:00', '', '2', '', '4', '7', '');
INSERT INTO `tb_user` VALUES ('55', '0', '2017-01-01 00:00:00', '13811318534', '888888', '', '杨  婧', '', '', '2017-01-01 00:00:00', '', '2', '', '4', '7', '');
INSERT INTO `tb_user` VALUES ('56', '0', '2017-01-01 00:00:00', '18211061218', '888888', '', '葛  宁', '', '', '2017-01-01 00:00:00', '', '2', '', '4', '7', '');
INSERT INTO `tb_user` VALUES ('57', '0', '2017-01-01 00:00:00', '15007191240', '888888', ' ', '黄怡畅', ' ', ' ', '2017-01-01 00:00:00', ' ', '2', ' ', '4', '7', '');
INSERT INTO `tb_user` VALUES ('58', '0', '2017-01-01 00:00:00', '18500146634', '888888', '', '陈绚蓝', '', '', '2017-01-01 00:00:00', '', '2', '', '4', '7', '');
INSERT INTO `tb_user` VALUES ('59', '0', '2017-01-01 00:00:00', '18511702289', '888888', '', '周金玺', '', '320341', '2017-01-01 00:00:00', '', '2', '', '4', '8', '');
INSERT INTO `tb_user` VALUES ('60', '0', '2017-01-01 00:00:00', '13701303680', '888888', '', '陈恺然', '', '323128', '2017-01-01 00:00:00', '', '2', '', '4', '8', '');
INSERT INTO `tb_user` VALUES ('61', '0', '2017-01-01 00:00:00', '18612036117', '888888', '', '米  雪', '', '325485', '2017-01-01 00:00:00', '', '2', '', '4', '8', '');
INSERT INTO `tb_user` VALUES ('62', '0', '2017-01-01 00:00:00', '18511261226', '888888', '', '周忆秋', '', '323433', '2017-01-01 00:00:00', '', '2', '', '4', '8', '');
INSERT INTO `tb_user` VALUES ('63', '0', '2017-01-01 00:00:00', '13811089747', '888888', '', '闫  俐', '', '303587', '2017-01-01 00:00:00', '', '1', '', '4', '8', '');
INSERT INTO `tb_user` VALUES ('64', '0', '2017-01-01 00:00:00', '18311192029', '888888', '', '徐晓彤', '', '', '2017-01-01 00:00:00', '', '2', '', '4', '8', '');
INSERT INTO `tb_user` VALUES ('65', '0', '2017-01-01 00:00:00', '15501098656', '888888', '', '李宁宁', '', '', '2017-01-01 00:00:00', '', '2', '', '4', '8', '');
INSERT INTO `tb_user` VALUES ('66', '0', '2017-01-01 00:00:00', '18811720809', '888888', '', '葛  凯', '', '', '2017-01-01 00:00:00', '', '2', '', '4', '8', '');
INSERT INTO `tb_user` VALUES ('67', '0', '2017-01-01 00:00:00', '18813071619', '888888', '', '王玉莲', '', '', '2017-01-01 00:00:00', '', '2', '', '4', '8', '');
INSERT INTO `tb_user` VALUES ('68', '0', '2017-01-01 00:00:00', '13716298006', '888888', '', '王  政', '', '308506', '2017-01-01 00:00:00', '85061710', '1', '', '5', '9', '');
INSERT INTO `tb_user` VALUES ('69', '0', '2017-01-01 00:00:00', '13810380056', '888888', '', '司  楠', '', '308507', '2017-01-01 00:00:00', '', '1', '', '5', '9', '');
INSERT INTO `tb_user` VALUES ('70', '0', '2017-01-01 00:00:00', '18612223196', '888888', '', '张小鹤', '', '323393', '2017-01-01 00:00:00', '', '2', '', '5', '9', '');
INSERT INTO `tb_user` VALUES ('71', '0', '2017-01-01 00:00:00', '18660402889', '888888', '', '党  正', '', '318667', '2017-01-01 00:00:00', '', '1', '', '5', '9', '');
INSERT INTO `tb_user` VALUES ('72', '0', '2017-01-01 00:00:00', '18210018565', '888888', '', '毕建录', '', '310149', '2017-01-01 00:00:00', '', '1', '', '5', '9', '');
INSERT INTO `tb_user` VALUES ('73', '0', '2017-01-01 00:00:00', '13520927698', '888888', '', '王铭砚', '', '315615', '2017-01-01 00:00:00', '', '1', '', '5', '10', '');
INSERT INTO `tb_user` VALUES ('74', '0', '2017-01-01 00:00:00', '18600761095', '888888', '', '艾  琰', '', '322636', '2017-01-01 00:00:00', '', '1', '', '5', '10', '');
INSERT INTO `tb_user` VALUES ('75', '0', '2017-01-01 00:00:00', '13717828899', '888888', '', '黄天辰', '', '324455', '2017-01-01 00:00:00', '', '2', '', '5', '10', '');
INSERT INTO `tb_user` VALUES ('76', '0', '2017-01-01 00:00:00', '15910656192', '888888', '', '邓俊芳', '', '323653', '2017-01-01 00:00:00', '', '2', '', '5', '10', '');
INSERT INTO `tb_user` VALUES ('77', '0', '2017-01-01 00:00:00', '18515531125', '888888', '', '孟亚萍', '', '326504', '2017-01-01 00:00:00', '', '2', '', '5', '10', '');
INSERT INTO `tb_user` VALUES ('78', '0', '2017-01-01 00:00:00', '13707217888', '888888', '', '姚  念', '', '326501', '2017-01-01 00:00:00', '', '2', '', '5', '10', '');
INSERT INTO `tb_user` VALUES ('79', '0', '2017-01-01 00:00:00', '15801302907', '888888', '', '颜  琼', '', '327019', '2017-01-01 00:00:00', '', '2', '', '5', '10', '');
INSERT INTO `tb_user` VALUES ('80', '0', '2017-01-01 00:00:00', '15911049369', '888888', '', '张睿君', '', '', '2017-01-01 00:00:00', '', '2', '', '5', '10', '');
INSERT INTO `tb_user` VALUES ('81', '0', '2017-01-01 00:00:00', '15010590767', '888888', '', '王  磊', '', '327311', '2017-01-01 00:00:00', '', '2', '', '5', '10', '');
INSERT INTO `tb_user` VALUES ('82', '0', '2017-01-01 00:00:00', '15210505539', '888888', '', '高  云', '', '327303', '2017-01-01 00:00:00', '', '2', '', '5', '10', '');
INSERT INTO `tb_user` VALUES ('83', '0', '2017-01-01 00:00:00', '15862510830', '888888', '', '谢祯琦', '', '327307', '2017-01-01 00:00:00', '', '2', '', '5', '10', '');
INSERT INTO `tb_user` VALUES ('84', '0', '2017-01-01 00:00:00', '13521086647', '888888', '', '王  巍', '', '106190', '2017-01-01 00:00:00', '', '2', '', '5', '10', '');
INSERT INTO `tb_user` VALUES ('85', '0', '2017-01-01 00:00:00', '13552042308', '888888', '', '佟昕欣', '', '', '2017-01-01 00:00:00', '', '2', '', '5', '10', '');
INSERT INTO `tb_user` VALUES ('86', '0', '2017-01-01 00:00:00', '15328185419', '888888', '', '赵  红', '', '', '2017-01-01 00:00:00', '', '2', '', '5', '10', '');
INSERT INTO `tb_user` VALUES ('87', '0', '2017-01-01 00:00:00', '18601017847', '888888', '', '龚  融', '', '', '2017-01-01 00:00:00', '', '2', '', '5', '10', '');
INSERT INTO `tb_user` VALUES ('88', '0', '2017-01-01 00:00:00', '18600511072', '888888', '', '金子雄', '', '308533', '2017-01-01 00:00:00', '', '1', '', '5', '10', '');
INSERT INTO `tb_user` VALUES ('89', '0', '2017-01-01 00:00:00', '13335057063', '888888', '', '巩  喆', '', '327013', '2017-01-01 00:00:00', '', '2', '', '5', '10', '');
INSERT INTO `tb_user` VALUES ('90', '0', '2017-01-01 00:00:00', '18137103630', '888888', '', '何  燕', '', '327305', '2017-01-01 00:00:00', '', '2', '', '5', '10', '');
INSERT INTO `tb_user` VALUES ('91', '0', '2017-01-01 00:00:00', '15101023909', '888888', '', '李  倩', '', '323652', '2017-01-01 00:00:00', '85061710', '2', '', '5', '11', '');
INSERT INTO `tb_user` VALUES ('92', '0', '2017-01-01 00:00:00', '15010786377', '888888', '', '胡绍聪', '', '324074', '2017-01-01 00:00:00', '', '2', '', '5', '11', '');
INSERT INTO `tb_user` VALUES ('93', '0', '2017-01-01 00:00:00', '18757731117', '888888', '', '黄铮铮', '', '326880', '2017-01-01 00:00:00', '', '2', '', '5', '11', '');
INSERT INTO `tb_user` VALUES ('94', '0', '2017-01-01 00:00:00', '13261914793', '888888', '', '王雪靖', '', '326285', '2017-01-01 00:00:00', '', '1', '', '5', '11', '');
INSERT INTO `tb_user` VALUES ('95', '0', '2017-01-01 00:00:00', '13693362505', '888888', '', '黄欣唯', '', '327020', '2017-01-01 00:00:00', '', '2', '', '5', '11', '');
INSERT INTO `tb_user` VALUES ('96', '0', '2017-01-01 00:00:00', '18618292704', '888888', '', '刘  卉', '', '', '2017-01-01 00:00:00', '', '2', '', '5', '11', '');
INSERT INTO `tb_user` VALUES ('97', '0', '2017-01-01 00:00:00', '18205326116', '888888', '', '朱  玫', '', '327310', '2017-01-01 00:00:00', '', '2', '', '5', '11', '');
INSERT INTO `tb_user` VALUES ('98', '0', '2017-01-01 00:00:00', '15711252541', '888888', '', '韩  婕', '', '', '2017-01-01 00:00:00', '', '2', '', '5', '11', '');
INSERT INTO `tb_user` VALUES ('99', '0', '2017-01-01 00:00:00', '15601386866', '888888', '', '胡申秋', '', '', '2017-01-01 00:00:00', '', '2', '', '5', '11', '');
INSERT INTO `tb_user` VALUES ('100', '0', '2017-01-01 00:00:00', '18810186445', '888888', '', '卜  石', '', '', '2017-01-01 00:00:00', '', '2', '', '5', '11', '');
INSERT INTO `tb_user` VALUES ('101', '0', '2017-01-01 00:00:00', '13466791753', '888888', '', '单  元', '', '321948', '2017-01-01 00:00:00', '', '2', '', '6', null, '');
INSERT INTO `tb_user` VALUES ('102', '0', '2017-01-01 00:00:00', '18600012125', '888888', '', '杨  砥', '', '304586', '2017-01-01 00:00:00', '', '2', '', '6', null, '');
INSERT INTO `tb_user` VALUES ('103', '0', '2017-01-01 00:00:00', '15210763139', '888888', '', '曹夏婷', '', '325483', '2017-01-01 00:00:00', '', '2', '', '6', null, '');
INSERT INTO `tb_user` VALUES ('104', '0', '2017-01-01 00:00:00', '15811063208', '888888', '', '孙  骁', '', '325484', '2017-01-01 00:00:00', '', '2', '', '6', null, '');
INSERT INTO `tb_user` VALUES ('105', '0', '2017-01-01 00:00:00', '15201202071', '888888', '', '张  梦', '', '311605', '2017-01-01 00:00:00', '', '2', '', '6', null, '');
INSERT INTO `tb_user` VALUES ('106', '0', '2017-01-01 00:00:00', '13701034066', '888888', '', '傅健舒', '', '321564', '2017-01-01 00:00:00', '', '2', '', '6', null, '');
INSERT INTO `tb_user` VALUES ('107', '0', '2017-01-01 00:00:00', '13699234292', '888888', '', '蔡梦霄', '', '326693', '2017-01-01 00:00:00', '', '2', '', '6', null, '');
INSERT INTO `tb_user` VALUES ('108', '0', '2017-01-01 00:00:00', '18813019312', '888888', '', '刘  晨', '', '327010', '2017-01-01 00:00:00', '', '2', '', '6', null, '');
INSERT INTO `tb_user` VALUES ('109', '0', '2017-01-01 00:00:00', '18515343908', '888888', '', '李叶子', '', '326499', '2017-01-01 00:00:00', '', '2', '', '6', null, '');
INSERT INTO `tb_user` VALUES ('110', '0', '2017-01-01 00:00:00', '18810534406', '888888', '', '李  晶', '', '', '2017-01-01 00:00:00', '', '2', '', '6', null, '');
INSERT INTO `tb_user` VALUES ('111', '0', '2017-01-01 00:00:00', '17740819185', '888888', '', '朱莹旻', '', '', '2017-01-01 00:00:00', '', '2', '', '6', null, '');
INSERT INTO `tb_user` VALUES ('112', '0', '2017-01-01 00:00:00', '18610248849', '888888', '', '王  荃', '', '', '2017-01-01 00:00:00', '', '2', '', '6', null, '');
INSERT INTO `tb_user` VALUES ('113', '0', '2017-01-01 00:00:00', '18618259274', '888888', '', '宋婧宇', '', '', '2017-01-01 00:00:00', '', '2', '', '6', null, '');
INSERT INTO `tb_user` VALUES ('114', '0', '2017-01-01 00:00:00', '13621199675', '888888', '', '陈  姌', '', '327304', '2017-01-01 00:00:00', '', '2', '', '7', '12', '');
INSERT INTO `tb_user` VALUES ('115', '0', '2017-01-01 00:00:00', '18612933072', '888888', '', '张  宇', '', '', '2017-01-01 00:00:00', '', '2', '', '7', null, '');
INSERT INTO `tb_user` VALUES ('116', '0', '2017-01-01 00:00:00', '18310231896', '888888', '', '李  琨', '', '327017', '2017-01-01 00:00:00', '', '2', '', '7', null, '');
INSERT INTO `tb_user` VALUES ('117', '0', '2017-01-01 00:00:00', '13552042308', '888888', '', '佟昕欣', '', '', '2017-01-01 00:00:00', '', '2', '', '7', null, '');
INSERT INTO `tb_user` VALUES ('118', '0', '2017-01-01 00:00:00', '13501118804', '888888', '', '李  响', '', '106181', '2017-01-01 00:00:00', '', '1', '', '8', '13', '');
INSERT INTO `tb_user` VALUES ('119', '0', '2017-01-01 00:00:00', '15811589306', '888888', '', '李向阳', '', '308578', '2017-01-01 00:00:00', '85061556', '1', '', '8', null, '');
INSERT INTO `tb_user` VALUES ('120', '0', '2017-01-01 00:00:00', '18610000100', '888888', '', '李  健', '', '322486', '2017-01-01 00:00:00', '85061511', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('121', '0', '2017-01-01 00:00:00', '13581540539', '888888', '', '彭  芃', '', '326509', '2017-01-01 00:00:00', '85061505', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('122', '0', '2017-01-01 00:00:00', '15010625952', '888888', '', '毕建坤', '', '324660', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('123', '0', '2017-01-01 00:00:00', '18612967569', '888888', '', '张  斌', '', '325482', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('124', '0', '2017-01-01 00:00:00', '15801616829', '888888', '', '张  博', '', '326687', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('125', '0', '2017-01-01 00:00:00', '13436431654', '888888', '', '李  宏', '', '326502', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('126', '0', '2017-01-01 00:00:00', '18510041015', '888888', '', '王  晨', '', '326506', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('127', '0', '2017-01-01 00:00:00', '15110262092', '888888', '', '王雪莹', '', '326696', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('128', '0', '2017-01-01 00:00:00', '18500192180', '888888', '', '张冬波', '', '326505', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('129', '0', '2017-01-01 00:00:00', '18612595746', '888888', '', '李志昂', '', '', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('130', '0', '2017-01-01 00:00:00', '13269622200', '888888', '', '赵  雷', '', '326689', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('131', '0', '2017-01-01 00:00:00', '18301629987', '888888', '', '罗  星', '', '326507', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('132', '0', '2017-01-01 00:00:00', '18801192836', '888888', '', '张宇鹏', '', '326871', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('133', '0', '2017-01-01 00:00:00', '13681048575', '888888', '', '马海峰', '', '326872', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('134', '0', '2017-01-01 00:00:00', '13661274605', '888888', '', '焦  斌', '', '326873', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('135', '0', '2017-01-01 00:00:00', '13811912312', '888888', '', '呙  畅', '', '326869', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('136', '0', '2017-01-01 00:00:00', '17701322115', '888888', '', '王  堃', '', '326688', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('137', '0', '2017-01-01 00:00:00', '18602238153', '888888', '', '谷田丰', '', '326874', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('138', '0', '2017-01-01 00:00:00', '15010045921', '888888', '', '李占北', '', '326870', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('139', '0', '2017-01-01 00:00:00', '15201019913', '888888', '', '姜浩然', '', '326875', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('140', '0', '2017-01-01 00:00:00', '13661085008', '888888', '', '褚洪洋', '', '327021', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('141', '0', '2017-01-01 00:00:00', '13520337073', '888888', '', '李  骜', '', '327087', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('142', '0', '2017-01-01 00:00:00', '15001320951', '888888', '', '刘佳明', '', '', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('143', '0', '2017-01-01 00:00:00', '18911877739', '888888', '', '岳  雷', '', '327312', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('144', '0', '2017-01-01 00:00:00', '15911150953', '888888', '', '赵建龙', '', '327309', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('145', '0', '2017-01-01 00:00:00', '13911785639', '888888', '', '胡荣政', '', '', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('146', '0', '2017-01-01 00:00:00', '18811193140', '888888', '', '史碧莹', '', '', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('147', '0', '2017-01-01 00:00:00', '15210570746', '888888', '', '周航宇', '', '', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('148', '0', '2017-01-01 00:00:00', '15010302688', '888888', '', '秦  晋', '', '', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('149', '0', '2017-01-01 00:00:00', '13612000362', '888888', '', '许艺舰', '', '', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('150', '0', '2017-01-01 00:00:00', '15210570153', '888888', '', '蔡国恩', '', '', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('151', '0', '2017-01-01 00:00:00', '18201534730', '888888', '', '王  鑫', '', '', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('152', '0', '2017-01-01 00:00:00', '15705133608', '888888', '', '杨梦影', '', '', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('153', '0', '2017-01-01 00:00:00', '15801518534', '888888', '', '郭天航', '', '', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('154', '0', '2017-01-01 00:00:00', '13810279159', '888888', '', '窦康殷', '', '', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('155', '0', '2017-01-01 00:00:00', '18801183615', '888888', '', '时要磊', '', '', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('156', '0', '2017-01-01 00:00:00', '15117964551', '888888', '', '王  菁', '', '', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('157', '0', '2017-01-01 00:00:00', '18611310081', '888888', '', '任  彧', '', '', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('158', '0', '2017-01-01 00:00:00', '15201562921', '888888', '', '周  媚', '', '', '2017-01-01 00:00:00', '', '2', '', '9', null, '');
INSERT INTO `tb_user` VALUES ('159', '0', '2017-01-01 00:00:00', '13811396693', '888888', '', 'JON-MARCUS EMIL RYDER', '', '324929', '2017-01-01 00:00:00', '', '3', '', '10', '14', '');
INSERT INTO `tb_user` VALUES ('160', '0', '2017-01-01 00:00:00', '18514460509', '888888', '', 'JOHN ALAN GOODRICH', '', '325495', '2017-01-01 00:00:00', '', '3', '', '10', null, '');
INSERT INTO `tb_user` VALUES ('161', '0', '2017-01-01 00:00:00', '13121656865', '888888', '', 'LIONEL DONOVAN III', '', '321412', '2017-01-01 00:00:00', '', '3', '', '10', null, '');
INSERT INTO `tb_user` VALUES ('162', '0', '2017-01-01 00:00:00', '18513130731', '888888', '', 'NADIM  DIAB', '', '325326', '2017-01-01 00:00:00', '', '3', '', '10', null, '');
INSERT INTO `tb_user` VALUES ('163', '0', '2017-01-01 00:00:00', '18500093705', '888888', '', 'NICHOLAS JAMES PETER MOORE', '', '323125', '2017-01-01 00:00:00', '', '3', '', '10', null, '');
INSERT INTO `tb_user` VALUES ('164', '0', '2017-01-01 00:00:00', '13651359833', '888888', '', 'NICHOLAS  DAVID  YATES', '', '326403', '2017-01-01 00:00:00', '', '3', '', '10', null, '');
INSERT INTO `tb_user` VALUES ('165', '0', '2017-01-01 00:00:00', '13241183762', '888888', '', 'SIM SIM', '', '326405', '2017-01-01 00:00:00', '', '3', '', '10', null, '');

insert into tb_user_res (disabled, createtime, user_id, res_id) select 0, now(), 1, id from tb_res where disabled = 0;/*给管理员赋权限*/

/*===============================================================分隔符===================================================================*/
/* v1.1 */
ALTER TABLE `tb_inside_income_dispatches`
ADD COLUMN `income_dispatches_time`  datetime default NULL COMMENT '收文日期' AFTER `print_and_dispatch_time`;
/*===============================================================分隔符===================================================================*/

/* 二期开始 */
/* 人事档案开始 */
drop table if exists tb_user_base;
create table tb_user_base
(
    id                              bigint(20) not null auto_increment,
    disabled                        tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                      datetime not null COMMENT '创建时间',
    user_id                         bigint(20) not null COMMENT '用户ID',
    blood_type                      tinyint(2) default null COMMENT '血型：1.A 2.B 3.O 4.AB 5.其他',
    constellation                   tinyint(2) default null COMMENT '星座',
    sex                             tinyint(2) default null COMMENT '性别：1.男 2.女',
    age                             int(3) default null COMMENT '年龄',
    nation                          varchar(20) default null COMMENT '民族',
    native_place                    varchar(50) default null COMMENT '籍贯',
    ID_type                         tinyint(2) default null COMMENT '证件类型',
    ID_number                       varchar(20) default null COMMENT '证件号码',
    work_years                      int(3) default null COMMENT '工作年限',
    same_industry_work_years        int(3) default null COMMENT '同行业工作年限',
    political_status                tinyint(2) default null COMMENT '政治面貌：1.中共党员 2.共青团员 3.群众 4.其他',
    join_party_date                 datetime default null COMMENT '入党（团）时间',
    first_work_date                 datetime default null COMMENT '第一次参加工作时间',
    marriage_status                 tinyint(2) default null COMMENT '婚姻状况：1.未婚 2.已婚 3.离异 4.丧偶',
    condition_of_children           varchar(10) default null COMMENT '婚姻状态：无子女填“无”，有子女填“几子几女”',
    highest_education               varchar(20) default null COMMENT '最高学历',
    highest_education_gain_time     datetime default null COMMENT '最高学历获得时间',
    highest_offering                varchar(20) default null COMMENT '最高学位',
    highest_offering_gain_time      datetime default null COMMENT '最高学位获得时间',
    foreign_language_level          varchar(50) default null COMMENT '外语种类及水平：请填写所考取最高级别的证书和成绩',
    computer_skill                  varchar(50) default null COMMENT '计算机水平，填写等级考试名称和级别，无考级填写精通、熟练、一般',
    job_titles                      varchar(50) default null COMMENT '职称',
    job_titles_level                varchar(50) default null COMMENT '职称等级',
    job_titles_evaluation_time      datetime default null COMMENT '职称评定时间',
    job_titles_evaluation_unit      varchar(50) default null COMMENT '职称评定单位',
    job_titles_cert_id              varchar(50) default null COMMENT '职称证书号',
    is_have_press_card              tinyint(1) default null COMMENT '是否有记者证：1.有 0.无',
    press_card_no                   varchar(50) default null COMMENT '职称证书号',
    home_address                    varchar(50) default null COMMENT '家庭地址（非租住地址，具体到门牌号）',
    home_postcode                   varchar(10) default null COMMENT '家庭邮编',
    present_address                 varchar(50) default null COMMENT '现地址（具体到门牌号）',
    present_postcode                varchar(10) default null COMMENT '现邮编',
    personnel_file_address          varchar(50) default null COMMENT '人事档案存放地',
    personnel_file_postcode         varchar(10) default null COMMENT '人事档案存放地邮编',
    domicile_place                  varchar(50) default null COMMENT '户口所在地',
    domicile_type                   tinyint(2) default null COMMENT '户口性质：1.本市城镇 2.本市农村 3.外埠城镇 4.外埠农村',
    birth_place                     varchar(50) default null COMMENT '出生地',
    emergency_contact_name          varchar(50) default null COMMENT '紧急联系人姓名',
    emergency_contact_mobile        varchar(11) default null COMMENT '紧急联系人手机号',
    hobby_and_speciality            text default null COMMENT '爱好/特长（填写精通的特长和技能，到达可参赛的程度，如有成绩的请填写成绩）',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户基础信息扩展表';

drop table if exists tb_user_work;
create table tb_user_work
(
    id                              bigint(20) not null auto_increment,
    disabled                        tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                      datetime not null COMMENT '创建时间',
    user_id                         bigint(20) not null COMMENT '用户ID',
    work_time_begin                 datetime default null COMMENT '起始时间',
    work_time_end                   datetime default null COMMENT '截止时间',
    work_comp_name                  varchar(50) default null COMMENT '工作单位',
    work_duty_name                  varchar(50) default null COMMENT '岗位',
    work_place                      varchar(50) default null COMMENT '工作地点',
    work_type                       tinyint(2) default null COMMENT '工作性质：1.全职 2.兼职 3.实习',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户工作经历表';

drop table if exists tb_user_education;
create table tb_user_education
(
    id                              bigint(20) not null auto_increment,
    disabled                        tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                      datetime not null COMMENT '创建时间',
    user_id                         bigint(20) not null COMMENT '用户ID',
    admission_date                  datetime default null COMMENT '入学时间',
    graduation_date                 datetime default null COMMENT '毕业时间',
    edu_school_name                 varchar(50) default null COMMENT '就读院校',
    edu_major                       varchar(50) default null COMMENT '所学专业',
    edu_place                       varchar(50) default null COMMENT '地点',
    edu_degree                      tinyint(2) default null COMMENT '学历：1.小学、2.初中、3.高中、4.中专、5.大专、6.本科、7.研究生、8.博士生',
    edu_offering                    varchar(50) default null COMMENT '学位',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户教育经历';

drop table if exists tb_user_schoolaward;
create table tb_user_schoolaward
(
    id                              bigint(20) not null auto_increment,
    disabled                        tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                      datetime not null COMMENT '创建时间',
    user_id                         bigint(20) not null COMMENT '用户ID',
    award_date                      datetime not null COMMENT '获奖时间',
    award_name                      varchar(50) not null COMMENT '奖项名称',
    award_level                     varchar(50) not null COMMENT '奖项等级',
    award_unit                      varchar(50) not null COMMENT '颁发单位',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户所获奖项';

drop table if exists tb_user_family_member;
create table tb_user_family_member
(
    id                              bigint(20) not null auto_increment,
    disabled                        tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                      datetime not null COMMENT '创建时间',
    user_id                         bigint(20) not null COMMENT '用户ID',
    family_member_name              varchar(50) default null COMMENT '姓名',
    family_relationship             tinyint(2) default null COMMENT '与本人关系：1.父亲、2.母亲、3.岳父、4.岳母、5.配偶、6.子女、7.兄弟姐妹',
    family_member_unit              varchar(50) default null COMMENT '工作单位',
    family_member_duty              varchar(50) default null COMMENT '岗位',
    family_member_mobile            varchar(11) default null COMMENT '联系电话',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户家庭成员表';

drop table if exists tb_user_contract;
create table tb_user_contract
(
    id                              bigint(20) not null auto_increment,
    disabled                        tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                      datetime not null COMMENT '创建时间',
    user_id                         bigint(20) not null COMMENT '用户ID',
    entry_time                      datetime default null COMMENT '入职时间',
    report_time                     datetime default null COMMENT '入台报道时间',
    positive_time                   datetime default null COMMENT '转正时间',
    contract_type                   tinyint(2) default null COMMENT '合同类型：1.固定期限劳动合同、2.无固定期限劳动合同、3.实习合同、4.劳务合同',
    first_sign_time                 datetime default null COMMENT '合同首次签订时间',
    first_expiration_time           datetime default null COMMENT '合同首次到期时间',
    first_contract_duration         decimal(4,1) default null COMMENT '首次签订期限',
    meal_card_number                varchar(50) default null COMMENT '餐卡卡号',
    business_card_number            varchar(50) default null COMMENT '公务卡号',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户合同信息表';

drop table if exists tb_user_contract_renewal;
create table tb_user_contract_renewal
(
    id                              bigint(20) not null auto_increment,
    disabled                        tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                      datetime not null COMMENT '创建时间',
    user_id                         bigint(20) not null COMMENT '用户ID',
    renewal_sign_time               datetime default null COMMENT '续签合同时间',
    renewal_expiration_time         datetime default null COMMENT '续签合同到期时间',
    renewal_contract_duration       decimal(4,1) default null COMMENT '续签年限',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户合同续签信息表';

ALTER TABLE `tb_user`
ADD COLUMN `archive_status` tinyint(2) NULL DEFAULT 0 COMMENT '归档状态：0.未归档 1.已归档' AFTER `tempate_style`;
ALTER TABLE `tb_user`
ADD COLUMN `user_status`  tinyint(2) NULL COMMENT '用户类型：1.实习、2.试用、3.在职、4.离职' AFTER `archive_status`;
update tb_user set user_status = 3;
/* 人事档案结束 */

/* 简历开始 */
drop table if exists tb_resume;
create table tb_resume
(
    id                              bigint(20) not null auto_increment,
    disabled                        tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                      datetime not null COMMENT '创建时间',
    mobile                          varchar(11) not null COMMENT '手机号',
    name                            varchar(50) default null COMMENT '姓名',
    sex                             tinyint(2) default null COMMENT '性别：1.男 2.女',
    birthday                        datetime default null COMMENT '出生日期',
    duty_id                         bigint(20) default null COMMENT '应聘岗位',
    available_time                  varchar(50) default null COMMENT '可到岗时间',
    current_salary                  decimal(12,2) default null COMMENT '目前薪资（税前）',
    expect_salary                   decimal(12,2) default null COMMENT '期望薪资（税前）',
    political_status                tinyint(2) default null COMMENT '政治面貌：1.中共党员 2.共青团员 3.群众 4.其他',
    nation                          varchar(20) default null COMMENT '民族',
    marriage_status                 tinyint(2) default null COMMENT '婚姻状况：1.未婚 2.已婚 3.离异 4.丧偶',
    condition_of_children           varchar(10) default null COMMENT '子女情况：无子女填“无”，有子女填“几子几女”',
    nationality                     varchar(50) default null COMMENT '国籍',
    stature                         decimal(4,1) default null COMMENT '身高（cm）',
    body_weight                     decimal(5,1) default null COMMENT '体重（kg）',
    health_condition                varchar(50) default null COMMENT '健康状况',
    past_illness_history            varchar(50) default null COMMENT '过往病史',
    highest_education               varchar(20) default null COMMENT '最高学历',
    graduate_school                 varchar(50) default null COMMENT '毕业院校',
    major                           varchar(50) default null COMMENT '专业',
    foreign_language_level          varchar(50) default null COMMENT '外语种类及水平',
    computer_skill                  varchar(50) default null COMMENT '计算机水平',
    job_titles                      varchar(50) default null COMMENT '获得职称',
    qualification_certificate       varchar(50) default null COMMENT '资格证书',
    domicile_place                  varchar(50) default null COMMENT '户口所在地',
    ID_number                       varchar(20) default null COMMENT '证件号码',
    email                           varchar(50) default null COMMENT '邮箱',
    home_address                    varchar(50) default null COMMENT '家庭地址',
    present_address                 varchar(50) default null COMMENT '在京居住地址',
    hobby_and_interest              text default null COMMENT '兴趣爱好',
    skills                          text default null COMMENT '技能专长',
    work_years                      int(3) default null COMMENT '工作年限',
    same_industry_work_years        int(3) default null COMMENT '同行业工作年限',
    resume_status                   tinyint(2) default 1 COMMENT '简历状态：1.面试 2.未录用 3.已录用',
    attach_ids                      text default null COMMENT '上传图片(多个图片用逗号分隔)',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='简历基础信息表';

drop table if exists tb_resume_work;
create table tb_resume_work
(
    id                              bigint(20) not null auto_increment,
    disabled                        tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                      datetime not null COMMENT '创建时间',
    resume_id                       bigint(20) not null COMMENT '简历ID',
    work_time_begin                 datetime not null COMMENT '起始时间',
    work_time_end                   datetime not null COMMENT '截止时间',
    work_comp_name                  varchar(50) not null COMMENT '单位名称',
    work_duty_name                  varchar(50) not null COMMENT '岗位',
    month_salary                    decimal(12,2) default null COMMENT '月收入（税前）',
    work_type                       tinyint(2) default null COMMENT '工作性质：1.全职 2.兼职 3.实习',
    voucher_name                    varchar(50) default null COMMENT '证明人',
    voucher_mobile                  varchar(11) default null COMMENT '证明人电话',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='简历工作经历表';

drop table if exists tb_resume_education;
create table tb_resume_education
(
    id                              bigint(20) not null auto_increment,
    disabled                        tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                      datetime not null COMMENT '创建时间',
    resume_id                       bigint(20) not null COMMENT '简历ID',
    admission_date                  datetime not null COMMENT '入学时间',
    graduation_date                 datetime not null COMMENT '毕业时间',
    edu_school_name                 varchar(50) not null COMMENT '学校',
    edu_major                       varchar(50) default null COMMENT '专业',
    edu_type                        varchar(50) default null COMMENT '学习性质：1.统招、2自考',
    edu_offering                    varchar(50) default null COMMENT '学位',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='简历教育经历表';

drop table if exists tb_resume_family_member;
create table tb_resume_family_member
(
    id                              bigint(20) not null auto_increment,
    disabled                        tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                      datetime not null COMMENT '创建时间',
    resume_id                       bigint(20) not null COMMENT '简历ID',
    family_member_name              varchar(50) not null COMMENT '姓名',
    family_relationship             tinyint(2) not null COMMENT '与本人关系：1.父亲、2.母亲、3.岳父、4.岳母、5.配偶、6.子女、7.兄弟姐妹',
    family_member_unit              varchar(50) default null COMMENT '工作单位',
    family_member_duty              varchar(50) default null COMMENT '职务',
    family_member_work_place        varchar(50) default null COMMENT '所在地址',
    family_member_mobile            varchar(11) default null COMMENT '联系电话',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='简历家庭成员表';

drop table if exists tb_resume_others;
create table tb_resume_others
(
    id                              bigint(20) not null auto_increment,
    disabled                        tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                      datetime not null COMMENT '创建时间',
    resume_id                       bigint(20) not null COMMENT '简历ID',
    reward_name                     varchar(50) default null COMMENT '何时何地受到何种奖励',
    punishment_name                 varchar(50) default null COMMENT '何时何地受到何种处分',
    dimission_reason                varchar(50) default null COMMENT '离职原因',
    contract_status_with_old_unit   varchar(50) default null COMMENT '与原单位劳动合同情况',
    has_non_competition             tinyint(2) default null COMMENT '有无竞业条例：0.无 1.有',
    apply_reason                    varchar(50) default null COMMENT '应聘原因',
    has_relatives_in_unit           tinyint(2) default null COMMENT '有无亲属在本单位任职：0.无 1.有',
    accept_other_positions          tinyint(2) default null COMMENT '如未能被申请岗位录用，有无其他意向岗位：0.无 1.有',
    accept_unit_adjustment          tinyint(2) default null COMMENT '是否接受单位调剂：0.否 1.是',
    self_evaluation                 varchar(50) default null COMMENT '个人评价',
    supplement_instruction          varchar(50) default null COMMENT '其他补充说明或其它个人特别的期望',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='简历其他信息表';

drop table if exists tb_foreign_expert;
create table tb_foreign_expert
(
    id                                  bigint(20) not null auto_increment,
    disabled                            tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                          datetime not null COMMENT '创建时间',
    chinese_name                        varchar(50) not null COMMENT '中文名',
    english_name                        varchar(100) not null COMMENT '英文名',
    sex                                 tinyint(2) default null COMMENT '性别：1.男 2.女',
    birthday                            datetime default null COMMENT '出生日期',
    duty_id                             bigint(20) default null COMMENT '职位',     
    nationality                         varchar(50) default null COMMENT '国籍',
    passport_number                     varchar(20) default null COMMENT '护照号',
    passport_expiration_date            datetime default null COMMENT '护照有效期',
    residence_permit_date               datetime default null COMMENT '居留到期时间',
    contract_number                     varchar(50) default null COMMENT '合同号',
    first_sign_time                     datetime default null COMMENT '首次签约',
    first_expiration_time               datetime default null COMMENT '合同终止时间',
    foreign_expert_certificate          varchar(50) default null COMMENT '专家证',
    current_annual_salary               decimal(12,2) default null COMMENT '目前年薪',
    card_number                         varchar(50) default null COMMENT '工作证号',
    vacation_remain_current_contract    int(5) default null COMMENT '休假剩余当前合同',
    degree_and_major                    varchar(50) default null COMMENT '学历专业',
    media_work_years                    varchar(50) default null COMMENT '来台前媒体领域工作年限',
    current_month_salary                decimal(12,2) default null COMMENT '目前月薪',
    mobile                              varchar(11) default null COMMENT '手机',
    email                               varchar(50) default null COMMENT '邮箱',
    address                             varchar(50) default null COMMENT '地址',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='外籍专家信息名录表';

drop table if exists tb_candidate_hire_approval;
create table tb_candidate_hire_approval
(
    id                                  bigint(20) not null auto_increment,
    disabled                            tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                          datetime not null COMMENT '创建时间',
    resume_id                           bigint(20) default null COMMENT '简历ID',     
    duty_id                             bigint(20) default null COMMENT '应聘岗位',     
    name                                varchar(50) not null COMMENT '姓名',     
    written_score                       decimal(4,1) default null COMMENT '笔试成绩',     
    interview_score                     decimal(4,1) default null COMMENT '面试成绩',     
    final_score                         decimal(4,1) default null COMMENT '最终成绩',   
    hire_result                         tinyint(2) default null COMMENT '录用结果：1.是、0.否',
    hired_status                        tinyint(2) default null COMMENT '录用状态：1.实习、2.试用',
    current_salary                      decimal(12,2) default null COMMENT '现薪资',
    expect_salary                       decimal(12,2) default null COMMENT '期望薪资',
    personal_information                text default null COMMENT '个人情况',
    duty_level                          decimal(12,2) default null COMMENT '核定岗级（工资）',
    suggest_salary                      decimal(12,2) default null COMMENT '建议薪资',
    department_id                       bigint(20) default null COMMENT '组别（分配科组）',   
    entry_time                          datetime default null COMMENT '入职日期',
    department_approval                 varchar(50) default null COMMENT '科组审批',   
    leader_approval                     varchar(50) default null COMMENT '领导审批',   
    approval_status                     tinyint(2) default 1 COMMENT '审批状态：1.未提交、2.已提交',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应聘人员录用审批表';

DROP TABLE IF EXISTS `tb_contract_sms_remind`;
CREATE TABLE `tb_contract_sms_remind` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `remind_type` tinyint(2) DEFAULT NULL COMMENT '提醒类型：0.岗位，1.个人',
  `user_id` bigint(20) DEFAULT NULL COMMENT '被提醒人',
  `duty_id` bigint(20) DEFAULT NULL COMMENT '被提醒岗位',
  `remind_content` varchar(255) NOT NULL COMMENT '提醒内容',
  `expire_time` datetime DEFAULT NULL COMMENT'合同到期时间',
  `remind_time` int(20) NOT NULL COMMENT '提前发送时间',  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同到期提醒表'; 

drop table if exists tb_user_photo;
create table tb_user_photo
(
    id                                bigint(20) not null auto_increment,
    disabled                          tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                        datetime not null COMMENT '创建时间',
    user_id                           bigint(20) not null COMMENT '用户ID',
    ID_attach_ids                     text default null COMMENT '身份证照',
    degree_attach_ids                 text default null COMMENT '学历证明',
    blue_small_two_inch_attach_ids    text default null COMMENT '蓝底小二寸证件照',
    white_small_two_inch_attach_ids   text default null COMMENT '白底小二寸证件照',
	others_attach_ids                 text default null COMMENT '其他证照：北京市居住证、党员证、记者证等',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户证件照表';

ALTER TABLE `tb_attachments`
MODIFY COLUMN `user_id`  bigint(20) NULL COMMENT '上传附件用户ID' AFTER `file_size`;

drop table if exists tb_assessment;
create table tb_assessment
(
    id                                bigint(20) not null auto_increment,
    disabled                          tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                        datetime not null COMMENT '创建时间',
    department_id                     bigint(20) not null COMMENT '部门科组ID',
    user_ids                          text default null COMMENT '用户IDs',
    award_date                        datetime not null COMMENT '获奖时间',
    award_name                        varchar(50) not null COMMENT '奖项名称',
    award_works                       varchar(50) not null COMMENT '获奖作品',
    award_level                       varchar(50) not null COMMENT '奖项等级',
    award_unit                        varchar(50) not null COMMENT '颁发单位',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评优管理';

ALTER TABLE `tb_user_schoolaward`
ADD COLUMN `assessment_id`  bigint(20) default null COMMENT '评优管理ID' AFTER `award_unit`;

drop table if exists tb_workload_statistics;
create table tb_workload_statistics
(
    id                                bigint(20) not null auto_increment,
    disabled                          tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                        datetime not null COMMENT '创建时间',
    log_date                          datetime not null COMMENT '记入时间',
    name                              varchar(50) not null COMMENT '姓名',
    bright_spot                       decimal(6,1) default null COMMENT '亮点',
    app_write                         decimal(6,1) default null COMMENT 'APP写',
    app_send                          decimal(6,1) default null COMMENT 'APP发',
    fb_write                          decimal(6,1) default null COMMENT 'FB写',
    fb_send                           decimal(6,1) default null COMMENT 'FB发',
    weibo_write                       decimal(6,1) default null COMMENT '微博写',
    weibo_send                        decimal(6,1) default null COMMENT '微博发',
    twt_write                         decimal(6,1) default null COMMENT 'TWT写',
    twt_send                          decimal(6,1) default null COMMENT 'TWT发',
    upload_yt_or_tencent              decimal(6,1) default null COMMENT '上传YT+腾讯',
    tumblr_send                       decimal(6,1) default null COMMENT 'tumblr发',
    WeChat                            decimal(6,1) default null COMMENT '微信',
    inst                              decimal(6,1) default null COMMENT 'inst',
    google                            decimal(6,1) default null COMMENT 'Google+',
    pinterest                         decimal(6,1) default null COMMENT 'pinterest',
    total                             decimal(7,1) default null COMMENT '小结',
    others                            varchar(255) default null COMMENT '其他',
    remark                            varchar(255) default null COMMENT '备忘',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作量统计表';

drop table if exists tb_project_cooperation;
create table tb_project_cooperation
(
    id                                bigint(20) not null auto_increment,
    disabled                          tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                        datetime not null COMMENT '创建时间',
    project_date                      datetime default null COMMENT '日期',
    project_name                      varchar(50) not null COMMENT '项目名称',
    initiator_name                    varchar(50) default null COMMENT '发起人',
    cooperation_name                  varchar(50) default null COMMENT '合作方',  
    result                            varchar(50) default null COMMENT '结果',
    remark                            varchar(255) default null COMMENT '备忘',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目协同表';

drop table if exists tb_workforce_management;
create table  tb_workforce_management
(
    id                                bigint(20) not null auto_increment,
    disabled                          tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                        datetime not null COMMENT '创建时间',
    user_name 						  varchar(50) not null COMMENT '姓名',
    classes							  varchar(50) not null COMMENT '班次',
    work_time						  datetime not null COMMENT '时间',
    remark                            varchar(255) default null COMMENT '备忘',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='排班功能表';

drop table if exists tb_user_dimission_logs;
create table tb_user_dimission_logs
(
    id                                bigint(20) not null auto_increment,
    disabled                          tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                        datetime not null COMMENT '创建时间',
    user_id                           bigint(20) not null COMMENT '用户ID',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户离职日志表';

ALTER TABLE `tb_user`
ADD COLUMN `is_synchronous`  tinyint(2) NULL DEFAULT 0 COMMENT '是否是同步生成（0.否 1.是）'; 

drop table if exists tb_attendance_record;
create table tb_attendance_record
(
    id                                bigint(20) not null auto_increment,
    disabled                          tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                        datetime not null COMMENT '创建时间',
    user_id                           bigint(20) not null COMMENT '用户ID',
    employee_number					  varchar(50) DEFAULT NULL COMMENT '员工编号',
    attendance_record_month           datetime not null COMMENT '考勤月份（年月）',
    need_attendance_days              int(2) default null COMMENT '应出勤（天）',   
    real_attendance_days              decimal(3,1) default null COMMENT '实出勤（天）',   
    official_business_days            decimal(3,1) default null COMMENT '公出（天）',   
    not_clock_in_times                int(2) default null COMMENT '未打卡（次）',   
    late_early_times                  int(2) default null COMMENT '迟到早退（次）',
    workday_overtime_hours            decimal(4,1) default null COMMENT '加班-工作日时长',
    offday_overtime_hours             decimal(4,1) default null COMMENT '加班-休息日时长',
    holiday_overtime_hours            decimal(4,1) default null COMMENT '加班-节假日时长',
    this_month_shift_hours            decimal(4,1) default null COMMENT '调休-本月调休',
    last_month_shift_remain_hours     decimal(4,1) default null COMMENT '调休-上月剩余',
    this_month_shift_remain_hours     decimal(4,1) default null COMMENT '调休-本月剩余',  
    absent_days                       decimal(3,1) default null COMMENT '旷工（天）',          
    sick_leave_days                   decimal(3,1) default null COMMENT '病假（天）',          
    casual_leave_days                 decimal(3,1) default null COMMENT '事假（天）',          
    marriage_leave_days               decimal(3,1) default null COMMENT '婚假（天）',           
    funeral_leave_days                decimal(3,1) default null COMMENT '丧假（天）',          
    this_month_annual_leave_days      decimal(3,1) default null COMMENT '本月年假（天）',          
    total_annual_leave_days           decimal(3,1) default null COMMENT '累计年假（天）',  
    antenatal_examination_days        decimal(3,1) default null COMMENT '产检（天）',   
    maternity_leave_days              decimal(3,1) default null COMMENT '产假（天）',  
    legal_holiday_days                decimal(3,1) default null COMMENT '公休日', 
    remark                            varchar(255) default null COMMENT '其他说明', 
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考勤记录表'; 

drop table if exists tb_vacation_apply;
create table tb_vacation_apply
(
    id                                bigint(20) not null auto_increment,
    disabled                          tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                        datetime not null COMMENT '创建时间',
    user_id                           bigint(20) not null COMMENT '用户ID',
    vacation_begin_date               datetime not null COMMENT '休假开始日期',  
    vacation_end_date                 datetime not null COMMENT '休假结束日期',  
    vacation_dates                    decimal(4,1) default null COMMENT '休假天数',
    cancel_vacation_date              datetime default null COMMENT '销假日期',
    remark                            varchar(255) default null COMMENT '备注',
    attach_ids                        text default null COMMENT '上传附件(多个附件用逗号分隔)',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='请假申请表'; 

drop table if exists tb_vacation_apply_details;
create table tb_vacation_apply_details
(
    id                                bigint(20) not null auto_increment,
    disabled                          tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                        datetime not null COMMENT '创建时间',
    user_id                           bigint(20) not null COMMENT '用户ID',
    vacation_apply_id                 bigint(20) not null COMMENT '请假申请ID',
    leave_type                        tinyint(2) not null COMMENT '请假类型',
    begin_time                        datetime not null COMMENT '休假开始日期',  
    end_time                          datetime not null COMMENT '休假结束日期',  
    dates                             decimal(4,1) default null COMMENT '天数',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='请假申请明细表'; 

drop table if exists tb_vacation_apply_approval;
create table tb_vacation_apply_approval
(
    id                                bigint(20) not null auto_increment,
    disabled                          tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                        datetime not null COMMENT '创建时间',
    vacation_apply_id                 bigint(20) not null COMMENT '请假申请ID',
    department_id                     bigint(20) default null COMMENT '部门/科组ID',
    department_name                   varchar(50) not null COMMENT '审批部门（如果为部门/科组，为部门名称，否则为输入）',
    approval_user_id                  bigint(20) not null COMMENT '审批人ID',
    approval_text                     text not null COMMENT '审批意见',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='请假申请审批记录表'; 

drop table if exists tb_overtime_record;
create table tb_overtime_record
(
    id                                bigint(20) not null auto_increment,
    disabled                          tinyint(2) not null COMMENT '禁用状态：0.未禁用, 1.禁用',
    createtime                        datetime not null COMMENT '创建时间',
    user_id                           bigint(20) not null COMMENT '用户ID',
    overtime_status                   tinyint(2) not null COMMENT '状态：1.工作日 2.休息日',  
    overtime_date                     datetime not null COMMENT '加班日期',  
    work_begin_time                   datetime not null COMMENT '上班起始时间',  
    work_end_time                     datetime not null COMMENT '上班结束时间',  
    work_hours                        varchar(10) not null COMMENT '加班时长：格式（小时:分钟）',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='加班记录表'; 

INSERT INTO `tb_res_group` VALUES ('13', '0', now(), '合同到期提醒');
INSERT INTO `tb_res_group` VALUES ('14', '0', now(), '外籍专家信息名录');
INSERT INTO `tb_res_group` VALUES ('15', '0', now(), '简历管理');
INSERT INTO `tb_res_group` VALUES ('16', '0', now(), '应聘人员录用审批');
INSERT INTO `tb_res_group` VALUES ('17', '0', now(), '评优管理');
INSERT INTO `tb_res_group` VALUES ('18', '0', now(), '排班管理');
INSERT INTO `tb_res_group` VALUES ('19', '0', now(), '考勤记录');
INSERT INTO `tb_res_group` VALUES ('20', '0', now(), '请假申请');
INSERT INTO `tb_res_group` VALUES ('21', '0', now(), '加班记录');
INSERT INTO `tb_res_group` VALUES ('22', '0', now(), '协同记录');
INSERT INTO `tb_res_group` VALUES ('23', '0', now(), '工作量统计');
INSERT INTO `tb_res_group` VALUES ('24', '0', now(), '人员统计分析');

INSERT INTO `tb_res` VALUES ('65', '0', now(), '合同到期提醒列表', '/admin/sms/contract_sms_remind_list.htm*', '13');
INSERT INTO `tb_res` VALUES ('66', '0', now(), '查看', '/admin/sms/contract_sms_remind_view.htm*', '13');
INSERT INTO `tb_res` VALUES ('67', '0', now(), '添加', '/admin/sms/contract_sms_remind_add.htm*', '13');
INSERT INTO `tb_res` VALUES ('68', '0', now(), '启用/关闭', '/admin/sms/csr_ajax_edit_save.htm*', '13');
INSERT INTO `tb_res` VALUES ('69', '0', now(), '编辑', '/admin/sms/contract_sms_remind_edit.htm*', '13');
INSERT INTO `tb_res` VALUES ('70', '0', now(), '删除', '/admin/sms/contract_sms_remind_delete.htm*', '13');

INSERT INTO `tb_res` VALUES ('71', '0', now(), '外籍专家信息名录列表', '/admin/user/foreign_expert_list.htm*', '14');
INSERT INTO `tb_res` VALUES ('72', '0', now(), '查看', '/admin/user/foreign_expert_view.htm*', '14');
INSERT INTO `tb_res` VALUES ('73', '0', now(), '添加', '/admin/user/foreign_expert_add.htm*', '14');
INSERT INTO `tb_res` VALUES ('74', '0', now(), '编辑', '/admin/user/foreign_expert_edit.htm*', '14');
INSERT INTO `tb_res` VALUES ('75', '0', now(), '删除', '/admin/user/foreign_expert_delete.htm*', '14');

INSERT INTO `tb_res` VALUES ('76', '0', now(), '简历列表', '/admin/user/resume_list.htm*', '15');
INSERT INTO `tb_res` VALUES ('77', '0', now(), '查看', '/admin/user/resume_view.htm*', '15');
INSERT INTO `tb_res` VALUES ('78', '0', now(), '删除', '/admin/user/resume_delete.htm*', '15');
INSERT INTO `tb_res` VALUES ('79', '0', now(), '导出Excel', '/admin/user/resume_exportExcel.htm*', '15');

INSERT INTO `tb_res` VALUES ('80', '0', now(), '应聘人员录用审批列表', '/admin/user/candidate_hire_approval_list.htm*', '16');
INSERT INTO `tb_res` VALUES ('81', '0', now(), '查看', '/admin/user/candidate_hire_approval_view.htm*', '16');
INSERT INTO `tb_res` VALUES ('82', '0', now(), '添加', '/admin/user/candidate_hire_approval_add.htm*', '16');
INSERT INTO `tb_res` VALUES ('83', '0', now(), '编辑', '/admin/user/candidate_hire_approval_edit.htm*', '16');
INSERT INTO `tb_res` VALUES ('84', '0', now(), '删除', '/admin/user/candidate_hire_approval_delete.htm*', '16');
INSERT INTO `tb_res` VALUES ('85', '0', now(), '打印', '/admin/user/candidate_hire_approval_print.htm*', '16');

INSERT INTO `tb_res` VALUES ('86', '0', now(), '评优管理列表', '/admin/user/assessment_list.htm*', '17');
INSERT INTO `tb_res` VALUES ('87', '0', now(), '查看', '/admin/user/assessment_view.htm*', '17');
INSERT INTO `tb_res` VALUES ('88', '0', now(), '添加', '/admin/user/assessment_add.htm*', '17');
INSERT INTO `tb_res` VALUES ('89', '0', now(), '编辑', '/admin/user/assessment_edit.htm*', '17');
INSERT INTO `tb_res` VALUES ('90', '0', now(), '删除', '/admin/user/assessment_delete.htm*', '17');
INSERT INTO `tb_res` VALUES ('91', '0', now(), '导出Excel', '/admin/user/assessment_exportExcel.htm*', '17');

INSERT INTO `tb_res` VALUES ('92', '0', now(), '排班管理列表', '/admin/work_attendance/workforce_management_list.htm*', '18');
INSERT INTO `tb_res` VALUES ('93', '0', now(), '导入', '/admin/work_attendance/workforce_management_import.htm*', '18');
INSERT INTO `tb_res` VALUES ('94', '0', now(), '导出Excel', '/admin/work_attendance/workforce_management_exportExcel.htm*', '18');
INSERT INTO `tb_res` VALUES ('95', '0', now(), '打印', '/admin/work_attendance/workforce_management_print.htm*', '18');

INSERT INTO `tb_res` VALUES ('96', '0', now(), '考勤记录列表', '/admin/work_attendance/attendance_record_list.htm*', '19');
INSERT INTO `tb_res` VALUES ('97', '0', now(), '编辑', '/admin/work_attendance/attendance_record_edit.htm*', '19');
INSERT INTO `tb_res` VALUES ('98', '0', now(), '导出Excel', '/admin/work_attendance/attendance_record_exportExcel.htm*', '19');

INSERT INTO `tb_res` VALUES ('99', '0', now(), '请假申请列表', '/admin/work_attendance/vacation_apply_list.htm*', '20');
INSERT INTO `tb_res` VALUES ('100', '0', now(), '查看', '/admin/work_attendance/vacation_apply_view.htm*', '20');
INSERT INTO `tb_res` VALUES ('101', '0', now(), '添加', '/admin/work_attendance/vacation_apply_add.htm*', '20');
INSERT INTO `tb_res` VALUES ('102', '0', now(), '编辑', '/admin/work_attendance/vacation_apply_edit.htm*', '20');
INSERT INTO `tb_res` VALUES ('103', '0', now(), '删除', '/admin/work_attendance/vacation_apply_delete.htm*', '20');
INSERT INTO `tb_res` VALUES ('104', '0', now(), '销假', '/admin/work_attendance/vacation_apply_cancel.htm*', '20');

INSERT INTO `tb_res` VALUES ('105', '0', now(), '加班记录列表', '/admin/work_attendance/overtime_record_list.htm*', '21');

INSERT INTO `tb_res` VALUES ('106', '0', now(), '协同记录列表', '/admin/projectcoo/cooperation_list.htm*', '22');
INSERT INTO `tb_res` VALUES ('107', '0', now(), '查看', '/admin/projectcoo/cooperation_view.htm*', '22');
INSERT INTO `tb_res` VALUES ('108', '0', now(), '添加', '/admin/projectcoo/cooperation_add.htm*', '22');
INSERT INTO `tb_res` VALUES ('109', '0', now(), '编辑', '/admin/projectcoo/cooperation_edit.htm*', '22');
INSERT INTO `tb_res` VALUES ('110', '0', now(), '删除', '/admin/projectcoo/cooperation_delete.htm*', '22');

INSERT INTO `tb_res` VALUES ('111', '0', now(), '工作量统计列表', '/admin/statistics/workload_statistics_list.htm*', '23');
INSERT INTO `tb_res` VALUES ('112', '0', now(), '删除', '/admin/statistics/workload_statistics_delete.htm*', '23');
INSERT INTO `tb_res` VALUES ('113', '0', now(), '导入', '/admin/statistics/workload_statistics_import.htm*', '23');
INSERT INTO `tb_res` VALUES ('114', '0', now(), '导出Excel', '/admin/statistics/workload_statistics_exportExcel.htm*', '23');
INSERT INTO `tb_res` VALUES ('115', '0', now(), '打印', '/admin/statistics/workload_statistics_print.htm*', '23');

INSERT INTO `tb_res` VALUES ('116', '0', now(), '人员统计分析', '/admin/statistics/user_statistics_list.htm*', '24');

INSERT INTO `tb_res` VALUES ('117', '0', now(), '批量删除', '/admin/office/fixed_assets_deleteAll.htm*', '4');

INSERT INTO `tb_res` VALUES ('118', '0', now(), '导出Excel', '/admin/user/user_exportExcel.htm*', '9');
INSERT INTO `tb_res` VALUES ('119', '0', now(), '档案编辑', '/admin/user/user_file_edit.htm*', '9');
INSERT INTO `tb_res` VALUES ('120', '0', now(), '归档', '/admin/user/user_file_archive.htm*', '9');
INSERT INTO `tb_res` VALUES ('121', '0', now(), '档案打印', '/admin/user/user_file_print_view.htm*', '9');

delete from tb_user_res where user_id = 1;
insert into tb_user_res (disabled, createtime, user_id, res_id) select 0, now(), 1, id from tb_res where disabled = 0;

/*=================================二期上线后更改======================================*/
CREATE TABLE `tb_user_logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `create_user_id` bigint(20) NOT NULL COMMENT '操作者ID',
  `log_info` varchar(50) DEFAULT NULL COMMENT '日志内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户入离职日志表';


ALTER TABLE `tb_event_apply_form`
ADD COLUMN `apply_event`  varchar(100) DEFAULT NULL  COMMENT '申请事项'; 


INSERT INTO `tb_res_group` VALUES ('25', '0', now(), '入离职日志');
INSERT INTO `tb_res` VALUES ('122', '0', now(), '入离职日志', '/admin/user/user_logs_list.htm*', '25');
delete from tb_user_res where user_id = 1;
insert into tb_user_res (disabled, createtime, user_id, res_id) select 0, now(), 1, id from tb_res where disabled = 0;

/*=================================版块模块======================================*/

ALTER TABLE `tb_user_logs`
ADD COLUMN `dimission_reason`  varchar(100) DEFAULT NULL  COMMENT '离职原因'; 

ALTER TABLE `tb_user_logs`
ADD COLUMN `dimission_time`  datetime DEFAULT NULL  COMMENT '离职时间'; 


DROP TABLE IF EXISTS `tb_section`;
CREATE TABLE `tb_section` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `section_name` varchar(255) NOT NULL COMMENT '版块名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='版块表';


DROP TABLE IF EXISTS `tb_sub_section`;
CREATE TABLE `tb_sub_section` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` tinyint(2) NOT NULL COMMENT '禁用状态：0.未禁用, 1.禁用',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `sub_section_name` varchar(255) NOT NULL COMMENT '子版块名称',
  `section_id` bigint(20) NOT NULL COMMENT '部门/科组ID',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='子版块表';

ALTER TABLE `tb_user`
ADD COLUMN `section_id`  bigint(20) DEFAULT NULL  COMMENT '版块ID'; 

ALTER TABLE `tb_user`
ADD COLUMN `sub_section_id`  bigint(20) DEFAULT NULL  COMMENT '子版块ID'; 


