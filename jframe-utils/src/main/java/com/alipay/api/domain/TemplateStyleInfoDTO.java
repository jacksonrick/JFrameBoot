package com.alipay.api.domain;

import java.util.List;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;
import com.alipay.api.internal.mapping.ApiListField;

/**
 * 卡模板样式信息
 *
 * @author auto create
 * @since 1.0, 2016-12-28 15:26:45
 */
public class TemplateStyleInfoDTO extends AlipayObject {

	private static final long serialVersionUID = 7117361332675773414L;

	/**
	 * 背景图片Id，通过接口（alipay.offline.material.image.upload）上传图片

图片说明：2M以内，格式：bmp、png、jpeg、jpg、gif；
尺寸不小于1020*643px；
图片不得有圆角，不得拉伸变形
	 */
	@ApiField("background_id")
	private String backgroundId;

	/**
	 * 背景色
	 */
	@ApiField("bg_color")
	private String bgColor;

	/**
	 * 品牌商名称
	 */
	@ApiField("brand_name")
	private String brandName;

	/**
	 * 钱包端显示名称（字符串长度）
	 */
	@ApiField("card_show_name")
	private String cardShowName;

	/**
	 * 卡片颜色
	 */
	@ApiField("color")
	private String color;

	/**
	 * 特色信息，用于领卡预览
	 */
	@ApiListField("feature_descriptions")
	@ApiField("string")
	private List<String> featureDescriptions;

	/**
	 * logo的图片ID，通过接口（alipay.offline.material.image.upload）上传图片

图片说明：1M以内，格式bmp、png、jpeg、jpg、gif；
尺寸不小于500*500px的正方形；
请优先使用商家LOGO；
	 */
	@ApiField("logo_id")
	private String logoId;

	/**
	 * 标语
	 */
	@ApiField("slogan")
	private String slogan;

	/**
	 * 标语图片， 通过接口（alipay.offline.material.image.upload）上传图片
	 */
	@ApiField("slogan_img_id")
	private String sloganImgId;

	public String getBackgroundId() {
		return this.backgroundId;
	}
	public void setBackgroundId(String backgroundId) {
		this.backgroundId = backgroundId;
	}

	public String getBgColor() {
		return this.bgColor;
	}
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getBrandName() {
		return this.brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCardShowName() {
		return this.cardShowName;
	}
	public void setCardShowName(String cardShowName) {
		this.cardShowName = cardShowName;
	}

	public String getColor() {
		return this.color;
	}
	public void setColor(String color) {
		this.color = color;
	}

	public List<String> getFeatureDescriptions() {
		return this.featureDescriptions;
	}
	public void setFeatureDescriptions(List<String> featureDescriptions) {
		this.featureDescriptions = featureDescriptions;
	}

	public String getLogoId() {
		return this.logoId;
	}
	public void setLogoId(String logoId) {
		this.logoId = logoId;
	}

	public String getSlogan() {
		return this.slogan;
	}
	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getSloganImgId() {
		return this.sloganImgId;
	}
	public void setSloganImgId(String sloganImgId) {
		this.sloganImgId = sloganImgId;
	}

}
