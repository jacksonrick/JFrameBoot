package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 公众号标签管理-添加标签
 *
 * @author auto create
 * @since 1.0, 2016-10-26 18:05:14
 */
public class AlipayOpenPublicLabelCreateModel extends AlipayObject {

	private static final long serialVersionUID = 1568361466151328278L;

	/**
	 * 标签名
	 */
	@ApiField("name")
	private String name;

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
