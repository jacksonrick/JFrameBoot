package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 单条数据传入
 *
 * @author auto create
 * @since 1.0, 2017-05-27 22:38:09
 */
public class ZhimaMerchantSingleDataUploadModel extends AlipayObject {

	private static final long serialVersionUID = 1182114884924941849L;

	/**
	 * 公用回传参数（非必填），这个字段由商户传入，系统透传给商户，便于商户做逻辑关联，请使用json格式。
	 */
	@ApiField("biz_ext_params")
	private String bizExtParams;

	/**
	 * 传入的json数据，商户通过json格式将数据传给芝麻 ， json中的字段可以通过如下步骤获取：首先调用zhima.merchant.data.upload.initialize接口获取数据模板，该接口会返回一个数据模板文件的url地址，如：http://zmxymerchant-prod.oss-cn-shenzhen.zmxy.com.cn/openApi/openDoc/信用护航-负面记录和信用足迹商户数据模板V1.0.xlsx，该数据模板文件详细列出了需要传入的字段，及各字段的要求，data中的各字段就是该文件中列出的字段编码。
	 */
	@ApiField("data")
	private String data;

	/**
	 * 主键列使用传入字段进行组合，也可以使用传入的某个单字段（确保主键稳定，而且可以很好的区分不同的数据）。例如order_no,pay_month或者order_no,bill_month组合，对于一个order_no只会有一条数据的情况，直接使用order_no作为主键列 。
	 */
	@ApiField("primary_keys")
	private String primaryKeys;

	/**
	 * 数据应用的场景编码 ，场景码和场景名称（数字为场景码）如下：
1:负面披露
2:信用足迹
3:负面+足迹
4:信用守护
5:负面+守护
6:足迹+守护
7:负面+足迹+守护
8:数据反馈
32:骑行
每个场景码对应的数据模板不一样，请使用zhima.merchant.data.upload.initialize接口获取场景码对应的数据模板。
	 */
	@ApiField("scene_code")
	private String sceneCode;

	public String getBizExtParams() {
		return this.bizExtParams;
	}
	public void setBizExtParams(String bizExtParams) {
		this.bizExtParams = bizExtParams;
	}

	public String getData() {
		return this.data;
	}
	public void setData(String data) {
		this.data = data;
	}

	public String getPrimaryKeys() {
		return this.primaryKeys;
	}
	public void setPrimaryKeys(String primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	public String getSceneCode() {
		return this.sceneCode;
	}
	public void setSceneCode(String sceneCode) {
		this.sceneCode = sceneCode;
	}

}
