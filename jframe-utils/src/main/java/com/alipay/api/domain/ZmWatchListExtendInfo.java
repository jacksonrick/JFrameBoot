package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 芝麻行业关注名单单条信息的扩展信息
 *
 * @author auto create
 * @since 1.0, 2017-05-03 14:30:48
 */
public class ZmWatchListExtendInfo extends AlipayObject {

	private static final long serialVersionUID = 4499499123751727969L;

	/**
	 * 对于这个key的描述
	 */
	@ApiField("description")
	private String description;

	/**
	 * 对应的字段名
	 */
	@ApiField("key")
	private String key;

	/**
	 * 对应的值
	 */
	@ApiField("value")
	private String value;

	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getKey() {
		return this.key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
