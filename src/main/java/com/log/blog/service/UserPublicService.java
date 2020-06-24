package com.log.blog.service;

import com.log.blog.entity.User;
import org.springframework.lang.NonNull;

public interface UserPublicService {
    boolean register(@NonNull User user);
    String loginCheck(@NonNull User user);
    User getUser(@NonNull String userId);
}
