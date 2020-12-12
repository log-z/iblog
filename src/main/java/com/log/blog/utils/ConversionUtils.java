package com.log.blog.utils;

import org.springframework.core.convert.ConversionService;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.stream.Collectors;

public class ConversionUtils {
    public static <T> List<T> convertList(ConversionService conversionService, @NonNull List<?> sourceList,
                                          Class<T> targetType) {
        return sourceList.stream()
                .map(source -> conversionService.convert(source, targetType))
                .collect(Collectors.toList());
    }
}
