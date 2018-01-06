package com.jf.page;

import com.github.pagehelper.PageInfo;

import java.io.IOException;

/**
 * 分页标签处理类【已弃用】
 *
 * @author rick
 * @version 1.1
 */
public class PaginationTag {

    // 分页数据
    private PageInfo page;
    // 查询表单id
    private String queryForm = "queryForm";

    public void doTag() throws Exception {

    }

    public PageInfo getPage() {
        return page;
    }

    public void setPage(PageInfo page) {
        this.page = page;
    }

    public String getQueryForm() {
        return queryForm;
    }

    public void setQueryForm(String queryForm) {
        this.queryForm = queryForm;
    }

}
