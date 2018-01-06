package com.jf.http;

import java.net.*;
import java.util.List;
import java.util.Map;

/**
 * Cookie处理
 */
public class HttpCookie {

    /**
     * 处理后保存的 Cookie
     */
    private String cookie;

    /**
     * 创建 Cookie
     *
     * @param url      创建 Cookie 连接的 url
     * @param paramStr 提交的参数
     * @return
     * @throws URISyntaxException
     */
    public String createCookie(String url, String paramStr) throws URISyntaxException {
        URI uri = new URI(url);

        HttpConnector http = new HttpConnector();

        http.setUrl(url);
        http.setMethod("POST");
        http.setParamStr(paramStr);

        http.connectUrl();

        //获取http连接的Cookies
        Map<String, String> map = http.getCookies();

        http.close();

        String cookie = getReturnCookie(map);

        java.net.HttpCookie hc = storeCookie(uri, cookie);

        if (hc != null && !hc.hasExpired()) {
            this.cookie = hc.getValue();
        }
        return this.cookie;
    }

    public String getCookie() {
        return cookie;
    }

    /**
     * 生成 Cookie
     *
     * @param map
     */
    private String getReturnCookie(Map<String, String> map) {
        if (map == null || map.size() <= 0) {
            return "";
        }
        String cookie = "";
        for (String key : map.keySet()) {
            cookie += key + "=" + map.get(key) + ";";
        }
        return cookie;
    }

    /**
     * 存储Cookie
     *
     * @param uri    链接地址
     * @param cookie cookie串
     * @return HttpCookie 对象
     */
    private java.net.HttpCookie storeCookie(URI uri, String cookie) {
        //创建一个默认的 CookieManager
        CookieManager manager = new CookieManager();

        //将规则改掉，接受所有的 Cookie
        manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        //保存这个定制的 CookieManager
        CookieHandler.setDefault(manager);

        //接受 HTTP 请求的时候，得到和保存新的 Cookie
        java.net.HttpCookie httpCookie = new java.net.HttpCookie("Cookie:", cookie);
        httpCookie.setMaxAge(6000);

        //获得保存cookie的对象
        CookieStore store = manager.getCookieStore();
        //添加cookie到客户端
        store.add(uri, httpCookie);

        // 得到所有的 URI
        List<URI> uris = store.getURIs();

        for (URI realURI : uris) {
            // 筛选需要的 URI
            List<java.net.HttpCookie> cookies = store.get(realURI);
            // 得到属于这个 URI 的所有 Cookie
            for (java.net.HttpCookie cookieObj : cookies) {
                return cookieObj;
            }
        }
        return null;
    }


}
