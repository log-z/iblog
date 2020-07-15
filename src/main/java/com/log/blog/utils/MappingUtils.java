package com.log.blog.utils;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MappingUtils {
    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    public static Map<String, Set<String>> parseMapping(Set<String> config) {
        Map<String, Set<String>> advancedExcludeMapping = new HashMap<>();
        config.forEach(itemStr -> {
            String[] item = itemStr.split("->", 2);
            String pattern = item[0].trim();
            String[] methodArray = item[1].split(",");

            Set<String> methodSet = Arrays.stream(methodArray)
                    .map(String::trim)
                    .collect(Collectors.toUnmodifiableSet());

            advancedExcludeMapping.put(pattern, methodSet);
        });
        return advancedExcludeMapping;
    }

    public static boolean match(Map<String, Set<String>> mapping, HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        for (Map.Entry<String, Set<String>> m : mapping.entrySet()) {
            String pattern = m.getKey();
            Set<String> methods = m.getValue();
            if (PATH_MATCHER.match(pattern, path) && methods.contains(method))
                return true;
        }
        return false;
    }
}
