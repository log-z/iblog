package com.log.blog.service.impl;

import com.log.blog.entity.User;
import com.log.blog.mapper.UserMapper;
import com.log.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void init(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean updateName(@NonNull String userId, @NonNull String newName) {
        User user = new User();
        user.setUserId(userId);
        user.setUserName(newName);
        try {
            userMapper.updateUser(user);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updatePassword(@NonNull String userId, @NonNull String oldPassword, @NonNull String newPassword) {
        try {
            User user = userMapper.getUserById(userId);
            if (user != null && passwordEncoder.matches(oldPassword, user.getUserPassword())) {
                User newUser = new User();
                newUser.setUserPassword(passwordEncoder.encode(newPassword));
                userMapper.updateUser(newUser);
                return true;
            }
        } catch (SQLException ignored) {
        }
        return false;
    }
}
