package com.log.blog.converter;

import com.log.blog.entity.User;
import com.log.blog.vo.UserVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class User2VOConverter implements Converter<User, UserVO> {
    private String contextPath = "";

    public void setContextPath(@Value("#{servletContext.contextPath}") String contextPath) {
        this.contextPath = contextPath == null ? "" : contextPath;
    }

    @Override
    public UserVO convert(User source) {
        UserVO userVO = new UserVO();
        userVO.setUserId(source.getUserId());
        userVO.setUserName(source.getUserName());
        userVO.setUserEmail(source.getUserEmail());
        userVO.setUserHomeUrl(contextPath + "/m/user#" + source.getUserId());
        return userVO;
    }
}
