package com.log.blog.dto;

import com.log.blog.dto.ValidatorGroup.Querying;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

public class PageRange {
    @PositiveOrZero(groups = Querying.class)
    private int pageNum = 1;

    @PositiveOrZero(groups = Querying.class)
    private int pageSize = 10;

    public PageRange() {
    }

    public PageRange(int pageNum) {
        this.pageNum = pageNum;
    }

    public PageRange(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageRange pageRange = (PageRange) o;
        return pageNum == pageRange.pageNum &&
                pageSize == pageRange.pageSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNum, pageSize);
    }

    @Override
    public String toString() {
        return "PageRange{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
