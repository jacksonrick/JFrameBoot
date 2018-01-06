package com.jf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 查询基类【已弃用】
 *
 * @author rick
 */
public class BaseQuery {

    /**
     * 默认分页,false不分页
     */
    @JsonIgnore
    private Boolean page = true;

    /**
     * 页数
     * <p>如果要设定pageSize,必须先指定pageSize，后指定pageNo</p>
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
     * sql [start],limit
     */
    @JsonIgnore
    private Integer pageStart;

    /**
     * 根据此值，可以在一个Mapper里写多个SQL调用
     */
    @JsonIgnore
    private Integer sql;

    /**
     * 查询字段-通用
     */
    private String keywords;

    private String startDate;

    private String endDate;

    public BaseQuery() {
        if (page) {
            if (pageSize == null) {
                this.pageSize = 10;
            }
            if (pageNo == null) {
                this.pageNo = 1;
                this.pageStart = 0;
            }
        }
    }

    public Boolean getPage() {
        return page;
    }

    public void setPage(Boolean page) {
        this.page = page;
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
        if (pageSize == null) {
            this.pageSize = 10;
        }
        if (pageNo != null && pageNo > 0) {
            this.pageNo = pageNo;
            this.pageStart = (pageNo - 1) * this.pageSize;
        } else {
            this.pageNo = 1;
            this.pageStart = 0;
        }
    }

    public String getPageSort() {
        return pageSort;
    }

    public void setPageSort(String pageSort) {
        this.pageSort = pageSort;
    }

    public Integer getPageStart() {
        return pageStart;
    }

    public void setPageStart(Integer pageStart) {
        this.pageStart = pageStart;
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
