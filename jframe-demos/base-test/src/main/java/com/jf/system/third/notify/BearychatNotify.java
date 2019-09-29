package com.jf.system.third.notify;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 倍恰通知
 * User: xujunfei
 * Date: 2019-05-31
 * Time: 16:49
 */
public class BearychatNotify {

    private final static String hook = "https://hook.bearychat.com/=bwDdY/incoming/xxxxxxxxxxxxx";

    public final static String SYS_NAME = "xx模块";

    /**
     * 发送通知
     *
     * @param system 系统名称或模块名称
     * @param detail 详细内容，支持markdown
     */
    public static void sendMessage(String system, String detail) {
        JSONObject object = new JSONObject();
        object.put("text", system);
        object.put("markdown", true);
        Map map = new HashMap();
        map.put("text", detail);
        map.put("color", "#ffa500");
        JSONArray array = new JSONArray();
        array.add(map);
        object.put("attachments", array);

        CloseableHttpClient client = HttpClients.createDefault();
        try {
            HttpPost post = new HttpPost(hook);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(2000).setSocketTimeout(2000).build();
            post.setConfig(requestConfig);
            post.setHeader("Content-Type", "application/json");
            StringEntity paramEntity = new StringEntity(object.toString(), "UTF-8");
            post.setEntity(paramEntity);
            client.execute(post);
            /*HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "GB2312");
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            /*if (e instanceof SocketTimeoutException) {
                result = "timeout";
            } else {
                result = "error";
            }*/
        } finally {
            try {
                ((CloseableHttpClient) client).close();
            } catch (IOException var16) {
                var16.printStackTrace();
            }
        }
    }

}
