package com.log.blog.converter;

import com.log.blog.entity.Admin;
import com.log.blog.vo.AdminVO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class Admin2VOConverter implements Converter<Admin, AdminVO> {
    @Override
    public AdminVO convert(Admin source) {
        AdminVO adminVO = new AdminVO();
        adminVO.setAdminId(source.getAdminId());
        adminVO.setAdminName(source.getAdminName());
        adminVO.setAdminEmail(source.getAdminEmail());
        return adminVO;
    }
}
