package com.log.blog.service.impl;

import com.log.blog.entity.Admin;
import com.log.blog.mapper.AdminMapper;
import com.log.blog.service.AdminAdvancedService;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service("adminAdvancedService")
public class AdminAdvancedServiceImpl extends AdminServiceImpl implements AdminAdvancedService {
    private final AdminMapper adminMapper;

    public AdminAdvancedServiceImpl(PasswordEncoder passwordEncoder, AdminMapper adminMapper) {
        super(passwordEncoder, adminMapper);
        this.adminMapper = adminMapper;
    }

    @Override
    public Admin getAdmin(@NonNull String adminId) {
        try {
            return adminMapper.getAdminById(adminId);
        } catch (SQLException e) {
            return null;
        }
    }
}
