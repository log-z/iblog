package com.log.blog.converter;

import com.log.blog.entity.Admin;
import com.log.blog.vo.RestAdmin;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class Admin2RestAdminConverter implements Converter<Admin, RestAdmin> {
    @Override
    public RestAdmin convert(Admin source) {
        RestAdmin restAdmin = new RestAdmin();
        restAdmin.setAdminId(source.getAdminId());
        restAdmin.setAdminName(source.getAdminName());
        restAdmin.setAdminEmail(source.getAdminEmail());
        return restAdmin;
    }
}
