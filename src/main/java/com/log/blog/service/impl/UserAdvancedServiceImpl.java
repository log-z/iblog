package com.log.blog.service.impl;

import com.log.blog.dto.UserParam;
import com.log.blog.entity.User;
import com.log.blog.mapper.UserMapper;
import com.log.blog.service.UserAdvancedService;
import com.log.blog.utils.PageUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        return userMapper.deleteUser(userId);
    }

    @Override
    public boolean updateName(@NonNull String userId, @NonNull String newName) {
        UserParam userParam = new UserParam();
        userParam.setUserId(userId);
        userParam.setUserName(newName);
        return userMapper.updateUser(userParam);
    }

    @Override
    public boolean updatePassword(@NonNull String userId, @NonNull String oldPassword, @NonNull String newPassword) {
        User user = userMapper.getUserById(userId);
        if (user != null && passwordEncoder.matches(oldPassword, user.getUserPassword())) {
            UserParam userParam = new UserParam();
            userParam.setUserId(userId);
            userParam.setUserPassword(passwordEncoder.encode(newPassword));
            return userMapper.updateUser(userParam);
        } else {
            return false;
        }
    }

    @Override
    public List<User> listUsers(@NonNull UserParam userParam) {
        PageUtils.startPage(userParam.getPageRange());
        if (userParam.isFuzzySearch())
            return userMapper.findUsers(userParam);
        return userMapper.listUsers(userParam);
    }

}
