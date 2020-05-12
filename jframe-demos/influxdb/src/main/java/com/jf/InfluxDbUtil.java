package com.jf;

import com.jf.metrics.CPUInfo;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

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
    private final static String url = "http://192.168.24.200:8086";
    private final static String database = "mydata";

    private static InfluxDB influxDB;

    static {
        influxDB = InfluxDBFactory.connect(url, username, password);
        influxDB.setDatabase(database);
        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
    }

    static Random random = new Random();

    public static void write() {
        CPUInfo info = new CPUInfo();
        info.setDevice("s01");
        int val = random.nextInt(100);
        System.out.println("val:" + val);
        info.setValue(val);
        Point point = Point.measurementByPOJO(CPUInfo.class)
                .addFieldsFromPOJO(info)
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .build();
        influxDB.write(point);
    }

}
