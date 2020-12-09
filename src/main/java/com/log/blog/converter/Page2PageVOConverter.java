package com.log.blog.converter;

import com.github.pagehelper.Page;
import com.log.blog.vo.PageVO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class Page2PageVOConverter implements Converter<Page<?>, PageVO> {
    @Override
    public PageVO convert(Page<?> page) {
        PageVO pageVO = new PageVO();
        pageVO.setPageNum(page.getPageNum());
        pageVO.setPageSize(page.getPageSize());
        pageVO.setTotal(page.getTotal());
        pageVO.setPages(page.getPages());
        return pageVO;
    }
}
