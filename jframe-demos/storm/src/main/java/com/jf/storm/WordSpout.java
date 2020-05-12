package com.jf.storm;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 数据源
 * User: xujunfei
 * Date: 2019-11-08
 * Time: 14:36
 */
public class WordSpout implements IRichSpout {

    private SpoutOutputCollector collector;
    private int index = 0;
    private final String[] lines = {
            "long long ago I like playing with cat",
            "playing with cat make me happy",
            "I feel happy to be with you",
            "you give me courage",
            "I like to be together with you",
            "long long ago I like you"
    };

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
    }

    @Override
    public void close() {

    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void nextTuple() {
        System.out.printf("\n########## 第 %d 次发送 ######\n", index);
        this.collector.emit(new Values(lines[index]));
        index++;
        if (index >= lines.length) {
            index = 0;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ack(Object o) {

    }

    @Override
    public void fail(Object o) {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("line"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
