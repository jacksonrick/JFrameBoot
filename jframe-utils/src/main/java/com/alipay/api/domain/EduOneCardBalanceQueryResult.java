package com.alipay.api.domain;

import java.util.Date;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 校园一卡通余额查询结果对象
 *
 * @author auto create
 * @since 1.0, 2016-10-26 17:43:42
 */
public class EduOneCardBalanceQueryResult extends AlipayObject {

	private static final long serialVersionUID = 5381242573599445318L;

	/**
	 * 校园一卡通机构
	 */
	@ApiField("agent_code")
	private String agentCode;

	/**
	 * 校园一卡通可用余额
	 */
	@ApiField("balance")
	private String balance;

	/**
	 * 校园一卡通姓名
	 */
	@ApiField("card_name")
	private String cardName;

	/**
	 * 校园一卡通卡号
	 */
	@ApiField("card_no")
	private String cardNo;

	/**
	 * 余额最后更新时间
	 */
	@ApiField("last_update_time")
	private Date lastUpdateTime;

	/**
	 * 校园一卡通待领金额
	 */
	@ApiField("pound_amount")
	private String poundAmount;

	public String getAgentCode() {
		return this.agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getBalance() {
		return this.balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getCardName() {
		return this.cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNo() {
		return this.cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getPoundAmount() {
		return this.poundAmount;
	}
	public void setPoundAmount(String poundAmount) {
		this.poundAmount = poundAmount;
	}

}
