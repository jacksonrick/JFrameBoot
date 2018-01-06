package com.jf.http;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Http协议连接工具
 */
public class HttpConnector {
    /**
     * 连接地址
     */
    private String url;

    /**
     * 传入参数<br/>
     * 例如：param1=123&param2=456
     */
    private String paramStr;

    /**
     * 编码格式
     */
    private String encode = "GBK";

    /**
     * 连接访问地址超时时间
     */
    private int connectTimeout;

    /**
     * 读取返回信息超时时间
     */
    private int readTimeout;

    /**
     * 提交方式(POST 或者 GET)
     */
    private String method;

    /**
     * Cookie 信息
     */
    private String cookie;

    /**
     * 访问地址回去信息的输入流<br/>
     * 如果输入流还没有关就再次读取数据，则在读取数据前将自动关闭输入流
     */
    private InputStream inputStream;

    /**
     * 连接
     */
    private HttpURLConnection conn = null;

    /**
     * 连接网址，并获取返回信息的输入流
     */
    public void connectUrl() {

        try {
            URL realURL = new URL(url);

            if (conn != null) {
                closeConnect();
            }
            if ("https".equalsIgnoreCase(realURL.getProtocol())) {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new TrustManager[]{new X509TrustManager() {

                    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    }

                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }}, new SecureRandom());
                conn = (HttpURLConnection) realURL.openConnection();
                ((HttpsURLConnection) conn).setSSLSocketFactory(sslContext.getSocketFactory());
                ((HttpsURLConnection) conn).setHostnameVerifier(new HostnameVerifier() {

                    public boolean verify(String arg0, SSLSession arg1) {
                        return true;
                    }
                });
            } else {
                conn = (HttpURLConnection) realURL.openConnection();
            }

            setConnection();

            synchronous();

            conn.connect();

            //如果当前输入流不为空，则先关闭输入流
            if (this.inputStream != null) {
                closeInputStream();
            }

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = conn.getInputStream();
            }

        } catch (MalformedURLException e) {
            System.out.println("错误的URL地址！");
        } catch (IOException e) {
            System.out.println("IO异常");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("请求特定的加密算法不存在！");
        } catch (KeyManagementException e) {
            System.out.println("通用密钥管理异常");
        }
    }

    /**
     * 获取输入流
     *
     * @return
     */
    public InputStream getInputStream() {
        return this.inputStream;
    }

    /**
     * 连接网址获取网址返回的字符串<br/>
     * 每次连接后，返回信息只能读取一次，再次读取时，返回空字符串
     *
     * @return
     * @throws IOException
     */
    public String getUrlReturnStr() throws IOException {

        if (inputStream == null) {
            throw new RuntimeException("还没有连接或连接地址[" + url + "]失败");
        }

        //获取读取流
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, encode));

        //返回串
        String result = "";
        //单行读取信息
        String str = null;

        do {
            if (str != null) {
                result += str;
            }
            str = reader.readLine();
        } while (str != null);

        return result;
    }

    /**
     * 获取连接的 Cookies<br/>
     * Cookies 信息可重复读取
     *
     * @return
     */
    public Map<String, String> getCookies() {
        if (conn == null) {
            return null;
        }

        List<String> cookieList = conn.getHeaderFields().get("Set-Cookie");

        Map<String, String> result = new HashMap<String, String>();

        if (cookieList != null) {
            for (String cookie : cookieList) {
                int p = cookie.indexOf("=");
                String key = cookie.substring(0, p);
                String value = cookie.substring(p + 1, cookie.indexOf(";"));
                result.put(key, value);
            }
        }

        return result;
    }

    /**
     * 关闭输入流和连接
     */
    public void close() {
        closeInputStream();
        closeConnect();
    }

    /**
     * 加载Cookie
     */
    private void synchronous() {
        if (conn == null) {
            return;
        }

        if (cookie == null || cookie.trim().length() <= 0) {
            return;
        }

        conn.setRequestProperty("Cookie ", cookie);
    }

    /**
     * 设置连接
     *
     * @throws ProtocolException
     */
    private void setConnection() throws ProtocolException {
        if (conn == null) {
            return;
        }

        //设置连接超时时间
        if (connectTimeout > 0) {
            conn.setConnectTimeout(connectTimeout);
        }

        //设置读取信息时间
        if (readTimeout > 0) {
            conn.setReadTimeout(readTimeout);
        }

        HttpURLConnection.setFollowRedirects(false);//设置客户端不自动执行 HTTP 重定向
        conn.setDoInput(true);
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/33.0.1750.154 Safari/537.36");
        conn.setDoOutput(true);//允许连接提交信息
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Language", "zh-cn");
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Cache-Control", "no-cache");

        //设置提交方式
        setMethod();

        //设置参数
        addParams();
    }

    /**
     * 设置提交方法
     *
     * @throws ProtocolException
     */
    private void setMethod() throws ProtocolException {
        if (conn == null) {
            return;
        }
        if (method == null || method.trim().length() <= 0) {
            return;
        }
        if ("POST".equalsIgnoreCase(method)) {
            conn.setRequestMethod("POST");
        } else {
            conn.setRequestMethod("GET");
        }
    }

    /**
     * 增加传入参数
     */
    private void addParams() {
        if (conn == null) {
            return;
        }

        if (paramStr == null || paramStr.trim().length() <= 0) {
            return;
        }

        OutputStream output = null;

        OutputStreamWriter writer = null;
        try {
            output = conn.getOutputStream();

            writer = new OutputStreamWriter(output, encode);

            writer.write(paramStr);
        } catch (Exception e) {
        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                } catch (Exception e) {
                }
                try {
                    writer.close();
                } catch (Exception e) {
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 关闭输入流
     */
    private void closeInputStream() {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (Exception e) {
            } finally {
                inputStream = null;
            }
        }
    }

    /**
     * 关闭请求连接
     */
    private void closeConnect() {
        if (conn != null) {
            try {
                conn.disconnect();
            } catch (Exception e) {
            } finally {
                conn = null;
            }
        }
    }

    /**
     * 设置编码格式
     *
     * @param encode
     */
    public void setEncode(String encode) {
        this.encode = encode;
    }

    /**
     * 设置连接地址
     * url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 设置传入参数
     *
     * @param paramStr
     */
    public void setParamStr(String paramStr) {
        this.paramStr = paramStr;
    }

    /**
     * 设置连接访问地址超时时间
     * connectTimeout
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * 设置读取返回信息超时时间
     * readTimeout
     */
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    /**
     * 设置提交方式
     *
     * @param method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 设置 Cookie
     *
     * @param cookie
     */
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    /**
     * 检查服务器网络连接
     *
     * @return
     */
    public boolean testNetWork() {
        HttpConnector httpConnector = new HttpConnector();

        httpConnector.setConnectTimeout(200);
        httpConnector.setUrl("https://www.baidu.com");
        httpConnector.connectUrl();

        int responseCode = 0;
        try {
            responseCode = httpConnector.conn.getResponseCode();
        } catch (IOException e) {
            System.out.println("连接异常！");
        }

        if (responseCode == 200) {
            return true;
        }
        return false;
    }
}
