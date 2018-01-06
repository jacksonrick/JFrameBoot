package com.jf.json;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Json时间转换
 * @author rick
 * @version
 */
public class DateJsonValueProcessor implements JsonValueProcessor {
	private String datePattern = "yyyy-MM-dd HH:mm:ss";

	public DateJsonValueProcessor() {
        super();
    }

	public DateJsonValueProcessor(String format) {
        super();
        this.datePattern = format;
    }

	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return process(value);
	}

	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		return process(value);
	}

	private Object process(Object value) {
		try {
			if (value instanceof Date) {
				SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
				return sdf.format((Date) value);
			}else if(value instanceof Timestamp){
				SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
				return sdf.format((Date) value);
			}
			return value == null ? "" : value.toString();
		} catch (Exception e) {
			return "";
		}
	}

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String pDatePattern) {
		datePattern = pDatePattern;
	}
}
