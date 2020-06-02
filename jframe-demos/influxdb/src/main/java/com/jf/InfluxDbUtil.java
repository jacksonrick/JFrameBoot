package com.jf;

import com.jf.metrics.CPUInfo;
import com.jf.metrics.Label;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-11-12
 * Time: 13:56
 */
public class InfluxDbUtil {

    private final static String username = "root";
    private final static String password = "12345678";
    private final static String url = "http://127.0.0.1:8086";
    private final static String database = "location";

    private static InfluxDB influxDB;

    static {
        influxDB = InfluxDBFactory.connect(url, username, password);
        influxDB.setDatabase(database);
        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
    }

    static Random random = new Random();

    // 写入CPU监控数据
    public static void write() {
        CPUInfo info = new CPUInfo();
        info.setDevice("s01");
        int val = random.nextInt(100);
        System.out.println("val:" + val);
        info.setValue(val);
        // 实体类注入
        Point point = Point.measurementByPOJO(CPUInfo.class)
                .addFieldsFromPOJO(info)
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .build();
        influxDB.write(point);
    }

    // 写入标签定位数据
    public static void write2() {
        // 自定义字段
        Point point = Point.measurement("L01")
                .time(1590309422l, TimeUnit.SECONDS) // 指定时间
                .addField("x", 1.2)
                .addField("y", 3.4)
                .addField("z", 2.5)
                .tag("m", "1") // 标签=索引
                .build();
        influxDB.setRetentionPolicy("mypolicy").write(point); //如果设置了过期策略，需要指定
    }

    // 读取
    public static void read() {
        Query query = new Query("SELECT * FROM L01", database);
        QueryResult queryResult = influxDB.query(query, TimeUnit.SECONDS); // 按照指定的时间格式转换
        System.out.println(queryResult);

        List<QueryResult.Result> resultList = queryResult.getResults();
        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper(); //将查询结果映射到实体类中，指定表名和时间单位（该类是线程安全的）
        List<Label> labels = resultMapper.toPOJO(queryResult, Label.class, "L01", TimeUnit.SECONDS);
        System.out.println(labels);
    }

    public static void main(String[] args) {
//        write();
        write2();
//        read();
    }

}
