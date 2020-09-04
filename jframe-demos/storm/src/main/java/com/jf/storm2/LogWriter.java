package com.jf.storm2;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2020-08-31
 * Time: 14:24
 */
public class LogWriter extends BaseRichBolt {

    private FileWriter writer = null;


    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        try {
            // 结果写入到文件
            writer = new FileWriter("/home/opt/storm/result/" + this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(Tuple input) {
        try {
            writer.write(input.getStringByField("user") + " : " + input.getIntegerByField("pv"));
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    }

}
