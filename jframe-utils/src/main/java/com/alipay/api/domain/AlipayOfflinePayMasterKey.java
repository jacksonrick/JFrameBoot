package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 描述支付宝脱机交易主秘钥
 *
 * @author auto create
 * @since 1.0, 2016-10-26 17:43:41
 */
public class AlipayOfflinePayMasterKey extends AlipayObject {

	private static final long serialVersionUID = 2886381656624954165L;

	/**
	 * 秘钥id
	 */
	@ApiField("key_id")
	private Long keyId;

	/**
	 * 支付宝脱机服务公钥
	 */
	@ApiField("public_key")
	private String publicKey;

	public Long getKeyId() {
		return this.keyId;
	}
	public void setKeyId(Long keyId) {
		this.keyId = keyId;
	}

	public String getPublicKey() {
		return this.publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

}
