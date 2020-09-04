package com.jf.storm3;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 用户支付记录
 * User: xujunfei
 * Date: 2020-08-31
 * Time: 15:06
 */
public class PaySpout extends BaseRichSpout {

    private SpoutOutputCollector _collector;
    private String[] _users = {"userA", "userB", "userC", "userD", "userE"};
    private String[] _pays = {"100", "200", "300", "400", "200"};
    private int count = 5;

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        _collector = collector;
    }

    @Override
    public void nextTuple() {
        for (int i = 0; i < count; i++) {
            try {
                Thread.sleep(1500);//停顿时间长一些，使得流量日志先于成交日志到达下游的LogMergeBolt组件
                _collector.emit("business", new Values(System.currentTimeMillis(), _users[i], _pays[i]));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declareStream("business", new Fields("time", "user", "pay"));
    }

}
