package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 会员卡模板外部权益查询
 *
 * @author auto create
 * @since 1.0, 2017-04-20 22:02:34
 */
public class AlipayMarketingCardBenefitQueryModel extends AlipayObject {

	private static final long serialVersionUID = 2633445795674199488L;

	/**
	 * 权益ID
	 */
	@ApiField("benefit_id")
	private String benefitId;

	/**
	 * 会员卡模板ID
	 */
	@ApiField("template_id")
	private String templateId;

	public String getBenefitId() {
		return this.benefitId;
	}
	public void setBenefitId(String benefitId) {
		this.benefitId = benefitId;
	}

	public String getTemplateId() {
		return this.templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

}
