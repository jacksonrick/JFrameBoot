package com.wechat.util;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Created on 16/4/22.
 */
public class HttpUtils {
    private String defaultContentEncoding;

    public HttpUtils() {
        this.defaultContentEncoding = Charset.defaultCharset().name();
    }

//    /**
//     * 发送GET请求
//     *
//     * @param urlString  URL地址
//     * @return 响应对象
//     * @throws IOException
//     */
//    public HttpRespons sendGet(String urlString) throws IOException {
//        return this.send(urlString, "GET", null, null);
//    }
//
//    /**
//     * 发送GET请求
//     *
//     * @param urlString  URL地址
//     * @param params 参数集合
//     * @return 响应对象
//     * @throws IOException
//     */
//    public HttpRespons sendGet(String urlString, Map<String, String> params) throws IOException {
//        return this.send(urlString, "GET", params, null);
//    }
//
//    /**
//     * 发送GET请求
//     *
//     * @param urlString URL地址
//     * @param params 参数集合
//     * @param propertys 请求属性
//     * @return 响应对象
//     * @throws IOException
//     */
//    public HttpRespons sendGet(String urlString, Map<String, String> params, Map<String, String> propertys)
//            throws IOException {
//        return this.send(urlString, "GET", params, propertys);
//    }
//
//    /**
//     * 发送POST请求
//     *
//     * @param urlString URL地址
//     * @return 响应对象
//     * @throws IOException
//     */
//    public HttpRespons sendPost(String urlString) throws IOException {
//        return this.send(urlString, "POST", null, null);
//    }
//
//    /**
//     * 发送POST请求
//     *
//     * @param urlString URL地址
//     * @param params 参数集合
//     * @return 响应对象
//     * @throws IOException
//     */
//    public HttpRespons sendPost(String urlString, Map<String, String> params) throws IOException {
//        return this.send(urlString, "POST", params, null);
//    }
//
//    /**
//     * 发送POST请求
//     *
//     * @param urlString URL地址
//     * @param params 参数集合
//     * @param propertys 请求属性
//     * @return 响应对象
//     * @throws IOException
//     */
//    public HttpRespons sendPost(String urlString, Map<String, String> params, Map<String, String> propertys)
//            throws IOException {
//        return this.send(urlString, "POST", params, propertys);
//    }
//
//    /**
//     * 发送HTTP请求
//     *
//     * @param urlString 地址
//     * @param method  get/post
//     * @param parameters  添加由键值对指定的请求参数
//     * @param propertys  添加由键值对指定的一般请求属性
//     * @return 响映对象
//     * @throws IOException
//     */
//    private HttpRespons send(String urlString, String method, Map<String, String> parameters,
//                             Map<String, String> propertys) throws IOException {
//        HttpURLConnection urlConnection = null;
//
//        if (method.equalsIgnoreCase("GET") && parameters != null) {
//            StringBuffer param = new StringBuffer();
//            int i = 0;
//            for (String key : parameters.keySet()) {
//                if (i == 0)
//                    param.append("?");
//                else
//                    param.append("&");
//                param.append(key).append("=").append(parameters.get(key));
//                i++;
//            }
//            urlString += param;
//        }
//
//        URL url = new URL(urlString);
//        urlConnection = (HttpURLConnection) url.openConnection();
//        urlConnection.setRequestMethod(method);
//        urlConnection.setDoOutput(true);
//        urlConnection.setDoInput(true);
//        urlConnection.setUseCaches(false);
//
//        if (propertys != null)
//            for (String key : propertys.keySet()) {
//                urlConnection.addRequestProperty(key, propertys.get(key));
//            }
//
//        if (method.equalsIgnoreCase("POST") && parameters != null) {
//            StringBuffer param = new StringBuffer();
//            for (String key : parameters.keySet()) {
//                param.append("&");
//                param.append(key).append("=").append(parameters.get(key));
//            }
//            urlConnection.getOutputStream().write(param.toString().getBytes());
//            urlConnection.getOutputStream().flush();
//            urlConnection.getOutputStream().close();
//        }
//        return this.makeContent(urlString, urlConnection);
//    }
//
//    /**
//     * 得到响应对象
//     *
//     * @param urlConnection
//     * @return 响应对象
//     * @throws IOException
//     */
//    private HttpRespons makeContent(String urlString, HttpURLConnection urlConnection) throws IOException {
//        HttpRespons httpResponser = new HttpRespons();
//        try {
//            InputStream in = urlConnection.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
//            httpResponser.contentCollection = new Vector<String>();
//            StringBuffer temp = new StringBuffer();
//            String line = bufferedReader.readLine();
//            while (line != null) {
//                httpResponser.contentCollection.add(line);
//                temp.append(line).append("\r\n");
//                line = bufferedReader.readLine();
//            }
//            bufferedReader.close();
//            String ecod = urlConnection.getContentEncoding();
//            if (ecod == null)
//                ecod = this.defaultContentEncoding;
//            httpResponser.urlString = urlString;
//            httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();
//            httpResponser.file = urlConnection.getURL().getFile();
//            httpResponser.host = urlConnection.getURL().getHost();
//            httpResponser.path = urlConnection.getURL().getPath();
//            httpResponser.port = urlConnection.getURL().getPort();
//            httpResponser.protocol = urlConnection.getURL().getProtocol();
//            httpResponser.query = urlConnection.getURL().getQuery();
//            httpResponser.ref = urlConnection.getURL().getRef();
//            httpResponser.userInfo = urlConnection.getURL().getUserInfo();
//            httpResponser.content = new String(temp.toString().getBytes(), ecod);
//            httpResponser.contentEncoding = ecod;
//            httpResponser.code = urlConnection.getResponseCode();
//            httpResponser.message = urlConnection.getResponseMessage();
//            httpResponser.contentType = urlConnection.getContentType();
//            httpResponser.method = urlConnection.getRequestMethod();
//            httpResponser.connectTimeout = urlConnection.getConnectTimeout();
//            httpResponser.readTimeout = urlConnection.getReadTimeout();
//            return httpResponser;
//        } catch (IOException e) {
//            throw e;
//        } finally {
//            if (urlConnection != null)
//                urlConnection.disconnect();
//        }
//    }

