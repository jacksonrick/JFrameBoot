package com.jf.obj;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 一般类工具类
 */
public class ObjectUtil {

    private ObjectUtil() {
    }

    /**
     * 将对象序列化成 XML 字符串
     *
     * @param obj 待序列化的对象
     * @return String 序列化后的 XML 字符串
     */
    public static String serializeToXml(Object obj) {
        XStream xstream = new XStream();
        return xstream.toXML(obj);
    }

    /**
     * 反序列化，将序列化的字符串反序列化成对象
     *
     * @param xml 待反序列化的字符串
     * @return Object 反序列化后的对象
     */
    public static Object deSerializeFromXml(String xml) {
        XStream xs = new XStream(new DomDriver());
        return xs.fromXML(xml);
    }

    /**
     * 深度克隆，将传入对象以及其中的自对象全部克隆<br/>
     * 被克隆对象包括其中的子对象必须序列化
     *
     * @param serializ
     * @return
     * @throws RuntimeException
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deepClone(T serializ) throws RuntimeException {
        ByteArrayOutputStream memoryBuffer = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        T dist = null;
        try {
            out = new ObjectOutputStream(memoryBuffer);
            out.writeObject(serializ);
            out.flush();
            in = new ObjectInputStream(new ByteArrayInputStream(memoryBuffer.toByteArray()));
            dist = (T) in.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null)
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            if (in != null)
                try {
                    in.close();
                    in = null;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
        return dist;
    }

    /**
     * 验证类 clazz 是否实现了接口 interfazz
     *
     * @param clazz     实现类
     * @param interfazz 接口
     * @return boolean (true 实现类实现了接口，false 实现类没有实现接口)
     */
    public static boolean isIntergrated(Class<?> clazz, Class<?> interfazz) {
        Class<?>[] cs = clazz.getInterfaces();
        if (cs != null && cs.length > 0) {
            for (Class<?> objc : cs) {
                if (objc == interfazz) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取类中所有Field包括父类 直到父类为finalClass为止
     *
     * @param c          目标类
     * @param finalClass 终止父类
     * @return 所有field 集合
     */
    public static List<Field> getFieldList(Class<?> c, Class<?> finalClass) {
        List<Field> list = new ArrayList<Field>();

        //防止修改 c 的类型，所以这里重新定义变量
        Class<?> clazz = c;

        do {
            Field originFields[] = clazz.getDeclaredFields();
            for (int i = 0; i < originFields.length; i++) {
                list.add(originFields[i]);
            }
            // 如果终止类即本身则直接跳出循环
            if (finalClass == clazz) {
                break;
            }

            clazz = clazz.getSuperclass();

            if (clazz == Object.class) {
                break;
            }
        } while (clazz != finalClass);

        return list;
    }

    /**
     * 获取类中所有Field包括父类 ,直到 Object 类为止
     *
     * @param c 目标类
     * @return
     */
    public static List<Field> getFieldList(Class<?> c) {
        return getFieldList(c, Object.class);
    }

    /**
     * 从类中获取指定名称的 Field，包括父类，如果没有返回 null
     *
     * @param c         目标类
     * @param fieldName 指定类名
     * @return
     */
    public static Field getField(Class<?> c, String fieldName) {
        do {
            try {
                return c.getDeclaredField(fieldName);
            } catch (Exception e) {
            }
            c = c.getSuperclass();
        } while (c != Object.class);

        return null;
    }

    /**
     * 比较类要将字段名用中文表示需要继承的接口
     */
    public interface CompareObj {
        /**
         * 获取其中的属性名 Map
         *
         * @return Map (key 属性字段，value 属性中文名)
         */
        public Map<String, String> getPropertyNameMap();
    }

}
