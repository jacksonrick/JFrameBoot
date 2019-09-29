package com.jf.entity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-07-17
 * Time: 09:58
 */
public class Pagination<T> {

    private List<T> data;
    private long pageIndex;
    private int totalPage;

    public Pagination() {
    }

    public Pagination(List<T> data, long pageIndex, int totalPage) {
        this.data = data;
        this.pageIndex = pageIndex;
        this.totalPage = totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "data=" + data +
                ", pageIndex=" + pageIndex +
                ", totalPage=" + totalPage +
                '}';
    }
}
