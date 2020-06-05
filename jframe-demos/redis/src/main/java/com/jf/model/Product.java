package com.jf.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2020-06-05
 * Time: 09:29
 */
public class Product {

    private Long id;

    private String name;

    private Boolean disabled;

    private BigDecimal curMoney;

    private Date createTime;

    private List<String> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public BigDecimal getCurMoney() {
        return curMoney;
    }

    public void setCurMoney(BigDecimal curMoney) {
        this.curMoney = curMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", disabled=" + disabled +
                ", curMoney=" + curMoney +
                ", createTime=" + createTime +
                ", items=" + items +
                '}';
    }
}
