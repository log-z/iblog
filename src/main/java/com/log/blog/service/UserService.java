package com.log.blog.service;

import com.log.blog.dto.UserParam;
import com.log.blog.entity.User;
import org.springframework.lang.NonNull;

public interface UserService {
    boolean register(@NonNull UserParam userParam);
    String loginCheck(@NonNull UserParam userParam);
    User getUser(@NonNull String userId);
}
