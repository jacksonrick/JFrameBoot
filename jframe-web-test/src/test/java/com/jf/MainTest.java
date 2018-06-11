package com.jf;

import com.jf.system.Environment;
import com.jf.system.SystemUtil;
import com.jf.system.LogManager;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-04-02
 * Time: 17:12
 */
public class MainTest {

    public static void main(String[] args) {

    }

    public static URI testURI(String uri) {
        URI u = URI.create(uri);
        //Let's assuming most of the time it is OK.
        if (u.getHost() != null) {
            return u;
        }
        String s = uri.substring(0, uri.lastIndexOf(":")).replaceFirst("redis://", "");
        //Assuming this is an IPv6 format, other situations will be handled by
        //Netty at a later stage.
        return URI.create(uri.replace(s, "[" + s + "]"));
    }

    public static void testSpace() {
        StringBuffer sb = new StringBuffer("Test" + " starting...");
        double[] space = new SystemUtil().getSpace();
        sb.append("\n操作系统：\t").append(Environment.getOsName())
                .append("\n系统版本：\t").append(Environment.getOsVersion())
                .append("\nIP地址：\t\t").append(Environment.getHostAddress())
                .append("\nJAVA版本：\t").append(Environment.getJavaVersion())
                .append("\nJAVAHOME：\t").append(Environment.getJavaHome())
                .append("\nJAVA类库：\t").append(Environment.getLibraryPath())
                .append("\n磁盘空间：\t").append("可用:").append(space[0]).append("GB, 已用:").append(space[1]).append("GB, 总计:").append(space[2]).append("GB")
                .append("\n内存：\t\t").append("可用:").append(space[3]).append("GB, 已用:").append(space[4]).append("GB, 总计:").append(space[5]).append("GB");

        LogManager.info(sb.toString());

        double minSpace = 5;
        double minMem = 2;
        if (space[0] < minSpace || space[3] < minMem) {
            LogManager.error("磁盘或内存空间不足【minSpace:" + minSpace + "GB, minMem:" + minMem + "GB】");
            System.exit(0);
        }

        LogManager.info("started");
    }

}
