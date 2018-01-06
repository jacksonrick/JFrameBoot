package com.alipay.api.response;

import com.alipay.api.internal.mapping.ApiField;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: alipay.mobile.public.menu.get response.
 * 
 * @author auto create
 * @since 1.0, 2016-01-05 22:34:31
 */
public class AlipayMobilePublicMenuGetResponse extends AlipayResponse {

	private static final long serialVersionUID = 7226412345748798684L;

	/** 
	 * success
	 */
	@ApiField("code")
	private String code;

	/** 
	 * 菜单内容
	 */
	@ApiField("menu_content")
	private String menuContent;

	/** 
	 * 成功
	 */
	@ApiField("msg")
	private String msg;

	public void setCode(String code) {
		this.code = code;
	}
	public String getCode( ) {
		return this.code;
	}

	public void setMenuContent(String menuContent) {
		this.menuContent = menuContent;
	}
	public String getMenuContent( ) {
		return this.menuContent;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsg( ) {
		return this.msg;
	}

}
