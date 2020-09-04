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
 * Time: 15:07
 */
public class LogMergeBolt extends BaseRichBolt {

    private OutputCollector _collector;
    // 暂时存储用户的访问日志记录
    private HashMap<String, String> srcMap;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        _collector = collector;
        if (srcMap == null) {
            srcMap = new HashMap<String, String>();
        }
    }

    @Override
    public void execute(Tuple input) {
        String streamID = input.getSourceStreamId();
        if (streamID.equals("visit")) {
            String user = input.getStringByField("user");
            String srcid = input.getStringByField("srcid");
            srcMap.put(user, srcid);
        }
        if (streamID.equals("business")) {
            String user = input.getStringByField("user");
            String pay = input.getStringByField("pay");
            String srcid = srcMap.get(user);

            if (srcid != null) {
                _collector.emit(new Values(user, pay, srcid));
                srcMap.remove(user);
            } else {
                //一般只有成交日志先于流量日志才会发生
            }
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("user", "srcid", "pay"));
    }

}