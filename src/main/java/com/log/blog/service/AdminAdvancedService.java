package com.log.blog.service;

import com.log.blog.entity.Admin;
import org.springframework.lang.NonNull;

public interface AdminAdvancedService extends AdminService {
    Admin getAdmin(@NonNull String adminId);
    boolean updateName(@NonNull String adminId, @NonNull String newName);
    boolean updatePassword(@NonNull String adminId, @NonNull String oldPassword, @NonNull String newPassword);
}
