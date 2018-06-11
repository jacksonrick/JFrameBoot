package com.jf.cloud.common;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Http get/post
 *
 * @author rick
 */
public class HttpUtil {

    // 请求和传输超时时间
    private final static int connectTimeout = 5000;
    private final static int socketTimeout = 5000;

    /**
     * get方式提交Http请求
     *
     * @param url
     * @return
     */
    public static String get(String url) {
        String result = "";
        HttpClient client = HttpClients.createDefault();
        try {
            HttpGet get = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
            get.setConfig(requestConfig);
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = "timeout";
        } finally {
            try {
                ((CloseableHttpClient) client).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.info(new StringBuilder().append("HttpClient: method 【GET】,url【").append(url).append("】").toString());
        return result;
    }

    /**
     * get方式提交Http请求
     * Authorization
     *
     * @param url
     * @return
     */
    public static String getWithAuthorization(String url, String namepwd) {
        String result = "";
        HttpClient client = HttpClients.createDefault();
        try {
            HttpGet get = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
            get.setConfig(requestConfig);
            get.setHeader("Authorization", "Basic " + new BASE64Encoder().encode(namepwd.getBytes()));
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = "timeout";
        } finally {
            try {
                ((CloseableHttpClient) client).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.info(new StringBuilder().append("HttpClient: method 【GET】,url【").append(url).append("】").append(",auth【true】").toString());
        return result;
    }

    /**
     * post方式提交Http请求
     *
     * @param url
     * @param paramsMap
     * @return
     */
    public static String post(String url, Map<String, String> paramsMap) {
        String result = "";
        HttpClient client = HttpClients.createDefault();
        try {
            HttpPost post = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
            post.setConfig(requestConfig);
            // 创建参数队列
            List<NameValuePair> formparams = getParamsList(paramsMap);
            if (formparams != null) {
                UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
                post.setEntity(uefEntity);
            }
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "GB2312");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "timeout";
        } finally {
            try {
                ((CloseableHttpClient) client).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.info(new StringBuilder().append("HttpClient: method 【POST】,url【").append(url).append("】").toString());
        return result;
    }

    /**
     * post方式提交Http请求
     * Authorization
     *
     * @param url
     * @param paramsMap
     * @return
     */
    public static String postWithAuthorization(String url, Map<String, String> paramsMap, String namepwd) {
        String result = "";
        HttpClient client = HttpClients.createDefault();
        try {
            HttpPost post = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
            post.setConfig(requestConfig);
            post.setHeader("Authorization", "Basic " + new BASE64Encoder().encode(namepwd.getBytes()));
            // 创建参数队列
            List<NameValuePair> formparams = getParamsList(paramsMap);
            if (formparams != null) {
                UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
                post.setEntity(uefEntity);
            }
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "GB2312");
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = "timeout";
        } finally {
            try {
                ((CloseableHttpClient) client).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.info(new StringBuilder().append("HttpClient: method 【POST】,url【").append(url).append("】").append(",auth【true】").toString());
        return result;
    }

    /**
     * post方式提交Http请求
     * 无参数名，只是参数内容
     *
     * @param url
     * @param param 无参数名的参数
     * @return
     * @throws Exception
     */
    public static String post(String url, String param) throws Exception {
        String result = "";
        HttpClient client = HttpClients.createDefault();
        try {
            HttpPost post = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
            post.setConfig(requestConfig);
            // 创建无参数名的参数
            StringEntity paramEntity = new StringEntity(param); // 无参数名，只是参数内容
            post.setEntity(paramEntity);
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "GB2312");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "timeout";
        } finally {
            try {
                ((CloseableHttpClient) client).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static List<NameValuePair> getParamsList(Map paramsMap) {
        if (paramsMap == null || paramsMap.size() == 0) {
            return null;
        }
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        Set<Map.Entry> set = paramsMap.entrySet();
        for (Map.Entry<String, String> entry : set) {
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return list;
    }

}