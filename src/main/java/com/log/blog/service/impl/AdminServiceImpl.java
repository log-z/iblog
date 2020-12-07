package com.log.blog.service.impl;

import com.log.blog.entity.Admin;
import com.log.blog.mapper.AdminMapper;
import com.log.blog.service.AdminService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service("adminBasicService")
@DependsOn({"passwordEncoder", "adminMapper"})
public class AdminServiceImpl implements AdminService {
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(PasswordEncoder passwordEncoder, AdminMapper adminMapper) {
        this.passwordEncoder = passwordEncoder;
        this.adminMapper = adminMapper;
    }

    @Override
    public boolean register(@NonNull Admin admin) {
        try {
            String encode = passwordEncoder.encode(admin.getAdminPassword());
            admin.setAdminPassword(encode);
            adminMapper.insertAdmin(admin);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public String loginCheck(@NonNull Admin admin) {
        try {
            Admin target = adminMapper.getAdminByEmail(admin.getAdminEmail());
            if (target != null && passwordEncoder.matches(admin.getAdminPassword(), target.getAdminPassword()))
                return target.getAdminId();
            return null;
        } catch (SQLException e) {
            return null;
        }
    }
}
