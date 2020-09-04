package com.jf;

import com.jf.storm2.LogReader;
import com.jf.storm2.LogStat;
import com.jf.storm2.LogWriter;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * Created with IntelliJ IDEA.
 * Description: 模拟网站计算用户PV(页面浏览量)
 * User: xujunfei
 * Date: 2020-08-31
 * Time: 14:21
 */
public class App2 {

    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("log-reader", new LogReader(), 1);
        builder.setBolt("log-stat", new LogStat(), 2).fieldsGrouping("log-reader", new Fields("user"));
        builder.setBolt("log-writer", new LogWriter(), 1).shuffleGrouping("log-stat");

        Config config = new Config();
        config.setNumWorkers(5);
        StormSubmitter.submitTopology("log-topology", config, builder.createTopology());
    }
}
