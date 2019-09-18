package com.jf.obj;

import com.jf.exception.SysException;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 实体类工具
 *
 * @author rick
 */
public class BeanUtil {

    /**
     * 实体类转Map
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> beanToMap(Object obj) {
        String exStr = "com.jf.database.model.custom.BaseVo";
        Map<String, Object> params = new HashMap<String, Object>(0);
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                String ex = descriptors[i].getReadMethod().getDeclaringClass().getName();
                if (exStr.equals(ex)) {
                    continue;
                }
                if (!"class".equals(name)) {
                    Object object = propertyUtilsBean.getNestedProperty(obj, name);
                    if (object != null) {
                        params.put(name, object);
                    }
                }
            }
        } catch (Exception e) {
            throw new SysException(e.getMessage(), e);
        }
        return params;
    }

    /**
     * 执行set方法
     *
     * @param field
     * @param value
     * @param entity
     * @param <T>
     * @throws Exception
     */
    public static <T> void setEntityValue(String field, Object value, T entity) throws Exception {
        PropertyDescriptor pd = new PropertyDescriptor(field, entity.getClass());
        Method method = pd.getWriteMethod();
        method.invoke(entity, value);
    }

    /**
     * 执行get方法
     *
     * @param field
     * @param entity
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> Object getEntityValue(String field, T entity) throws Exception {
        PropertyDescriptor pd = new PropertyDescriptor(field, entity.getClass());
        Method method = pd.getReadMethod();
        return method.invoke(entity);
    }

    /**
     * 执行指定方法
     *
     * @param method
     * @param entity
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> Object doMethod(String method, T entity) throws Exception {
        Method met = entity.getClass().getMethod(method);
        return met.invoke(entity);
    }

    /**
     * 执行指定方法（带参）
     *
     * @param method
     * @param entity
     * @param args
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> Object doMethod(String method, T entity, Object... args) throws Exception {
        Class[] cls = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            cls[i] = args[i].getClass();
        }
        Method met = entity.getClass().getMethod(method, cls);
        return met.invoke(entity, args);
    }

}
