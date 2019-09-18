package com.jf.http;

import com.jf.commons.LogManager;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.*;

/**
 * HttpUtil
 * <p>请使用 com.jf.http.HttpUtils</p>
 *
 * @author rick
 * @version 2.0
 */
@Deprecated
public class HttpUtil {

    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    // 请求和传输超时时间
    private final static int connectTimeout = 20000;
    private final static int socketTimeout = 10000;
    // 根据返回字符串判断异常
    public final static String STR_TIMEOUT = "timeout";
    public final static String STR_ERROR = "error";

    /**
     * 第三方API调用
     *
     * @param httpUrl 请求地址
     * @param httpArg 参数拼接串
     * @param apikey  apikey
     * @return
     */
    public static String apiRequest(String httpUrl, String httpArg, String apikey) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", apikey);
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

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
            log.error(e.getMessage(), e);
            if (e instanceof SocketTimeoutException) {
                result = STR_TIMEOUT;
            } else {
                result = STR_ERROR;
            }
        } finally {
            try {
                ((CloseableHttpClient) client).close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        LogManager.info(new StringBuilder().append("HttpClient: method 【GET】,url【").append(url).append("】").toString());
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
            Base64.Encoder encoder = Base64.getEncoder();
            get.setHeader("Authorization", "Basic " + encoder.encodeToString(namepwd.getBytes()));
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            if (e instanceof SocketTimeoutException) {
                result = STR_TIMEOUT;
            } else {
                result = STR_ERROR;
            }
        } finally {
            try {
                ((CloseableHttpClient) client).close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        LogManager.info(new StringBuilder().append("HttpClient: method 【GET】,url【").append(url).append("】").append(",auth【true】").toString());
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
            log.error(e.getMessage(), e);
            if (e instanceof SocketTimeoutException) {
                result = STR_TIMEOUT;
            } else {
                result = STR_ERROR;
            }
        } finally {
            try {
                ((CloseableHttpClient) client).close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        LogManager.info(new StringBuilder().append("HttpClient: method 【POST】,url【").append(url).append("】").toString());
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
            Base64.Encoder encoder = Base64.getEncoder();
            post.setHeader("Authorization", "Basic " + encoder.encodeToString(namepwd.getBytes()));
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
            log.error(e.getMessage(), e);
            if (e instanceof SocketTimeoutException) {
                result = STR_TIMEOUT;
            } else {
                result = STR_ERROR;
            }
        } finally {
            try {
                ((CloseableHttpClient) client).close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        LogManager.info(new StringBuilder().append("HttpClient: method 【POST】,url【").append(url).append("】").append(",auth【true】").toString());
        return result;
    }

    /**
     * post方式提交Http请求
     * 无参数名，只是参数内容(JSON)
     *
     * @param url
     * @param param JSON字符串
     * @return
     * @throws Exception
     */
    public static String postJson(String url, String param) {
        String result = "";
        HttpClient client = HttpClients.createDefault();
        try {
            HttpPost post = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
            post.setConfig(requestConfig);
            post.setHeader("Content-Type", "application/json");
            // 创建无参数名的参数
            StringEntity paramEntity = new StringEntity(param, "UTF-8");
            post.setEntity(paramEntity);
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "GB2312");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (e instanceof SocketTimeoutException) {
                result = STR_TIMEOUT;
            } else {
                result = STR_ERROR;
            }
        } finally {
            try {
                ((CloseableHttpClient) client).close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        LogManager.info(new StringBuilder().append("HttpClient: method 【POST】,url【").append(url).append("】").append(" ,json【").append(param).append("】").toString());
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