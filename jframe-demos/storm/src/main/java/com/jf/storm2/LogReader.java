package com.jf.storm2;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2020-08-31
 * Time: 14:23
 */
public class LogReader extends BaseRichSpout {

    private SpoutOutputCollector _collector;
    private Random _rand = new Random();
    private int _count = 100;
    private String[] _users = {"userA", "userB", "userC", "userD", "userE"};
    private String[] _urls = {"url1", "url2", "url3", "url4", "url5"};

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        _collector = collector;
    }

    @Override
    public void nextTuple() {
        try {
            Thread.sleep(1000);
            // 随发发生
            while (_count-- >= 0) {
                _collector.emit(new Values(System.currentTimeMillis(), _users[_rand.nextInt(5)], _urls[_rand.nextInt(5)]));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("time", "user", "url"));
    }

}
