package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 虚拟卡信息同步
 *
 * @author auto create
 * @since 1.0, 2017-05-15 20:58:52
 */
public class AlipayCommerceTransportOfflinepayVirtualcardSendModel extends AlipayObject {

	private static final long serialVersionUID = 8895448184129453117L;

	/**
	 * 虚拟卡信息同步动作
	 */
	@ApiField("action")
	private String action;

	/**
	 * 用户虚拟卡余额，单位元。
	 */
	@ApiField("balance")
	private String balance;

	/**
	 * hex格式表示的虚拟卡数据，卡数据将在虚拟卡二维码中透传。
	 */
	@ApiField("card_data")
	private String cardData;

	/**
	 * 用户虚拟卡卡号
	 */
	@ApiField("card_no")
	private String cardNo;

	/**
	 * 虚拟卡卡类型
	 */
	@ApiField("card_type")
	private String cardType;

	/**
	 * 表示虚拟卡是否可用
	 */
	@ApiField("disabled")
	private String disabled;

	/**
	 * 当虚拟卡不可用时，提示用户不可用原因。
	 */
	@ApiField("disabled_tips")
	private String disabledTips;

	/**
	 * json格式字符串，存放扩展信息。
	 */
	@ApiField("ext_info")
	private String extInfo;

	/**
	 * 虚拟卡最后更新时间 使用标准java时间格式
	 */
	@ApiField("last_update_time")
	private String lastUpdateTime;

	/**
	 * 支付宝用户id
	 */
	@ApiField("user_id")
	private String userId;

	public String getAction() {
		return this.action;
	}
	public void setAction(String action) {
		this.action = action;
	}

	public String getBalance() {
		return this.balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getCardData() {
		return this.cardData;
	}
	public void setCardData(String cardData) {
		this.cardData = cardData;
	}

	public String getCardNo() {
		return this.cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardType() {
		return this.cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getDisabled() {
		return this.disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getDisabledTips() {
		return this.disabledTips;
	}
	public void setDisabledTips(String disabledTips) {
		this.disabledTips = disabledTips;
	}

	public String getExtInfo() {
		return this.extInfo;
	}
	public void setExtInfo(String extInfo) {
		this.extInfo = extInfo;
	}

	public String getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
