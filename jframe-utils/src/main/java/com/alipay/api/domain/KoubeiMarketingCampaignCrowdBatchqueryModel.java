package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 口碑商户人群组列表查询接口
 *
 * @author auto create
 * @since 1.0, 2017-02-15 16:31:23
 */
public class KoubeiMarketingCampaignCrowdBatchqueryModel extends AlipayObject {

	private static final long serialVersionUID = 5729419562845729594L;

	/**
	 * 人群名称
	 */
	@ApiField("name")
	private String name;

	/**
	 * 分页页码，从1开始计数,如果不填写默认为1
	 */
	@ApiField("page_number")
	private String pageNumber;

	/**
	 * 分页大小，每页显示的数目，如果不填写默认为20
	 */
	@ApiField("page_size")
	private String pageSize;

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPageNumber() {
		return this.pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getPageSize() {
		return this.pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

}
