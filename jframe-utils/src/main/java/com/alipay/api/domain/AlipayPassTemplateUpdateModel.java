package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 支付宝pass更新模版接口
 *
 * @author auto create
 * @since 1.0, 2017-03-31 15:32:46
 */
public class AlipayPassTemplateUpdateModel extends AlipayObject {

	private static final long serialVersionUID = 5187623654583215172L;

	/**
	 * 模板内容信息，遵循JSON规范，详情参见tpl_content参数说明:https://doc.open.alipay.com/doc2/detail.htm?treeId=193&articleId=105249&docType=1#tpl_content
	 */
	@ApiField("tpl_content")
	private String tplContent;

	/**
	 * 更新的模板ID
	 */
	@ApiField("tpl_id")
	private String tplId;

	public String getTplContent() {
		return this.tplContent;
	}
	public void setTplContent(String tplContent) {
		this.tplContent = tplContent;
	}

	public String getTplId() {
		return this.tplId;
	}
	public void setTplId(String tplId) {
		this.tplId = tplId;
	}

}
