package com.log.blog.interceptor;

import com.log.blog.controller.UserPublicController;
import com.log.blog.entity.User;
import com.log.blog.service.UserPublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class UserLoggedInterceptor implements HandlerInterceptor {
    private UserPublicService userPublicService;

    @Autowired
    public void init(UserPublicService userPublicService) {
        this.userPublicService = userPublicService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object userId = session.getAttribute(UserPublicController.SESSION_KEY_USER_IDENTITY);
        if (userId instanceof String) {
            User currentUser = userPublicService.getUser((String) userId);
            if (currentUser != null) {
                request.setAttribute("redirectPath", "/" + userId);
                request.getRequestDispatcher("/redirect").forward(request, response);
                return false;
            } else {
                session.removeAttribute(UserPublicController.SESSION_KEY_USER_IDENTITY);
            }
        }
        return true;
    }
}
