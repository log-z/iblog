package com.log.blog.service.impl;

import com.log.blog.dto.AdminParam;
import com.log.blog.entity.Admin;
import com.log.blog.mapper.AdminMapper;
import com.log.blog.service.AdminAdvancedService;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("adminAdvancedService")
public class AdminAdvancedServiceImpl extends AdminServiceImpl implements AdminAdvancedService {
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminAdvancedServiceImpl(PasswordEncoder passwordEncoder, AdminMapper adminMapper) {
        super(passwordEncoder, adminMapper);
        this.adminMapper = adminMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Admin getAdmin(@NonNull String adminId) {
        return adminMapper.getAdminById(adminId);
    }

    @Override
    public boolean updateName(String adminId, String newName) {
        AdminParam adminParam = new AdminParam();
        adminParam.setAdminId(adminId);
        adminParam.setAdminName(newName);
        return adminMapper.updateAdmin(adminParam);
    }

    @Override
    public boolean updatePassword(String adminId, String oldPassword, String newPassword) {
        Admin admin = adminMapper.getAdminById(adminId);
        if (admin != null && passwordEncoder.matches(oldPassword, admin.getAdminPassword())) {
            AdminParam adminParam = new AdminParam();
            adminParam.setAdminId(adminId);
            adminParam.setAdminPassword(newPassword);
            return adminMapper.updateAdmin(adminParam);
        } else {
            return false;
        }
    }
}
