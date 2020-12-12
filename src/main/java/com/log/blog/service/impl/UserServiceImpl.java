package com.log.blog.service.impl;

import com.log.blog.dto.UserParam;
import com.log.blog.entity.User;
import com.log.blog.mapper.UserMapper;
import com.log.blog.service.UserService;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userBasicService")
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean register(@NonNull UserParam userParam) {
        String encode = passwordEncoder.encode(userParam.getUserPassword());
        userParam.setUserPassword(encode);
        return userMapper.insertUser(userParam);
    }

    @Override
    public String loginCheck(@NonNull UserParam userParam) {
        User target = userMapper.getUserByEmail(userParam.getUserEmail());
        if (target != null && passwordEncoder.matches(userParam.getUserPassword(), target.getUserPassword()))
            return target.getUserId();
        return null;
    }

    @Override
    public User getUser(@NonNull String userId) {
        return userMapper.getUserById(userId);
    }
}
