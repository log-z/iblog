package com.log.blog.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.log.blog.dto.PageRange;

public class PageUtils {
    public static <E> Page<E> startPage(PageRange pageRange) {
        if (pageRange == null) {
            return null;
        } else {
            return PageHelper.startPage(pageRange.getPageNum(), pageRange.getPageSize());
        }
    }
}
