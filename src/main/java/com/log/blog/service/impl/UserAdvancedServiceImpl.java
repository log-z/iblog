package com.log.blog.service.impl;

import com.log.blog.dto.Range;
import com.log.blog.entity.User;
import com.log.blog.mapper.UserMapper;
import com.log.blog.service.UserAdvancedService;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Service("userAdvancedService")
public class UserAdvancedServiceImpl extends UserServiceImpl implements UserAdvancedService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserAdvancedServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        super(userMapper, passwordEncoder);
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
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
                newUser.setUserId(userId);
                newUser.setUserPassword(passwordEncoder.encode(newPassword));
                userMapper.updateUser(newUser);
                return true;
            }
        } catch (SQLException ignored) {
        }
        return false;
    }

    @Override
    public List<User> getUsers(@NonNull Range range) {
        try {
            return userMapper.getAllUsers(range);
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public long getUsersCount() {
        try {
            return userMapper.getUsersCount();
        } catch (SQLException e) {
            return -1;
        }
    }
}
