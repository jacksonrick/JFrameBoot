package com.jf.storm3;

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
 * Time: 15:08
 */
public class LogStatBolt extends BaseRichBolt {

    private OutputCollector _collector;
    private HashMap<String, Long> srcpay;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        _collector = collector;
        if (srcpay == null) {
            srcpay = new HashMap<String, Long>();
        }
    }

    @Override
    public void execute(Tuple input) {
        String pay = input.getStringByField("pay");
        String srcid = input.getStringByField("srcid");

        if (srcpay.containsKey(srcid)) {
            srcpay.put(srcid, srcpay.get(srcid) + Long.parseLong(pay.trim()));
        } else {
            srcpay.put(srcid, Long.parseLong(pay.trim()));
        }
        _collector.emit(new Values(srcid, srcpay.get(srcid)));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("srcid", "pay"));
    }
}
