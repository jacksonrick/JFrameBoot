package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 触发现金红包活动
 *
 * @author auto create
 * @since 1.0, 2017-02-23 23:07:59
 */
public class AlipayMarketingCampaignCashTriggerModel extends AlipayObject {

	private static final long serialVersionUID = 4378184276654762197L;

	/**
	 * 现金活动号
	 */
	@ApiField("crowd_no")
	private String crowdNo;

	/**
	 * 用户登录账号名：邮箱或手机号。user_id与login_id至少有一个非空，都非空时，以user_id为准。
	 */
	@ApiField("login_id")
	private String loginId;

	/**
	 * 领取红包的外部业务号，只由可由字母、数字、下划线组成。同一个活动中不可重复，相同的外部业务号会被幂等并返回之前的结果。不填时，系统会生成一个默认固定的外部业务号。
	 */
	@ApiField("out_biz_no")
	private String outBizNo;

	/**
	 * 用户唯一标识userId。user_id与login_id至少有一个非空；都非空时，以user_id为准。
	 */
	@ApiField("user_id")
	private String userId;

	public String getCrowdNo() {
		return this.crowdNo;
	}
	public void setCrowdNo(String crowdNo) {
		this.crowdNo = crowdNo;
	}

	public String getLoginId() {
		return this.loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getOutBizNo() {
		return this.outBizNo;
	}
	public void setOutBizNo(String outBizNo) {
		this.outBizNo = outBizNo;
	}

	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
