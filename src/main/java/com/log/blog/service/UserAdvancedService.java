package com.log.blog.service;

import com.log.blog.dto.UserParam;
import com.log.blog.entity.User;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserAdvancedService extends UserService {
    boolean deleteUser(@NonNull String userId);
    boolean updateName(@NonNull String userId, @NonNull String newName);
    boolean updatePassword(@NonNull String userId, @NonNull String oldPassword, @NonNull String newPassword);
    List<User> listUsers(@NonNull UserParam userParam);
}
