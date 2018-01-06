package com.alipay.api.domain;

import java.util.Date;
import java.util.List;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;
import com.alipay.api.internal.mapping.ApiListField;

/**
 * 快消店铺展位内容修改接口
 *
 * @author auto create
 * @since 1.0, 2017-05-16 17:02:20
 */
public class KoubeiMarketingCampaignRetailDmModifyModel extends AlipayObject {

	private static final long serialVersionUID = 2692352229579944392L;

	/**
	 * 第三方详情页链接：该商品/活动的详细介绍，注意：该字段需要过风控校验，不得传入敏感链接
	 */
	@ApiField("action_url")
	private String actionUrl;

	/**
	 * 下架时间，基本格式:yyyy-MM-dd HH:mm:ss，下架时间必须晚于上架时间，下架时间不得早于当前时间，下架状态的内容不能上架,上架状态下架时间不能为空，只有上架状态可以修改下架时间。
	 */
	@ApiField("campaign_end_time")
	private Date campaignEndTime;

	/**
	 * 活动类型：该活动是属于单品优惠，还是全场活动，单品优惠 SINGLE,全场优惠UNIVERSAL
	 */
	@ApiField("campaign_type")
	private String campaignType;

	/**
	 * 内容ID，调用koubei.marketing.campaign.retail.dm.create创建内容时返回的内容ID
	 */
	@ApiField("content_id")
	private String contentId;

	/**
	 * 优惠类型，全场优惠传入枚举值
比如：DISCOUNT(折扣),OFF(立减),CARD(集点),VOUCHER(代金),REDEMPTION(换购),EXCHANGE(兑换),GIFT(买赠),OTHERS(其他)
	 */
	@ApiField("coupon_type")
	private String couponType;

	/**
	 * 该活动的活动文案，主要涉及（活动时间、参与方式、活动力度），最多不得超过1024个字，注意：该字段需要过风控校验，不得传入敏感词
	 */
	@ApiField("description")
	private String description;

	/**
	 * 扩展备用信息，一些其他信息存入该字段
	 */
	@ApiField("ext_info")
	private String extInfo;

	/**
	 * 图片url，该图片id只有一个，由Isv传入，（通过alipay.offline.material.image.upload 接口上传视频/图片获取的资源id）(图片的宽高比为4:3)
	 */
	@ApiField("image_id")
	private String imageId;

	/**
	 * 品牌：该商品属于哪个牌子/该活动属于哪个商家（比如 海飞丝，统一，徐福记，立白......）
	 */
	@ApiField("item_brand")
	private String itemBrand;

	/**
	 * 该商品/活动所属类别（吃的:食品   面膜:个人洗护    拖把:家庭清洁）
	 */
	@ApiField("item_category")
	private String itemCategory;

	/**
	 * 商品编码，SKU或店内码，该编码由Isv系统传入
	 */
	@ApiField("item_code")
	private String itemCode;

	/**
	 * 商品名称，单品优惠时传入商品名称；全场活动时传入活动名称，注意：该字段需要过风控校验，不得传入敏感词
	 */
	@ApiField("item_name")
	private String itemName;

	/**
	 * 该商品/活动，是否是会员专享的，TRUE表示会员专享，FALSE表示非会员专享
	 */
	@ApiField("member_only")
	private String memberOnly;

	/**
	 * 适用外部门店id，传入该优惠适用口碑门店id，可以传入多个值，列表类型
	 */
	@ApiListField("shop_ids")
	@ApiField("string")
	private List<String> shopIds;

	public String getActionUrl() {
		return this.actionUrl;
	}
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public Date getCampaignEndTime() {
		return this.campaignEndTime;
	}
	public void setCampaignEndTime(Date campaignEndTime) {
		this.campaignEndTime = campaignEndTime;
	}

	public String getCampaignType() {
		return this.campaignType;
	}
	public void setCampaignType(String campaignType) {
		this.campaignType = campaignType;
	}

	public String getContentId() {
		return this.contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getCouponType() {
		return this.couponType;
	}
	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getExtInfo() {
		return this.extInfo;
	}
	public void setExtInfo(String extInfo) {
		this.extInfo = extInfo;
	}

	public String getImageId() {
		return this.imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getItemBrand() {
		return this.itemBrand;
	}
	public void setItemBrand(String itemBrand) {
		this.itemBrand = itemBrand;
	}

	public String getItemCategory() {
		return this.itemCategory;
	}
	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getItemCode() {
		return this.itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return this.itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getMemberOnly() {
		return this.memberOnly;
	}
	public void setMemberOnly(String memberOnly) {
		this.memberOnly = memberOnly;
	}

	public List<String> getShopIds() {
		return this.shopIds;
	}
	public void setShopIds(List<String> shopIds) {
		this.shopIds = shopIds;
	}

}
