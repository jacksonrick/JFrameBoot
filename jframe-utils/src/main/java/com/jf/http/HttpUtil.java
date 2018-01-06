package com.jf.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * Http get/post
 * @author rick
 */
public class HttpUtil {

	public static void main(String[] args) throws Exception {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("", "");
		paramsMap.put("", "");
		System.out.println(HttpUtil.postMethod("http://localhost:8080/get", paramsMap));
		// System.out.println(HttpUtil.getMethod("http://localhost:8080/post?code=17730215423&uname=feifei"));
	}

	/**
	 * 第三方API调用
	 * 
	 * @param httpUrl 请求地址
	 * @param httpArg 参数拼接串
	 * @param apikey apikey
	 * @return
	 */
	public static String request(String httpUrl, String httpArg, String apikey) {
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
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * get方式提交Http请求
	 * 
	 * @param url
	 * @return
	 */
	public static String getMethod(String url) {
		String result = "";
		HttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * post方式提交Http请求
	 * 
	 * @param url 请求地址格式"http://localhost/Test";
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public static String postMethod(String url, Map<String, String> paramsMap) throws Exception {
		String result = "";
		HttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
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
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<NameValuePair> getParamsList(Map paramsMap) {
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