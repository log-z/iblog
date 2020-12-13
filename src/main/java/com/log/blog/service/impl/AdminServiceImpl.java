package com.log.blog.service.impl;

import com.log.blog.dto.AdminParam;
import com.log.blog.entity.Admin;
import com.log.blog.mapper.AdminMapper;
import com.log.blog.service.AdminService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public boolean register(@NonNull AdminParam adminParam) {
        String encode = passwordEncoder.encode(adminParam.getAdminPassword());
        adminParam.setAdminPassword(encode);
        return adminMapper.insertAdmin(adminParam);
    }

    @Override
    public String loginCheck(@NonNull AdminParam adminParam) {
        Admin target = adminMapper.getAdminByEmail(adminParam.getAdminEmail());
        if (target != null && passwordEncoder.matches(adminParam.getAdminPassword(), target.getAdminPassword()))
            return target.getAdminId();
        return null;
    }
}
