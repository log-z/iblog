package com.log.blog.service;

import com.log.blog.entity.Admin;
import org.springframework.lang.NonNull;

public interface AdminPublicService {
    boolean register(@NonNull Admin admin);
    String loginCheck(@NonNull Admin admin);
}