    /**
     * 默认的响应字符集
     */
    public String getDefaultContentEncoding() {
        return this.defaultContentEncoding;
    }


    /**
     * 发送GET请求
     *
     * @param url
     * @param params
     * @param headers
     * @return
     * @throws Exception
     */
    public static URLConnection sendGetRequest(String url,
                                               Map<String, String> params, Map<String, String> headers)
            throws Exception {
        StringBuilder buf = new StringBuilder(url);
        Set<Entry<String, String>> entrys = null;
        // 如果是GET请求，则请求参数在URL中
        if (params != null && !params.isEmpty()) {
            buf.append("?");
            entrys = params.entrySet();
            for (Entry<String, String> entry : entrys) {
                buf.append(entry.getKey()).append("=")
                        .append(URLEncoder.encode(entry.getValue(), "gb2312"))
                        .append("&");
            }
            buf.deleteCharAt(buf.length() - 1);
        }
        URL url1 = new URL(buf.toString());
        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
        conn.setRequestMethod("GET");
        // 设置请求头
        if (headers != null && !headers.isEmpty()) {
            entrys = headers.entrySet();
            for (Entry<String, String> entry : entrys) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        conn.getResponseCode();
        return conn;
    }

    /**
     * 发送POST请求
     *
     * @param url
     * @param params
     * @param headers
     * @return
     * @throws Exception
     */
    public static URLConnection sendPostRequest(String url,
                                                Map<String, String> params, Map<String, String> headers)
            throws Exception {
        StringBuilder buf = new StringBuilder();
        Set<Entry<String, String>> entrys = null;
        // 如果存在参数，则放在HTTP请求体，形如name=aaa&age=10
        if (params != null && !params.isEmpty()) {
            entrys = params.entrySet();
            for (Entry<String, String> entry : entrys) {
                buf.append(entry.getKey()).append("=")
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"))
                        .append("&");
            }
            buf.deleteCharAt(buf.length() - 1);
        }
        URL url1 = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        OutputStream out = conn.getOutputStream();
        out.write(buf.toString().getBytes("UTF-8"));
        if (headers != null && !headers.isEmpty()) {
            entrys = headers.entrySet();
            for (Entry<String, String> entry : entrys) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        conn.getResponseCode(); // 为了发送成功
        return conn;
    }

    /**
     * 直接通过HTTP协议提交数据到服务器,实现如下面表单提交功能:
     * <FORM METHOD=POST ACTION="http://192.168.0.200:8080/ssi/fileload/test.do" enctype="multipart/form-data">
     * <INPUT TYPE="text" NAME="name">
     * <INPUT TYPE="text" NAME="id">
     * <input type="file" name="imagefile"/>
     * <input type="file" name="zip"/>
     * </FORM>
     *
     * @param path   上传路径(注：避免使用localhost或127.0.0.1这样的路径测试，因为它会指向手机模拟器，你可以使用http://www.itcast.cn或http://192.168.1
     *               .10:8080这样的路径测试)
     * @param params 请求参数 key为参数名,value为参数值
     * @param files  上传文件
     */
    public static boolean uploadFiles(String path, Map<String, String> params, FormFile[] files) throws Exception {
        final String BOUNDARY = "---------------------------7da2137580612"; //数据分隔线
        final String endline = "--" + BOUNDARY + "--\r\n";//数据结束标志

        int fileDataLength = 0;
        if (files != null && files.length != 0) {
            for (FormFile uploadFile : files) {//得到文件类型数据的总长度
                StringBuilder fileExplain = new StringBuilder();
                fileExplain.append("--");
                fileExplain.append(BOUNDARY);
                fileExplain.append("\r\n");
                fileExplain.append("Content-Disposition: form-data;name=\"" + uploadFile.getParameterName() + "\";" +
                        "filename=\"" + uploadFile.getFilname() + "\"\r\n");
                fileExplain.append("Content-Type: " + uploadFile.getContentType() + "\r\n\r\n");
                fileExplain.append("\r\n");
                fileDataLength += fileExplain.length();
                if (uploadFile.getInStream() != null) {
                    fileDataLength += uploadFile.getFile().length();
                } else {
                    fileDataLength += uploadFile.getData().length;
                }
            }
        }
        StringBuilder textEntity = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            for (Entry<String, String> entry : params.entrySet()) {//构造文本类型参数的实体数据
                textEntity.append("--");
                textEntity.append(BOUNDARY);
                textEntity.append("\r\n");
                textEntity.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
                textEntity.append(entry.getValue());
                textEntity.append("\r\n");
            }
        }
        //计算传输给服务器的实体数据总长度
        int dataLength = textEntity.toString().getBytes().length + fileDataLength + endline.getBytes().length;

        URL url = new URL(path);
        int port = url.getPort() == -1 ? 80 : url.getPort();
        Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
        OutputStream outStream = socket.getOutputStream();
        //下面完成HTTP请求头的发送
        String requestmethod = "POST " + url.getPath() + " HTTP/1.1\r\n";
        outStream.write(requestmethod.getBytes());
        String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, " +
                "application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, " +
                "application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, " +
                "application/msword, */*\r\n";
        outStream.write(accept.getBytes());
        String language = "Accept-Language: zh-CN\r\n";
        outStream.write(language.getBytes());
        String contenttype = "Content-Type: multipart/form-data; boundary=" + BOUNDARY + "\r\n";
        outStream.write(contenttype.getBytes());
        String contentlength = "Content-Length: " + dataLength + "\r\n";
        outStream.write(contentlength.getBytes());
        String alive = "Connection: Keep-Alive\r\n";
        outStream.write(alive.getBytes());
        String host = "Host: " + url.getHost() + ":" + port + "\r\n";
        outStream.write(host.getBytes());
        //写完HTTP请求头后根据HTTP协议再写一个回车换行
        outStream.write("\r\n".getBytes());
        //把所有文本类型的实体数据发送出来
        outStream.write(textEntity.toString().getBytes());
        //把所有文件类型的实体数据发送出来
        if (files != null && files.length != 0) {
            for (FormFile uploadFile : files) {
                StringBuilder fileEntity = new StringBuilder();
                fileEntity.append("--");
                fileEntity.append(BOUNDARY);
                fileEntity.append("\r\n");
                fileEntity.append("Content-Disposition: form-data;name=\"" + uploadFile.getParameterName() + "\";" +
                        "filename=\"" + uploadFile.getFilname() + "\"\r\n");
                fileEntity.append("Content-Type: " + uploadFile.getContentType() + "\r\n\r\n");
                outStream.write(fileEntity.toString().getBytes());
                if (uploadFile.getInStream() != null) {
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = uploadFile.getInStream().read(buffer, 0, 1024)) != -1) {
                        outStream.write(buffer, 0, len);
                    }
                    uploadFile.getInStream().close();
                } else {
                    outStream.write(uploadFile.getData(), 0, uploadFile.getData().length);
                }
                outStream.write("\r\n".getBytes());
            }
        }
        //下面发送数据结束标志，表示数据已经结束
        outStream.write(endline.getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        if (reader.readLine().indexOf("200") == -1) {//读取web服务器返回的数据，判断请求码是否为200，如果不是200，代表请求失败
            return false;
        }
        outStream.flush();
        outStream.close();
        reader.close();
        socket.close();
        return true;
    }

