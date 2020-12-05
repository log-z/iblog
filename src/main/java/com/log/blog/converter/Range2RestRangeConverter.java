package com.log.blog.converter;

import com.log.blog.dto.Range;
import com.log.blog.vo.rest.RestRange;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class Range2RestRangeConverter implements Converter<Range, RestRange> {
    @Override
    public RestRange convert(Range source) {
        RestRange range = new RestRange();
        range.setNum(source.getNum());
        range.setOffset(source.getOffset());
        return range;
    }
}
