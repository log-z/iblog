# iBolg
基于 Spring Boot + MyBatis 搭建的博客平台。现已适配前后端分离。

## 网站入口
> 桌面版暂不可用。
* 桌面版（JSP）
    * 主页：`/`
    * 管理员后台：`/admin/`
* 移动版（RESTful API + Vue）
    * 主页：`/m/index.html`
    * 管理员后台：`/m/admin.html`

## 功能点
* 用户相关
    * 用户注册
    * 用户登陆
    * 用户退出
    * 个人主页（列出已发布的文章）
    * 个人信息
        * 修改名称
        * 修改密码
    * 管理文章
        * 写文章
        * 修改文章
* 文章相关
    * 博客主页（列出最新文章）
    * 搜索
    * 文章详情
    * 文章图片
* 管理员相关
    * 管理员注册
    * 管理员登陆
    * 管理员退出
    * 管理员门户
    * 管理用户
        * 查看所有用户
        * 删除用户
    * 管理文章
        * 查看所有文章
        * 删除文章

## 实现方式
* 安全
    * 密码：前端采用CryptoJs库进行sha256加密，后端采用SpringSecurity库进行BCrypt加密。
    * 鉴权：通过拦截器检查Session鉴权信息，阻止非法访问。
    * 防XSS攻击：采用Apache的Text库把特殊字符编码为HTML实体。
* 数据
    * 数据源：采用 mybatis-spring-boot-starter 库管理数据源（MySQL）。
    * 验证：采用HibernateValidator库简化数据验证过程。
    * 国际化：通过信息源（MessageSource）为不同区域的用户自动匹配翻译。
* 视图
    * ~~模板引擎：桌面版采用JSP渲染视图。~~
    * 前端分离：移动版采用Vue.js库渲染视图。
    * RESTful API：自定义通用的响应模板，并采用Jackson库将其序列化为JSON。

## 设计图
> 设计图暂未更新为 Spring boot 版本。
* PDF：[design/设计图.pdf](design/设计图.pdf)
* JPG：[design/设计图.jpg](design/设计图.jpg)
