package com.log.blog.service;

import com.log.blog.entity.Admin;
import org.springframework.lang.NonNull;

public interface AdminAdvancedService extends AdminService {
    Admin getAdmin(@NonNull String adminId);
}
