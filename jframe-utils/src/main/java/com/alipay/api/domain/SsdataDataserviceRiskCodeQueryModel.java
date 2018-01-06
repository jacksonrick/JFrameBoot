package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 反欺诈服务
 *
 * @author auto create
 * @since 1.0, 2017-03-23 16:21:32
 */
public class SsdataDataserviceRiskCodeQueryModel extends AlipayObject {

	private static final long serialVersionUID = 5169564134846326897L;

	/**
	 * 地址信息。省+市+区/县+详细地址，其中 省+市+区/县可以为空，长度不超过256，不含",","/u0001"，"|","&","^","\\"
	 */
	@ApiField("address")
	private String address;

	/**
	 * 银行卡号。中国大陆银行发布的银行卡:借记卡长度19位；信用卡长度16位；各位的取值位[0,9]的整数；不含虚拟卡。
	 */
	@ApiField("bank_card")
	private String bankCard;

	/**
	 * 电子邮箱。合法email，字母小写，特殊符号以半角形式出现
	 */
	@ApiField("email")
	private String email;

	/**
	 * 国际移动设备标志。15位长度数字
	 */
	@ApiField("imei")
	private String imei;

	/**
	 * ip地址。以"."分割的四段Ip，如 x.x.x.x，x为[0,255]之间的整数
	 */
	@ApiField("ip")
	private String ip;

	/**
	 * 物理地址。支持格式如下：xx:xx:xx:xx:xx:xx，xx-xx-xx-xx-xx-xx，xxxxxxxxxxxx，x取值范围[0,9]之间的整数及A，B，C，D，E，F
	 */
	@ApiField("mac")
	private String mac;

	/**
	 * 手机号码，中国大陆合法手机号码，长度11位，不含国家代码
	 */
	@ApiField("mobile")
	private String mobile;

	/**
	 * 姓名，长度不超过64，姓名中不含",","/u0001"，"|","&","^","\\"
	 */
	@ApiField("name")
	private String name;

	/**
	 * wifi的物理地址。支持格式如下：xx:xx:xx:xx:xx:xx，xx-xx-xx-xx-xx-xx，xxxxxxxxxxxx，x取值范围[0,9]之间的整数及A，B，C，D，E，F
	 */
	@ApiField("wifimac")
	private String wifimac;

	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getBankCard() {
		return this.bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getImei() {
		return this.imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getIp() {
		return this.ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return this.mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getMobile() {
		return this.mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getWifimac() {
		return this.wifimac;
	}
	public void setWifimac(String wifimac) {
		this.wifimac = wifimac;
	}

}
