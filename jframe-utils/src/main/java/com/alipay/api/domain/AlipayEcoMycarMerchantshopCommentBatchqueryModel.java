package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 分页查询评论信息
 *
 * @author auto create
 * @since 1.0, 2016-11-16 10:02:20
 */
public class AlipayEcoMycarMerchantshopCommentBatchqueryModel extends AlipayObject {

	private static final long serialVersionUID = 8117795849132831159L;

	/**
	 * 当前页号（从1开始）
	 */
	@ApiField("page_num")
	private Long pageNum;

	/**
	 * 分页数量，每页不超过100条。
	 */
	@ApiField("page_size")
	private Long pageSize;

	/**
	 * 门店id
	 */
	@ApiField("shop_id")
	private Long shopId;

	public Long getPageNum() {
		return this.pageNum;
	}
	public void setPageNum(Long pageNum) {
		this.pageNum = pageNum;
	}

	public Long getPageSize() {
		return this.pageSize;
	}
	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	public Long getShopId() {
		return this.shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

}
