--- 初始用户名密码为 admin/123456
INSERT INTO `sunchaser_zyj`.`zyj_user` (`id`, `account`, `password`, `nick_name`, `avatar`, `sex`, `email`, `phone_number`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (1, 'admin', '$2a$10$EOjcQYs1lBXeWFfcYXExY.pCjTQ5aLdzu126w7hBbeRUMq21w.HjK', '超级管理员', 'https://cdn.lilu.org.cn/avatar.png', '男', 'admin@lilu.org.cn', '10086', 0, 0, 'system', NOW(), 'system', NOW());

INSERT INTO `sunchaser_zyj`.`zyj_role` (`id`, `code`, `name`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (1, 'super-admin', '超级管理员', 0, 0, 0, 'system', NOW(), 'system', NOW());

INSERT INTO `sunchaser_zyj`.`zyj_user_role` (`id`, `user_id`, `role_id`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (1, 1, 1, 1, 'system', NOW(), 'system', NOW());

INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (1, '首页', 0, 2, '', '/', 'Tabs', 'index', 0, 0, 1, 'system', NOW(), 'system', NOW());
INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (2, '系统管理', 0, 0, 'setting', '/system', 'BlankView', '', 0, 0, 0, 'system', NOW(), 'system', NOW());
INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (3, '菜单管理', 2, 1, 'menu-unfold', '/system/menu', 'MenuManage', '', 0, 0, 0, 'system', NOW(), 'system', NOW());
INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (4, '用户管理', 2, 1, 'user', '/system/user', 'UserManage', '', 10, 0, 0, 'system', NOW(), 'system', NOW());
INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (5, '角色管理', 2, 1, 'idcard', '/system/role', 'RoleManage', '', 20, 0, 0, 'system', NOW(), 'system', NOW());

INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (6, '可观测性', 0, 0, 'radar-chart', '/observability', 'BlankView', '', 0, 0, 0, 'system', NOW(), 'system', NOW());
INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (7, 'Spring Boot Admin', 6, 1, 'stock', '/observability/spring-boot-admin', 'SpringBootAdmin', 'observability:sba:view', 1, 0, 0, 'system', NOW(), 'system', NOW());
INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (8, '系统日志', 6, 0, 'file-text', '/observability/logs', 'BlankView', '', 0, 0, 0, 'system', NOW(), 'system', NOW());
INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (9, '操作日志', 8, 1, 'file-done', '/observability/logs/operate', 'OperateLog', '', 0, 0, 0, 'system', NOW(), 'system', NOW());
INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (10, '登录日志', 8, 1, 'audit', '/observability/logs/login', 'LoginLog', '', 0, 0, 0, 'system', NOW(), 'system', NOW());


INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (11, '新建菜单', 3, 2, 'plus', '', '', 'system:permission:create', 0, 0, 0, 'system', NOW(), 'system', NOW());
INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (12, '编辑菜单', 3, 2, 'edit', '', '', 'system:permission:update', 0, 0, 0, 'system', NOW(), 'system', NOW());
INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (13, '隐藏菜单', 3, 2, 'close-circle', '', '', 'system:permission:update', 0, 0, 0, 'system', NOW(), 'system', NOW());
INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (14, '删除菜单', 3, 2, 'delete', '', '', 'system:permission:delete', 0, 0, 0, 'system', NOW(), 'system', NOW());
INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (15, '查询菜单', 3, 2, '', '', '', 'system:permission:list', 0, 0, 0, 'system', NOW(), 'system', NOW());

INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (16, '新建用户', 4, 2, 'plus', '', '', 'system:user:create', 0, 0, 0, 'system', NOW(), 'system', NOW());
INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (17, '编辑用户', 4, 2, 'edit', '', '', 'system:user:update', 0, 0, 0, 'system', NOW(), 'system', NOW());
INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (18, '删除用户', 4, 2, 'delete', '', '', 'system:user:delete', 0, 0, 0, 'system', NOW(), 'system', NOW());
INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (19, '查询用户', 4, 2, '', '', '', 'system:user:list', 0, 0, 0, 'system', NOW(), 'system', NOW());
INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (20, '分配角色', 4, 2, 'copy', '', '', 'system:user-role:assign', 0, 0, 0, 'system', NOW(), 'system', NOW());

INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (21, '新建角色', 5, 2, 'plus', '', '', 'system:role:create', 0, 0, 0, 'system', NOW(), 'system', NOW());
INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (22, '编辑角色', 5, 2, 'edit', '', '', 'system:role:update', 0, 0, 0, 'system', NOW(), 'system', NOW());
INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (23, '赋权', 5, 2, 'snippets', '', '', 'system:role-permission:assign', 0, 0, 0, 'system', NOW(), 'system', NOW());
INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (24, '停用角色', 5, 2, 'close-circle', '', '', 'system:role:update', 0, 0, 0, 'system', NOW(), 'system', NOW());
INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (25, '删除角色', 5, 2, 'delete', '', '', 'system:role:delete', 0, 0, 0, 'system', NOW(), 'system', NOW());
INSERT INTO `sunchaser_zyj`.`zyj_permission` (`id`, `name`, `parent_id`, `type`, `icon`, `path`, `component`, `permission`, `sort_value`, `status`, `is_deleted`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (26, '查询角色', 5, 2, '', '', '', 'system:role:list', 0, 0, 0, 'system', NOW(), 'system', NOW());