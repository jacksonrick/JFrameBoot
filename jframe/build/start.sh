# 注：该文件不能直接执行

## 启动命令
nohup java [-server [-jvm参数] -jar xxx.jar --spring.profiles.active=mgm-pro >/dev/null 2>&1 &


# 启动脚本
pid() {
    echo `jps -l | grep xxx.jar | tr -s " " | cut -d" " -f1`
}
id=$(pid)
echo "PID: ${id}"
if [ -n "$id" ];then
  kill $id
  sleep 5
fi
nohup java -jar xxx.jar --spring.profiles.active=mgm-pro >/dev/null 2>&1 &


# 用于jvisualvm监控
java -Dcom.sun.management.jmxremote.port=18888 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Djava.rmi.server.hostname=192.168.40.200 -jar xxx.jar
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar xxx.jar


# JVM参数
java -Xms2048m -Xmx2048m -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=256m -jar xxx.jar

# 自启动
#sleep 30
su - user -c /home/user/start.sh   -->    /etc/rc.d/rc.local
chmod +x /etc/rc.d/rc.local
