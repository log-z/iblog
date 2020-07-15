package com.log.blog.vo.rest;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.Objects;

public class RestRange {
    @JsonView(View.Base.class)
    private long num;

    @JsonView(View.Base.class)
    private long offset;

    @JsonView(View.Base.class)
    private long total;

    public RestRange() {
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestRange restRange = (RestRange) o;
        return num == restRange.num &&
                offset == restRange.offset &&
                total == restRange.total;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, offset, total);
    }
}
