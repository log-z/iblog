package com.log.blog.service;

import org.springframework.lang.NonNull;

public interface UserService {
    boolean updateName(@NonNull String userId, @NonNull String newName);
    boolean updatePassword(@NonNull String userId, @NonNull String oldPassword, @NonNull String newPassword);
}
