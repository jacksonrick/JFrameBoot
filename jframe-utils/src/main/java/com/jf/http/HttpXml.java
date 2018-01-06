package com.jf.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by xujunfei on 2017/2/10.
 */
public class HttpXml {

    /**
     * 发送参数为xml的请求
     * @param xml
     * @param url
     * @return
     */
    public static String getXml(String xml, String url) {
        String content = xml;
        //创建httpclient工具对象
        HttpClient client = new HttpClient();
        //创建post请求方法
        PostMethod myPost = new PostMethod(url);
        //设置请求超时时间
        client.setConnectionTimeout(300 * 1000);
        String responseString = null;
        try {
            //设置请求头部类型
            myPost.setRequestHeader("Content-Type", "text/xml");
            myPost.setRequestHeader("charset", "utf-8");
            myPost.setRequestBody(content);
            int statusCode = client.executeMethod(myPost);
            if (statusCode == 200) {
                BufferedInputStream bis = new BufferedInputStream(myPost.getResponseBodyAsStream());
                byte[] bytes = new byte[1024];
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int count = 0;
                while ((count = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, count);
                }
                byte[] strByte = bos.toByteArray();
                responseString = new String(strByte, 0, strByte.length, "gb2312");
                bos.close();
                bis.close();

                System.out.println("\nresponseString:" + responseString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseString;
    }

}
