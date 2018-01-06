package com.jf.obj;

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
		String exStr = "com.jf.entity.BaseVo";
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
			e.printStackTrace();
		}
		return params;
	}

}
