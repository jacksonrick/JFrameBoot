package com.jf.storm3;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2020-08-31
 * Time: 15:08
 */
public class LogWriter extends BaseRichBolt {

    private HashMap<String, Long> counts = null;
    private FileWriter writer = null;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.counts = new HashMap<String, Long>();
        try {
            writer = new FileWriter("/home/opt/storm/result/" + this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(Tuple input) {
        String srcid = input.getStringByField("srcid");
        Long pay = input.getLongByField("pay");
        counts.put(srcid, pay);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

    @Override
    public void cleanup() {
        Iterator<Map.Entry<String, Long>> it = counts.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, Long> entry = it.next();
            try {
                writer.write(entry.getKey() + " : " + entry.getValue());
                writer.write("\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}