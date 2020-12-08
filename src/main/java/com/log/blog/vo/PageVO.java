package com.log.blog.vo;

import com.fasterxml.jackson.annotation.JsonView;

public class PageVO {
    @JsonView(View.Base.class)
    private int pageNum = 1;

    @JsonView(View.Base.class)
    private int pageSize = 10;

    @JsonView(View.Base.class)
    private long total;

    @JsonView(View.Base.class)
    private int pages;

    public PageVO() {
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
