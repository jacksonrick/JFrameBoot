package com.alipay.api.response;

import com.alipay.api.internal.mapping.ApiField;
import com.alipay.api.domain.MerchantCard;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: alipay.marketing.card.query response.
 * 
 * @author auto create
 * @since 1.0, 2017-01-13 12:23:01
 */
public class AlipayMarketingCardQueryResponse extends AlipayResponse {

	private static final long serialVersionUID = 3793365123574876964L;

	/** 
	 * 商户卡信息
	 */
	@ApiField("card_info")
	private MerchantCard cardInfo;

	/** 
	 * 商户会员卡页面跳转到支付宝卡券详情页面的schema地址
	 */
	@ApiField("schema_url")
	private String schemaUrl;

	public void setCardInfo(MerchantCard cardInfo) {
		this.cardInfo = cardInfo;
	}
	public MerchantCard getCardInfo( ) {
		return this.cardInfo;
	}

	public void setSchemaUrl(String schemaUrl) {
		this.schemaUrl = schemaUrl;
	}
	public String getSchemaUrl( ) {
		return this.schemaUrl;
	}

}
