# JavaBase

基于springboot 框架的再封装,在此框架下规范行为并更加关注业务。

## 模块

- database:数据库SQL语句,仅有适用于security的**用户登录表/用户权限表**

- launcher:启动模块,为了优雅,所有模块的启动都集中在这里,是**Application入口**

- base:**一切的基础**,包括切面编程中日志记录,IP拦截限制,规范响应体结构,全局异常处理,自定义错误码,拦截器配置等
- db:**数据库层管理**,包括MySQL(内含Mybatis-Plus代码生成器) ,Redis设置,MongoDB(后续再补充)
- security:**安全模块**,包括最基本controller用户注册和登录,使用jwt token验证,可配置用户权限路径,灵活开放不同的path
- web:一般后台系统的服务器端内容,主要业务模块
- ws:**websocket通信**

项目名:com.xyz.[module],增加todo,方便修改为个人/公司项目的名称,只需打开todo列表,自行替换

## 详细介绍

### database

存放SQL建表语句,仅有两个表

`db_user`

- id
- user_name
- password
- phone
- email(登录时的唯一账号)
- register_time

`db_admin`

- id
- user_id(db_user外键进行关联)
- admin_level(权限等级,默认是normal,可自行设置admin等,需在security配置文件中修改权限命名)
- permission_time

password不能直接插入,需要走controller中的register请求,如下(请使用json发送)

```
POST
http://127.0.0.1:8081/account/register

request:
{
    "name": "user_name",
    "password": "123456",
    "email": "hnlbzkk@163.com",
    "phone": "17752570726"
}

response:
{
    "code": "SUC0000",
    "message": "成功",
    "data": null
}
```

### launcher

启动模块,这里只引用其他子模块,仅有一个Application启动,也在这里做配置 appliaction.yml

### base

一切的基础,是基于springboot提供的各注解等内容,对行为进行约束

**切面编程**

- 通过@LogApi注解进行controller方法的日志记录
- 通过@AccessLimit注解进行请求IP记录,设置不同的限制请求次数达到拒绝策略,可自行扩展永久加入黑名单等操作

**响应体**

放在message中,包括错误码定制,响应的结构体规范,要求所有请求都需要此结构

**controller增强**

只针对响应体,强行将不是规范的response进行封装

全局捕获异常,并进行日志记录和封装

**基础工具**

放在utils包中,可自行添加最基础通用的工具类,例如DataTime

### db

这里进行所有的数据库交互,提供封装好的操作数据库方法.不再放入web模块中,web模块只需要引入该子模块直接调用相关方法

### security

安全模块,这里是对开放哪些接口的集中管理处,详细配置可见`SecurityConfig`.todo list中可自行灵活配置

### web/ws

业务模块



## 测试

已提供最基础的controller用作测试.下面所有的request内容,都是json形式,放入Body中

### 注册

无需token

```
POST
http://127.0.0.1:8081/account/register

request:
{
    "name": "user_name",
    "password": "123456",
    "email": "hnlbzkk@163.com",
    "phone": "17752570726"
}

response:
{
    "code": "SUC0000",
    "message": "成功",
    "data": null
}
```

### 登录

无需token,返回token

```
GET
http://127.0.0.1:8081/account/login

request:
{
    "email":"hnlbzkk@163.com",
    "password":"123456"
}

response:
{
    "code": "SUC0000",
    "message": "成功",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0NGRiZjU2ZjAwNWM0NTliYTc0NTk1MjUzYWE3ZmIwZSIsInN1YiI6IntcImFjY291bnROb25FeHBpcmVkXCI6dHJ1ZSxcImFjY291bnROb25Mb2NrZWRcIjp0cnVlLFwiY3JlZGVudGlhbHNOb25FeHBpcmVkXCI6dHJ1ZSxcImVuYWJsZWRcIjp0cnVlLFwicGFzc3dvcmRcIjpcIlwiLFwicGVybWlzc2lvbnNcIjpbXCJub3JtYWxcIl0sXCJ1c2VyXCI6e1wiZW1haWxcIjpcImhubGJ6a2tAMTYzLmNvbVwiLFwiaWRcIjoyNCxcInBhc3N3b3JkXCI6XCJcIixcInBob25lXCI6XCIxNzc1MjU3MDcyNlwiLFwicmVnaXN0ZXJUaW1lXCI6XCIyMDIzLTA4LTE0VDAxOjI2OjQ0XCIsXCJ1c2VyTmFtZVwiOlwidXNlcl9uYW1lXCJ9LFwidXNlcm5hbWVcIjpcImhubGJ6a2tAMTYzLmNvbVwifSIsImlzcyI6IkphdmFCYXNlIiwiaWF0IjoxNjkyMDI2NzAzLCJleHAiOjE2OTIxMTMxMDN9.pytoRi4aKe0odSK6yeCe_M_rtdQPvBCrWZjyOxA6E2o"
    }
}
```

### 权限限制

以上步骤先创建两个用户,admin_level分别为 `normal` `admin`

**admin**

在Headers中添加token

```
GET
http://127.0.0.1:8081/admin/hello

Headers:
token:xxxx

response:
{
    "code": "ERR2009",
    "message": "用户权限不足"
}
或者
{
    "code": "SUC0000",
    "message": "成功",
    "data": "hello admin."
}
```

**normal**

同上

### IP限制

这里并没有做用户权限限制,属于直接放开的接口

```
GET
http://127.0.0.1:8081/access/hello

response:
{
    "code": "SUC0000",
    "message": "成功",
    "data": "hello user."
}
或者
{
    "code": "ERR3001",
    "message": "该IP被禁止,请稍后再试",
    "data": null
}
```



