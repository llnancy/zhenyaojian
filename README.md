<div align="center">
    <img src="https://cdn.lilu.org.cn/website/logo/zhenyaojian-logo.png" alt="logo" style="width: 180px; height: 180px" />
    <h1 align="center">zhenyaojian</h1>
    <div align="center">
        ⚔ 镇妖剑 - 权限管理系统
        <br /><br />
        <img src="https://img.shields.io/circleci/project/github/badges/shields/master?color=%231ab1ad&label=master" alt="project" />
        <img src="https://img.shields.io/badge/JDK-8.0+-0e83c" alt="java-version" />
        <img src="https://img.shields.io/github/license/llnancy/zhenyaojian?color=FF5531" alt="license" />
    </div>
</div>

# 镇妖剑

原本是神界将军飞蓬的佩剑，因在新仙界与重楼战败而流落人间。镇妖剑具有震慑妖魔的神力，被蜀山仙剑派奉为镇派之宝。

# 概述

`zhenyaojian` 是一个轻量级权限管理系统，基于 `Spring Boot`、`Spring Security` 和 `MyBatis-Plus`
构建，其核心设计目标是开发迅速、学习简单、轻量级、易扩展。

# 特性

- 轻量级：使用较新主流技术栈，社区资源丰富。
- 模块化：模块化的结构设计，可轻松扩展。
- 工程化：友好的代码结构与注释，方便阅读学习及二次开发。
- 前后端分离：后端提供标准 `RESTful API`，使用 `JWT` 鉴权。
- 容器化：提供 `Docker`
  镜像，并实时更新推送 [`dockerhub`](https://hub.docker.com/repository/docker/sunchaserlilu/sunchaser-zhenyaojian)
  ，可一键进行容器化部署。
- 可观测性：集成 `Spring Boot Admin` 观测当前应用。

# 系统功能

基于 `RBAC` 模型实现了权限管理的核心功能，包含用户管理、角色管理及菜单管理。具体功能点如下图所示：

![系统功能](https://raw.githubusercontent.com/llnancy/zhenyaojian/master/assets/system_functions.svg)

# 项目结构

```text
zhenyaojian
├── docs 项目文档
├── zhenyaojian-admin 管理后台模块
│ └── src/main
│      ├── java/io/github/llnancy/zhenyaojian/admin/web/controller 控制器
│      └── resources 配置文件
├── zhenyaojian-framework 核心框架模块
│ └── src/main
│     │ ├── java/io/github/llnancy/zhenyaojian/framework
│     │ │                     ├── advice 全局异常处理
│     │ │                     ├── config 配置文件
│     │ │                     │ └── property 属性配置类
│     │ │                     ├── enums 枚举
│     │ │                     ├── exception 自定义异常
│     │ │                     ├── mapstruct 对象转化映射器
│     │ │                     ├── model 业务模型
│     │ │                     │ ├── request 请求对象
│     │ │                     │ └── response 响应对象
│     │ │                     ├── security Spring Security 相关
│     │ │                     │ ├── filter JWT 认证过滤器
│     │ │                     │ └── handler 权限异常处理器、认证异常处理器及退出登录处理器
│     │ │                     ├── service 业务实现层
│     │ │                     │ └── jwt JWT 模块及实现
│     │ │                     └── util 系统工具类
│     │ └── resources 存放 JWT 公私钥文件
│     └── test 测试模块
└── zhenyaojian-system 核心系统模块
    └── src/main
         ├── java/io/github/llnancy/zhenyaojian/system
         │                     ├── generate MyBatis-Plus 代码生成器
         │                     └── repository
         │                         ├── entity 领域模型
         │                         └── mapper MyBatis Mapper
         └── resources
             ├── mapper MyBatis Mapper XML
             ├── scripts 数据库脚本
             │      ├── initdb.sql 初始化数据脚本
             │      └── schema.sql 初始化表结构脚本
             └── templates 代码生成模板
```

# 技术选型

## 后端

| 技术                | 说明                     | 官网                                                                                                   |
|-------------------|------------------------|------------------------------------------------------------------------------------------------------|
| Spring Boot       | 基础服务框架                 | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)                     |
| Spring Security   | 认证和授权框架                | [https://spring.io/projects/spring-security](https://spring.io/projects/spring-security)             |
| mojian            | 魔剑 - 业务开发工具集           | [https://github.com/llnancy/mojian](https://github.com/llnancy/mojian)                               |
| MyBatis-Plus      | ORM 框架：对 MyBatis 增强    | [https://baomidou.com](https://baomidou.com)                                                         |
| MP-Generate       | 基于 MyBatis-Plus 的代码生成器 | [https://baomidou.com/pages/779a6e/](https://baomidou.com/pages/779a6e/)                             |
| JWT               | JWT 登录认证               | [https://github.com/jwtk/jjwt](https://github.com/jwtk/jjwt)                                         |
| Lombok            | 简化 Bean 对象             | [https://projectlombok.org](https://projectlombok.org)                                               |
| Mapstruct         | Java Bean 对象转化映射器      | [https://mapstruct.org](https://mapstruct.org)                                                       |
| Hutool            | 小而全的 Java 工具类库         | [https://hutool.cn](https://hutool.cn)                                                               |
| Guava             | Google 核心 Java 类库      | [https://github.com/google/guava](https://github.com/google/guava)                                   |
| Spring Boot Admin | Spring Boot 服务监控       | [https://github.com/codecentric/spring-boot-admin](https://github.com/codecentric/spring-boot-admin) |

> `mojian` 项目是本人开发的一个专注于业务开发的工具集，目前已发布至中央仓库，欢迎 `star`。

## 前端

前端项目代码仓库：[https://github.com/llnancy/zhenyaojian-ui](https://github.com/llnancy/zhenyaojian-ui)

| 技术             | 说明                      | 官网                                                                                       |
|----------------|-------------------------|------------------------------------------------------------------------------------------|
| Vue2           | 基础前端框架                  | [https://v2.vuejs.org](https://v2.vuejs.org)                                             |
| Vue-Router     | 路由框架                    | [https://router.vuejs.org](https://router.vuejs.org)                                     |
| Vuex           | 全局状态管理框架                | [https://router.vuejs.org](https://router.vuejs.org)                                     |
| Axios          | 基于 Promise 的 HTTP 客户端   | [https://axios-http.com](https://axios-http.com)                                         |
| NProgress      | 页面加载进度条组件               | [https://github.com/rstacruz/nprogress](https://github.com/rstacruz/nprogress)           |
| Ant-Design-Vue | UI 组件库                  | [https://1x.antdv.com/docs/vue/introduce-cn](https://1x.antdv.com/docs/vue/introduce-cn) |
| Vue Antd Admin | Ant Design Pro 的 Vue 实现 | [https://github.com/iczer/vue-antd-admin](https://github.com/iczer/vue-antd-admin)       |

# 环境要求

- JDK8+
- MySQL5.7+
- Maven3.6+

# 项目演示

在线体验地址：[https://lilu.org.cn/zhenyaojian/](https://lilu.org.cn/zhenyaojian/)

> 账号密码：llnancy/123456

## 效果展示

![menu.png](https://raw.githubusercontent.com/llnancy/zhenyaojian/master/assets/menu.png)
![user.png](https://raw.githubusercontent.com/llnancy/zhenyaojian/master/assets/user.png)
![role.png](https://raw.githubusercontent.com/llnancy/zhenyaojian/master/assets/role.png)
![operate_log.png](https://raw.githubusercontent.com/llnancy/zhenyaojian/master/assets/operate_log.png)
![login_log.png](https://raw.githubusercontent.com/llnancy/zhenyaojian/master/assets/login_log.png)

# 项目部署

## 本地运行

- `git clone https://github.com/llnancy/zhenyaojian.git`
- `IDEA` 导入项目
- 连接 `MySQL` 执行 `zhenyaojian-system/src/main/resources/scripts/schema.sql` 数据库初始化脚本
- 修改 `zhenyaojian-admin/src/main/resources/application-dev.yml` 配置文件，更新 `MySQL` 连接地址、用户名及密码等
- 运行 `io.github.llnancy.zhenyaojian.admin.ZhenYaoJianApplication.main` 方法即可启动后端服务
- 默认初始账号密码为 `admin/123456`

## `Docker` 运行

需自行修改 `docker-compose.yml` 文件中的数据库连接地址、用户名及密码等配置。一键启动命令如下：

```shell
docker-compose -f docker-compose.yml up -d
```

# 致谢

- 感谢 [https://github.com/iczer/vue-antd-admin](https://github.com/iczer/vue-antd-admin) 提供的前端模板

# 参与贡献

我们非常欢迎您的贡献，您可以通过以下方式和我们一起共建：

- 在您的公司或个人项目中使用 [zhenyaojian](https://github.com/llnancy/zhenyaojian)。
- 通过 [Issue](https://github.com/llnancy/zhenyaojian/issues) 报告 `Bug` 或进行提问。
- 提交 [Pull Request](https://github.com/llnancy/zhenyaojian/pulls) 优化现有代码。

# 许可证

[Apache License 2.0](https://github.com/llnancy/zhenyaojian/blob/master/LICENSE)

Copyright (c) 2022-present llnancy

# 捐赠

如果该项目对您有所帮助，可以请作者喝一杯咖啡。

| 支付宝                                        | 微信                                            |
|--------------------------------------------|-----------------------------------------------|
| ![支付宝](https://cdn.lilu.org.cn/website/base/alipay.png) | ![微信](https://cdn.lilu.org.cn/website/base/wechat-pay.png) |
