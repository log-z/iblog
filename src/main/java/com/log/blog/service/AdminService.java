package com.log.blog.service;

import com.log.blog.dto.Range;
import com.log.blog.entity.Admin;
import com.log.blog.entity.Article;
import com.log.blog.entity.User;
import org.springframework.lang.NonNull;

import java.util.List;

public interface AdminService {
    Admin getAdmin(@NonNull String adminId);
    List<User> getUsers(@NonNull Range range);
    boolean deleteUser(@NonNull String userId);
    boolean deleteArticle(@NonNull String articleId);
}
