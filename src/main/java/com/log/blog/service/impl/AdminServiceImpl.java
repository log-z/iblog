package com.log.blog.service.impl;

import com.log.blog.dto.Range;
import com.log.blog.entity.Admin;
import com.log.blog.entity.Article;
import com.log.blog.entity.User;
import com.log.blog.mapper.AdminMapper;
import com.log.blog.mapper.ArticleMapper;
import com.log.blog.mapper.UserMapper;
import com.log.blog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private AdminMapper adminMapper;
    private UserMapper userMapper;
    private ArticleMapper articleMapper;

    @Autowired
    public void init(AdminMapper adminMapper, UserMapper userMapper, ArticleMapper articleMapper) {
        this.adminMapper = adminMapper;
        this.userMapper = userMapper;
        this.articleMapper = articleMapper;
    }

    @Override
    public Admin getAdmin(@NonNull String adminId) {
        try {
            return adminMapper.getAdminById(adminId);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<User> getUsers(@NonNull Range range) {
        try {
            return userMapper.getAllUser(range);
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean deleteUser(@NonNull String userId) {
        try {
            userMapper.deleteUser(userId);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteArticle(@NonNull String articleId) {
        try {
            articleMapper.deleteArticle(articleId);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
