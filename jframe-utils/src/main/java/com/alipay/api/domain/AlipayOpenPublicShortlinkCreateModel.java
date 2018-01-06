package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 服务窗短链自主生成接口
 *
 * @author auto create
 * @since 1.0, 2016-08-10 17:26:47
 */
public class AlipayOpenPublicShortlinkCreateModel extends AlipayObject {

	private static final long serialVersionUID = 4731719981661899148L;

	/**
	 * 对于场景ID的描述，商户自己定义
	 */
	@ApiField("remark")
	private String remark;

	/**
	 * 短链接对应的场景ID，该ID由商户自己定义
	 */
	@ApiField("scene_id")
	private String sceneId;

	public String getRemark() {
		return this.remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSceneId() {
		return this.sceneId;
	}
	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}

}
