package com.log.blog.converter;

import com.log.blog.entity.User;
import com.log.blog.vo.RestUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class User2RestUserConverter implements Converter<User, RestUser> {
    private String contextPath = "";

    public void setContextPath(@Value("#{servletContext.contextPath}") String contextPath) {
        this.contextPath = contextPath == null ? "" : contextPath;
    }

    @Override
    public RestUser convert(User source) {
        RestUser restUser = new RestUser();
        restUser.setUserId(source.getUserId());
        restUser.setUserName(source.getUserName());
        restUser.setUserEmail(source.getUserEmail());
        restUser.setUserHomeUrl(contextPath + "/m/user#" + source.getUserId());
        return restUser;
    }
}
