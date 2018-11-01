package com.jf.system.third.notify;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-10-23
 * Time: 10:36
 */
public class BearychatNotify {

    private final static String hook = "https://hook.bearychat.com/=bwDdY/incoming/30b7bfb5932670c25b5e61a51875a53e";

    /**
     * 发送通知
     *
     * @param system 系统名称
     * @param detail 详细，支持markdown
     * @return
     */
    public static int sendMessage(String system, String detail) {
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(hook);

        JSONObject object = new JSONObject();
        object.put("text", system);
        object.put("markdown", true);

        Map map = new HashMap();
        map.put("text", detail);
        map.put("color", "#ffa500");
        JSONArray array = new JSONArray();
        array.add(map);
        object.put("attachments", array);

        try {
            post.setRequestEntity(new StringRequestEntity(object.toString(), "application/json", "UTF-8"));
            return client.executeMethod(post);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            post.releaseConnection();
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(sendMessage("测试", "详细"));
    }

}
