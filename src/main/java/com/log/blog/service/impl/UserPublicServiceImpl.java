package com.log.blog.service.impl;

import com.log.blog.entity.User;
import com.log.blog.mapper.UserMapper;
import com.log.blog.service.UserPublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserPublicServiceImpl implements UserPublicService {
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void init(PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public boolean register(@NonNull User user) {
        try {
            String encode = passwordEncoder.encode(user.getUserPassword());
            user.setUserPassword(encode);
            userMapper.insertUser(user);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public String loginCheck(@NonNull User user) {
        try {
            User target = userMapper.getUserByEmail(user.getUserEmail());
            if (target != null && passwordEncoder.matches(user.getUserPassword(), target.getUserPassword()))
                return target.getUserId();
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public User getUser(@NonNull String userId) {
        try {
            return userMapper.getUserById(userId);
        } catch (SQLException e) {
            return null;
        }
    }
}
