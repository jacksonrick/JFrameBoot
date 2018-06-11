# JFrame
##### JFrame - SpringBoot & Cloud Project `v5.0 beta1`
made by @jackson-rick   <br>
本架构是对技术框架的整合及开发的规范


### 模块
* jframe `文档/配置/共享静态资源/Docker`
* jframe-core `实体对象/服务类/系统相关`
* jframe-utils `工具类`
* jframe-web-app `App端`
* jframe-web-front `PC端`
* jframe-web-manage `后台管理端`
* jframe-web-test `模块测试`
* jframe-service-order `微服务模块测试`
* jframe-service-oauth `OAuth2.0模块`

### 集成
* Spring/SpringMVC/MyBatis/SpringBoot/SpringCloud
* Spring Redis/Redis Session/Redisson/FastDFS
* 拦截器/AppToken/自定义权限/SpringSecurity
* 后台管理功能：权限管理、模块管理、地址、文件、工具等
* 线程池、日志服务、分布式日志Flume、异常管理、多数据源、分布式事务
* PageHelper分页、封装分页组件
* Freemarker模板、封装函数和指令
* Quartz(单点/集群)、管理器、异步任务
* 第三方：微信SDK/支付宝SDK/推送/高德/阿里云/短信/邮件
* Spring-WebSocket、Openfire、Actuator健康监控、数据源监控
* Util包：字符、时间、数字转换/JSON/Http/PDF+Excel/分页/文件/对象/验证器...
* JS插件：封装：Ajax/DataTables/CityPicker/Layer/DatePicker...
* 可开关服务：db、scheduler[springsch|quartz]、cache、activemq、socket、fdfs、endpoint、eureka、feign...
* 测试模块：[jframe-web-test]DEMO、组件等测试
* Jenkins自动化部署、脚本

### 更新日志：
* v5.0 beta1 `20180611`
    * 1.架构优化，轻量化服务
    * 2.优化测试模块，集成更多的服务测试
    * 3.新增OAuth授权服务及三种模式
    * 4.优化打包，集成Docker配置
    * 5.移除全部的拦截器，更换为AOP，包括请求日志、权限、统一的错误处理等
    * 6.解决部分依赖不兼容的问题，修复个别BUG
* v4.8 `20180507`
    * 1.优化Jar依赖关系
    * 2.新增AMPQ、ELK等组件
    * 3.移除model模块
* v4.7.1 `20180423`
    * 1.完善系统架构文档说明
    * 2.优化日志、Sys、Redisson等服务的配置
* v4.7 `20180403`
    * 1.修复BUG
    * 2.优化参数和部署脚本
* v4.6 beta2 `20180327`
    * 1.增强系统稳定性
    * 2.修复BUG
* v4.6 beta1 `20180315`
    * 1.升级核心框架：SpringBoot 2.0/SpringCloud Finchley.M7
    * 2.优化各种组件的兼容性问题
    * 3.优化并简化yml配置
    * 4.新增测试模块，测试DEMO
    * 5.其他优化：日志、配置、监控等
* v4.5.1 `20180309`
    * 1.优化日志输出、拦截器、支付、run脚本
    * 2.新增二维码生成工具
    * 3.新增JenkinsAPI管理，可自动部署
* v4.5 `20180303`
    * 1.服务组件条件化加载，可单独启用或关闭
    * 2.优化SpringBoot配置、监控服务端UI
    * 3.新增Quartz，可动态管理任务[原SpringSchedule]
    * 4.移除Jedis，使用Spring Template
* v4.4.4 `20180227`
    * 1.添加了服务消费示例[service-order]
    * 2.分离监控配置文件
    * 3.整合测试DEMO[web-front]
    * 4.下个版本将集中优化模块依赖、服务加载和配置
* v4.4.3 `20180225`
    * 1.优化服务端启动脚本
    * 2.删除支付宝SDK本地Jar，替换为Maven
    * 3.修复日志文件名称为undefined的问题
* v4.4.2 `20180208`
    * 1.服务监控中心和配置中心(cloud-server)
    * 2.优化日志、上传、定时任务配置
    * 3.修复Maven打包后程序出现文件找不到的问题
* v4.4.1 `20180202`
    * 1.新增模块删除和地址导出功能
    * 2.更新fa图标库，优化CSS效果
    * 3.服务监控中心[Test]
* v4.4 `20180130`
    * 1.更新springboot-druid-starter
    * 2.新增druid数据源监控
    * 3.多数据源配置和自动切换及事务
    * 4.修复了部分名称和变量引起的BUG
