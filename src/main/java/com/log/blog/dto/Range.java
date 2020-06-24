package com.log.blog.dto;

import java.util.Objects;

public class Range {
    private int num;
    private int offset;

    public Range(int num) {
        this.num = num;
    }

    public Range(int num, int offset) {
        this.num = num;
        this.offset = offset;
    }

    public Range(Integer num, int numDefault, Integer offset, int offsetDefault) {
        this.num = num == null ? numDefault : num;
        this.offset = offset == null ? offsetDefault : offset;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Range range = (Range) o;
        return num == range.num &&
                offset == range.offset;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, offset);
    }
}
