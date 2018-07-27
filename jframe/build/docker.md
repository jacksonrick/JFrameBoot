# docker部署说明

* 工作目录：`/home/project/`
* Jar包目录：`/data/wwwroot/`
* 日志目录：`/data/wwwlogs/`

**动静分离**
* 启动Jar：`/data/wwwroot/test/test.jar`
* 静态资源：`/data/wwwroot/test/static/`
* 依赖包：`/data/wwwroot/test/lib/`

**服务相关**  
参阅：`docker-app/docker-compose.yml`