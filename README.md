# Spring Boot EF project

## 功能

- 用户注册和登录
- 书籍管理（添加、更新、删除、查看书籍）
- 订单管理（创建、取消、查看订单状态）

## 技术栈

- Java 21
- Spring Boot 3.3
- Spring Security
- JWT (JSON Web Token)
- JPA (Hibernate)
- MySQL
- Lombok

## 运行项目

### 先决条件

1. 确保你的系统上已安装 Java 21。
2. 确保你的系统上已安装 Gradle。
3. 确保你的系统上已安装 Docker。

## 启动项目

```bash
./gradlew bootRun
```

## 测试项目功能

1. 导入 [EF.postman_collection.json](EF.postman_collection.json) 到 postman 中
2. 对每个 api 进行测试