* v4.3.1 `20180129`
    * 1.修复若干问题
    * 2.新增对上传、富文本、Select2等插件的BS验证
    * 3.优化了js注释和参数命名
    * 4.优化Tomcat性能
* v4.3 `20180126`
    * 1.集成Openfire+Smack即时通信服务
    * 2.集成微信支付SDK，新增多个接口
* v4.2.2 `20180119`
    * 1.优化日志服务
    * 2.自动生成实体类工具现可以生成HTML
    * 3.优化支付宝微信接口
* v4.2.1 `20180116`
    * 1.集成Spring WebSocket并与Redis Session集成
    * 2.优化目录结构，暂时关闭不需要的组件服务
    * 3.通过ConfigurationProperties进行配置
* v4.2 `20180109`
    * 1.集成spring activemq
    * 2.集成boot-admin服务监控[front]
    * 3.优化代码和目录结构
* v4.1 `20180104`
    * 1.集成Spring Redisson分布式事务
    * 2.Spring Session Redis管理
    * 3.Spring Redis缓存管理
    * 4.FastDFS基于SpringBoot配置
    * 5.集成SpringCloud服务发现
    * 6.SpringBoot 监控[测试]
* v4.0 `20171218`
    * 1.基于SpringBoot构建
    * 2.集中文件配置
    * 3.更新为Logback日志管理
    * 4.优化多个核心组件的配置
    * 5.基于Maven的一键式打包
* v3.7.1 `20171103`
    * 1.修复BUG
* v3.7 `20171022`
    * 1.更新页面模板：Freemarker
    * 2.集成Flume日志
* v3.6.1 `20171016`
    * 1.新的分页插件：pageHelper
    * 2.优化dataTables
* v3.6 `20171012`
    * 1.新增Spring Data Session
    * 2.升级Spring至4.3.11
    * 3.优化日志服务
* v3.5.5 `20170930`
    * 1.新增Quartz管理器
    * 2.优化日志服务
    * 3.新增地址插件
    * 4.优化部分JS插件
* v3.5.4 `20170921`
    * 1.新增后台模块管理
    * 2.新增阿里云服务
    * 3.优化日志服务
* v3.5.3 `20170825`
    * 1.优化后台页面和部分JS插件
    * 2.优化支付接口和推送接口
* v3.5.2 `20170801`
    * 1.优化Redis服务
    * 2.优化AppToken服务
* v3.5.1 `20170620`
    * 1.优化SQL查询和实体类查询
    * 2.新增第三方验证码接口
    * 3.新增多个Util工具类
    * 4.新增和优化PC/APP上传插件和服务端
    * 5.新增Redis服务和AppToken
* v3.4.3 `20170525`
    * 1.更新部分SQL稳定性
    * 2.新增第三方支付和推送接口
* v3.4.2 `20170424`
    * 1.新增FastDFS文件存储
    * 2.模块分层，优化结构
    * 3.统一的异常处理
* v3.4.1 `20170324`
    * 1.新增地址和图片上传组件
    * 2.优化封装的JS插件
* v1.x `20160601`
    * JFrame项目Start
    
### 环境与配置
* Jdk 1.8
* MySQL 5.6 +
* Maven 3.3.9
* Redis 2.8.9
* Linux CentOS 7
* IDEA 2017+
---
* application-dev.yml 开发环境
* application-pro.yml 生产环境
* application-cloud.yml SpringCloud支持

### 运行
#### 打包到服务器运行
* 修改生产环境配置
* 在各个端下运行 `clean package -DskipTests`进行打包[必须先install父pom]
* 将target目录下的打包文件上传到服务器目录
* 运行`jframe/build/run`命令或直接运行`java -jar`命令
* 注意：运行脚本前，请先查看注释，需要按照指定的命名方式

#### Docker
* 直接运行打包命令，将上传到服务器目录，如:/opt/jframe/front
* 运行`docker-compose up -d`

### 其他
* 使用maven下载Jar包时，请先在`maven-.../conf/setting.xml`的mirrors标签内加入以下：
    ```
    <mirror>
        <id>alimaven</id>
        <name>aliyun maven</name>
        <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
        <mirrorOf>central</mirrorOf>
    </mirror>
    ```
* 数据库脚本：`jframe/doc/jframe.sql`
* 操作手册说明：`jframe/doc/系统架构.docx`

`*注：项目中的Demo可能需要去除相关注释后方可正常运行`