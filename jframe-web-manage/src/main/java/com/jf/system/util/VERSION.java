package com.jf.system.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by xujunfei on 2017/4/24.
 */
public class VERSION {

    public static String SYS_VER = "";
    public static String SYS_NAME = "";
    public static String UPGRADE = "";

    static {
        readVersionFile();
    }

    /**
     * 读取更新日志并生成html
     */
    private static void readVersionFile() {
        String html = "";
        InputStream is = VERSION.class.getResourceAsStream("/VERSION");
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);
            Element root = doc.getRootElement();
            Element info = root.element("sys");
            SYS_VER = info.elementText("lastest");
            SYS_NAME = info.elementText("sysname");
            for (Iterator<Element> iter = root.elementIterator("upgrade"); iter.hasNext(); ) {
                html += "<div class=\"panel panel-default\"><div class=\"panel-heading\"><h5 " +
                        "class=\"panel-title\"><a class=\"collapsed\">";
                Element element = iter.next();
                for (Iterator<Element> it2 = element.elementIterator(); it2.hasNext(); ) {
                    Element child = it2.next();
                    if ("version".equals(child.getName())) {
                        html += child.getData() + "</a><code class=\"pull-right\">";
                    }
                    if ("date".equals(child.getName())) {
                        html += child.getData() + "</code></h5></div><div class=\"panel-collapse collapse in\"><div " +
                                "class=\"panel-body\">";
                    }
                    if ("content".equals(child.getName())) {
                        html += "<ul style=\"padding-left: 10px;\">";
                        for (Iterator<Element> it3 = child.elementIterator(); it3.hasNext(); ) {
                            Element son = it3.next();
                            html += "<li>" + son.getData() + "</li>";
                        }
                        html += "</ul>";
                    }
                }
                html += "</div></div></div>\r\n";
            }
            UPGRADE = html;
        } catch (DocumentException e) {
            UPGRADE = "";
        }
    }

}
