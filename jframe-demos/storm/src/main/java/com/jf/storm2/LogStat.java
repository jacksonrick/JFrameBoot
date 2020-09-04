package com.jf.storm2;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2020-08-31
 * Time: 14:24
 */
public class LogStat extends BaseRichBolt {

    private OutputCollector _collector; //注意和SpoutOutputCollector区分
    private Map<String, Integer> _pvMap = new HashMap<String, Integer>();

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        _collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        String user = input.getStringByField("user");

        if (_pvMap.containsKey(user)) {
            _pvMap.put(user, _pvMap.get(user) + 1);
        } else {
            _pvMap.put(user, 1);
        }
        //把每个用户的最新PV输出到下一节点
        _collector.emit(new Values(user, _pvMap.get(user)));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("user", "pv"));
    }

}
