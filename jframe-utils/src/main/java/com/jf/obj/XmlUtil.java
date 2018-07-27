package com.jf.obj;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Created with IntelliJ IDEA.
 * Description: XML-对象互转
 * User: xujunfei
 * Date: 2018-07-06
 * Time: 16:18
 */
public class XmlUtil {

    /**
     * 将对象序列化成 XML 字符串
     *
     * @param obj   待序列化的对象
     * @param alias 别名
     * @return String 序列化后的 XML 字符串
     */
    public static String serializeToXml(Object obj, String alias) {
        XStream xstream = new XStream();
        xstream.alias(alias, obj.getClass());
        return xstream.toXML(obj);
    }

    /**
     * 反序列化，将序列化的字符串反序列化成对象
     *
     * @param xml   待反序列化的字符串
     * @param alias 别名
     * @param clazz
     * @return Object 反序列化后的对象
     */
    public static <T> T deSerializeFromXml(String xml, String alias, Class<T> clazz) {
        XStream xstream = new XStream(new DomDriver());
        xstream.alias(alias, clazz);
        return (T) xstream.fromXML(xml);
    }

}
