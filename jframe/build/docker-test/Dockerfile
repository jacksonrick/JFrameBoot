FROM hub.c.163.com/library/java:8u111-jre
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/Shanghai" > /etc/timezone
RUN java -version
COPY lib/ /data/wwwroot/test/lib
COPY jframe-web-test.jar /data/wwwroot/test/