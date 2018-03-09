package com.jf.model.custom;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 查询基类
 * <p>pagehelper</p>
 *
 * @author rick
 */
public class BaseVo {

    /**
     * 默认不分页
     */
    @JsonIgnore
    private Boolean page = false;

    /**
     * 每页数量
     */
    @JsonIgnore
    private Integer pageSize;

    /**
     * 页码
     */
    @JsonIgnore
    private Integer pageNo;

    /**
     * 排序字段
     */
    @JsonIgnore
    private String pageSort;

    /**
     * 根据此值，可以在一个Mapper里写多个SQL调用
     */
    @JsonIgnore
    private Integer sql;

    /**
     * 查询字段-通用-关键字
     */
    private String keywords;
    /**
     * 查询字段-开始时间
     */
    private String startDate;
    /**
     * 查询字段-结束时间
     */
    private String endDate;

    public BaseVo() {

    }

    public Boolean getPage() {
        return page;
    }

    /**
     * 默认不分页，分页指定默认页码：1
     *
     * @param page
     */
    public void setPage(Boolean page) {
        this.page = page;
        if (page) { // page = true，分页
            if (this.pageNo == null) {
                this.pageNo = 1;
            }
            if (this.pageSize == null) {
                this.pageSize = 20;
            }
        }
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSort() {
        return pageSort;
    }

    public void setPageSort(String pageSort) {
        this.pageSort = pageSort;
    }

    public Integer getSql() {
        return sql;
    }

    public void setSql(Integer sql) {
        this.sql = sql;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