    /**
     * 提交数据到服务器
     *
     * @param path   上传路径(注：避免使用localhost或127.0.0.1这样的路径测试，因为它会指向手机模拟器，你可以使用http://www.itcast.cn或http://192.168.1
     *               .10:8080这样的路径测试)
     * @param params 请求参数 key为参数名,value为参数值
     * @param file   上传文件
     */
    public static boolean uploadFile(String path, Map<String, String> params, FormFile file) throws Exception {
        return uploadFiles(path, params, new FormFile[]{file});
    }

    /**
     * 将输入流转为字节数组
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] read2Byte(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }

    /**
     * 将输入流转为字符串
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static String read2String(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return new String(outSteam.toByteArray(), "UTF-8");
    }


    /**
     * 发送xml数据
     *
     * @param path     请求地址
     * @param xml      xml数据
     * @param encoding 编码
     * @return
     * @throws Exception
     */
    public static byte[] postXml(String path, String xml, String encoding) throws Exception {
        byte[] data = xml.getBytes(encoding);
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "text/xml; charset=" + encoding);
        conn.setRequestProperty("Content-Length", String.valueOf(data.length));
        conn.setConnectTimeout(5 * 1000);
        OutputStream outStream = conn.getOutputStream();
        outStream.write(data);
        outStream.flush();
        outStream.close();
        if (conn.getResponseCode() == 200) {
            return read2Byte(conn.getInputStream());
        }
        return null;
    }

    //测试函数
    public static void main(String args[]) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "xiazdong");
        params.put("age", "10");
        HttpURLConnection conn = (HttpURLConnection)
                sendPostRequest(
                        "http://211.149.161.183/app/getBannerImage",
                        params, null);
        int code = conn.getResponseCode();
        InputStream in = conn.getInputStream();
        String data = read2String(in);
        System.out.print(data);
    }

    /**
     * 设置默认的响应字符集
     */
    public void setDefaultContentEncoding(String defaultContentEncoding) {
        this.defaultContentEncoding = defaultContentEncoding;
    }


    /**
     * @param @return
     * @return String
     * @throws
     * @Title: getIpAddr
     * @author kaka  www.zuidaima.com
     * @Description: 获取客户端IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

}
