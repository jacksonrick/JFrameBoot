package com.jf;

import com.jf.storm2.LogWriter;
import com.jf.storm3.LogMergeBolt;
import com.jf.storm3.LogStatBolt;
import com.jf.storm3.PaySpout;
import com.jf.storm3.VisitSpout;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * Created with IntelliJ IDEA.
 * Description: 模拟一个简化的电子商务网站来实时计算来源成交效果
 * User: xujunfei
 * Date: 2020-08-31
 * Time: 15:02
 */
public class App3 {

    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("log-vspout", new VisitSpout(), 1);
        builder.setSpout("log-pspout", new PaySpout(), 1);

        //定义两个流名称，分别为visit和business
        builder.setBolt("log-merge", new LogMergeBolt(), 2).fieldsGrouping("log-vspout",
                "visit", new Fields("user")).fieldsGrouping("log-pspout", "business", new Fields("user"));
        builder.setBolt("log-stat", new LogStatBolt(), 2).fieldsGrouping("log-merge", new Fields("srcid"));
        builder.setBolt("log-writer", new LogWriter()).globalGrouping("log-stat");

        Config conf = new Config();
        //实时计算不需要可靠消息，故关闭acker节省通信资源
        conf.setNumAckers(0);
        //设置独立java进程数，一般设置为同spout和bolt的总tasks数量相等或更多，使得每个task
        //都运行在独立的Java进程中，以避免多个task集中在一个jvm里运行产生GC(垃圾回收机制)瓶颈
        conf.setNumWorkers(8);
        StormSubmitter.submitTopology("log-topology", conf, builder.createTopology());
    }

}
