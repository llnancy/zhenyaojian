CREATE
DATABASE `sunchaser_zyj` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE
`sunchaser_zyj`;

DROP TABLE if EXISTS `zyj_user`;
CREATE TABLE `sunchaser_zyj`.`zyj_user`
(
    `id`           bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `account`      varchar(64)  NOT NULL COMMENT '用户账号',
    `password`     varchar(255) NOT NULL COMMENT '用户密码',
    `nick_name`    varchar(32)  NOT NULL COMMENT '用户昵称',
    `avatar`       varchar(128) NOT NULL COMMENT '用户头像',
    `sex`          enum('男','女','未知') NOT NULL DEFAULT '未知' COMMENT '用户性别',
    `email`        varchar(64) DEFAULT NULL COMMENT '用户邮箱',
    `phone_number` varchar(11) DEFAULT NULL COMMENT '电话号码',
    `status`       tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户状态（0：正常；1：停用）',
    `is_deleted`   tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除（0：正常；1：删除）',
    `create_user`  varchar(64)  NOT NULL COMMENT '创建人',
    `create_time`  datetime     NOT NULL COMMENT '创建时间',
    `update_user`  varchar(64)  NOT NULL COMMENT '更新人',
    `update_time`  datetime     NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表';

DROP TABLE if EXISTS `zyj_role`;
CREATE TABLE `sunchaser_zyj`.`zyj_role`
(
    `id`          bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `code`        varchar(32) NOT NULL COMMENT '角色编码',
    `name`        varchar(16) NOT NULL COMMENT '角色名称',
    `sort_value`  int(4) NOT NULL DEFAULT 0 COMMENT '显示排序',
    `status`      tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '角色状态（0：正常；1：停用）',
    `is_deleted`  tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除（0：正常；1：删除）',
    `create_user` varchar(64) NOT NULL COMMENT '创建人',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `update_user` varchar(64) NOT NULL COMMENT '更新人',
    `update_time` datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name` (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表';

DROP TABLE if EXISTS `zyj_permission`;
CREATE TABLE `sunchaser_zyj`.`zyj_permission`
(
    `id`          bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `name`        varchar(32)  NOT NULL COMMENT '权限名称',
    `parent_id`   bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '父级权限ID',
    `type`        tinyint(1) UNSIGNED NOT NULL COMMENT '权限类型（0：目录；1：菜单；2：按钮）',
    `icon`        varchar(128) NOT NULL DEFAULT '' COMMENT '权限图标',
    `path`        varchar(128) NOT NULL COMMENT '路由地址',
    `component`   varchar(128)          DEFAULT NULL COMMENT '组件名称',
    `permission`  varchar(128) NOT NULL DEFAULT '' COMMENT '权限标识',
    `sort_value`  int(4) NOT NULL DEFAULT 0 COMMENT '显示排序',
    `status`      tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '权限状态（0：显示；1：隐藏）',
    `is_deleted`  tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除（0：正常；1：删除）',
    `create_user` varchar(64)  NOT NULL COMMENT '创建人',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `update_user` varchar(64)  NOT NULL COMMENT '更新人',
    `update_time` datetime     NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限表';

DROP TABLE if EXISTS `zyj_user_role`;
CREATE TABLE `sunchaser_zyj`.`zyj_user_role`
(
    `id`          bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `user_id`     bigint(20) UNSIGNED NOT NULL COMMENT '用户ID',
    `role_id`     bigint(20) UNSIGNED NOT NULL COMMENT '角色ID',
    `is_deleted`  tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除（0：正常；1：删除）',
    `create_user` varchar(64) NOT NULL COMMENT '创建人',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `update_user` varchar(64) NOT NULL COMMENT '更新人',
    `update_time` datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色关联表';

DROP TABLE if EXISTS `zyj_role_permission`;
CREATE TABLE `sunchaser_zyj`.`zyj_role_permission`
(
    `id`            bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `role_id`       bigint(20) UNSIGNED NOT NULL COMMENT '角色ID',
    `permission_id` bigint(20) UNSIGNED NOT NULL COMMENT '权限ID',
    `is_deleted`    tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除（0：正常；1：删除）',
    `create_user`   varchar(64) NOT NULL COMMENT '创建人',
    `create_time`   datetime    NOT NULL COMMENT '创建时间',
    `update_user`   varchar(64) NOT NULL COMMENT '更新人',
    `update_time`   datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限关联表';

CREATE TABLE `zyj_system_log`
(
    `id`             bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `request_ip`     varchar(32)   DEFAULT NULL COMMENT '请求IP',
    `user_agent`     varchar(255)  DEFAULT NULL COMMENT '用户代理 UA',
    `device_type`    varchar(32)   DEFAULT NULL COMMENT '设备类型',
    `browser`        varchar(32)   DEFAULT NULL COMMENT '浏览器名称',
    `os`             varchar(32)   DEFAULT NULL COMMENT '操作系统',
    `region`         varchar(32)   DEFAULT NULL COMMENT '地理位置',
    `request_uri`    varchar(128)  DEFAULT NULL COMMENT '请求地址',
    `request_method` varchar(7)    DEFAULT NULL COMMENT 'HTTP 请求方式',
    `class_name`     varchar(255)  DEFAULT NULL COMMENT '请求类名',
    `method_name`    varchar(128)  DEFAULT NULL COMMENT '请求方法名',
    `parameters`     longtext COMMENT '请求参数',
    `response`       longtext COMMENT '返回值',
    `exception`      longtext COMMENT '异常描述',
    `user_id`        varchar(32)   DEFAULT NULL COMMENT '操作者 ID',
    `user_account`   varchar(64)   DEFAULT NULL COMMENT '操作者账户',
    `description`    varchar(1024) DEFAULT NULL COMMENT '操作描述',
    `status`         tinyint(1) DEFAULT NULL COMMENT '请求状态（0：成功，1：异常）',
    `type`           varchar(32)   DEFAULT NULL COMMENT '日志类型',
    `start_time`     datetime      DEFAULT NULL COMMENT '开始时间',
    `end_time`       datetime      DEFAULT NULL COMMENT '结束时间',
    `rt`             int(11) unsigned DEFAULT NULL COMMENT '消耗时间（单位：ms）',
    `create_user`    varchar(64)   DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime      DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT='系统日志记录表';

--- 可选扩展表参考
DROP TABLE if EXISTS `zyj_department`;
CREATE TABLE `sunchaser_zyj`.`zyj_department`
(
    `id`            bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `name`          varchar(32)  NOT NULL COMMENT '部门名称',
    `parent_id`     bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '父级部门ID',
    `ancestor_path` varchar(255) NOT NULL COMMENT '祖先部门结构',
    `sort_value`    int(4) NOT NULL DEFAULT 0 COMMENT '显示排序',
    `leader`        varchar(128) NOT NULL COMMENT '部门负责人',
    `phone_number`  varchar(11)  NOT NULL COMMENT '电话号码',
    `status`        tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '部门状态（0：正常；1：停用）',
    `is_deleted`    tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除（0：正常；1：删除）',
    `create_user`   varchar(64)  NOT NULL COMMENT '创建人',
    `create_time`   datetime     NOT NULL COMMENT '创建时间',
    `update_user`   varchar(64)  NOT NULL COMMENT '更新人',
    `update_time`   datetime     NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表';

DROP TABLE if EXISTS `zyj_position`;
CREATE TABLE `sunchaser_zyj`.`zyj_position`
(
    `id`          bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `code`        varchar(32) NOT NULL COMMENT '岗位编码',
    `name`        varchar(32) NOT NULL COMMENT '岗位名称',
    `sort_value`  int(4) NOT NULL DEFAULT 0 COMMENT '显示排序',
    `status`      tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '岗位状态（0：正常；1：停用）',
    `is_deleted`  tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除（0：正常；1：删除）',
    `create_user` varchar(64) NOT NULL COMMENT '创建人',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `update_user` varchar(64) NOT NULL COMMENT '更新人',
    `update_time` datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位表';
