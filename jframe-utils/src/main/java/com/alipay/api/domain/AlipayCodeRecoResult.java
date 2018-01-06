package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 数据枢纽验证码服务返回结果
 *
 * @author auto create
 * @since 1.0, 2016-10-26 17:43:39
 */
public class AlipayCodeRecoResult extends AlipayObject {

	private static final long serialVersionUID = 7725558246318818744L;

	/**
	 * 识别的验证码内容
	 */
	@ApiField("content")
	private String content;

	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
