package com.log.blog.service;

import com.log.blog.dto.AdminParam;
import org.springframework.lang.NonNull;

public interface AdminService {
    boolean register(@NonNull AdminParam adminParam);
    String loginCheck(@NonNull AdminParam adminParam);
}
