/**
 * Copyright (C) 2008 Happy Fish / YuQing
 * <p>
 * FastDFS Java Client may be copied only under the terms of the GNU Lesser
 * General Public License (LGPL).
 * Please visit the FastDFS Home Page http://www.csource.org/ for more detail.
 **/

package com.jf.fdfs.fastdfs;

import com.jf.fdfs.common.IniFileReader;
import com.jf.fdfs.common.MyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Global variables
 *
 * @author Happy Fish / YuQing
 * @version Version 1.11
 */
@Component
public class ClientGlobal {
    public static int connect_timeout; //millisecond
    public static int network_timeout; //millisecond
    public static String charset;
    public static String tracker_server;
    public static int tracker_server_port;
    public static String nginx_server;
    public static int tracker_http_port;
    public static boolean anti_steal_token;  //if anti-steal token
    public static String secret_key;   //generage token secret key

    public static TrackerGroup tracker_group;

    public static final int DEFAULT_CONNECT_TIMEOUT = 5;  //second
    public static final int DEFAULT_NETWORK_TIMEOUT = 30; //second

    @Value("${fdfs.connect_timeout}")
    public void setConnect_timeout(int connect_timeout) {
        ClientGlobal.connect_timeout = connect_timeout * 1000;
    }

    @Value("${fdfs.network_timeout}")
    public void setNetwork_timeout(int network_timeout) {
        ClientGlobal.network_timeout = network_timeout * 1000;
    }

    @Value("${fdfs.charset}")
    public void setCharset(String charset) {
        ClientGlobal.charset = charset;
    }

    @Value("${fdfs.tracker_server}")
    public void setTracker_server(String tracker_server) {
        ClientGlobal.tracker_server = tracker_server;
    }

    @Value("${fdfs.tracker_server_port}")
    public void setTracker_server_port(int tracker_server_port) {
        ClientGlobal.tracker_server_port = tracker_server_port;
    }

    @Value("${fdfs.nginx_server}")
    public void setNginx_server(String nginx_server) {
        ClientGlobal.nginx_server = nginx_server;
    }

    @Value("${fdfs.http.tracker_http_port}")
    public void setTracker_http_port(int tracker_http_port) {
        ClientGlobal.tracker_http_port = tracker_http_port;
    }

    @Value("${fdfs.http.anti_steal_token}")
    public void setAnti_steal_token(boolean anti_steal_token) {
        ClientGlobal.anti_steal_token = anti_steal_token;
    }

    @Value("${fdfs.http.secret_key}")
    public void setSecret_key(String secret_key) {
        ClientGlobal.secret_key = secret_key;
    }

    /**
     * load global variables
     */
    public static void init() throws IOException, MyException {
        // servers
        InetSocketAddress[] tracker_servers = new InetSocketAddress[]
                {new InetSocketAddress(ClientGlobal.tracker_server, ClientGlobal.tracker_server_port)};
        tracker_group = new TrackerGroup(tracker_servers);
    }

    /**
     * construct Socket object
     *
     * @param ip_addr ip address or hostname
     * @param port    port number
     * @return connected Socket object
     */
    public static Socket getSocket(String ip_addr, int port) throws IOException {
        Socket sock = new Socket();
        sock.setSoTimeout(ClientGlobal.network_timeout);
        sock.connect(new InetSocketAddress(ip_addr, port), ClientGlobal.connect_timeout);
        return sock;
    }

    /**
     * construct Socket object
     *
     * @param addr InetSocketAddress object, including ip address and port
     * @return connected Socket object
     */
    public static Socket getSocket(InetSocketAddress addr) throws IOException {
        Socket sock = new Socket();
        sock.setSoTimeout(ClientGlobal.network_timeout);
        sock.connect(addr, ClientGlobal.connect_timeout);
        return sock;
    }

}
