FROM hub.c.163.com/library/java:8u111-jre
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/Shanghai" > /etc/timezone
RUN java -version
COPY lib/ /data/wwwroot/app/lib
COPY jframe-web-app.jar /data/wwwroot/app/