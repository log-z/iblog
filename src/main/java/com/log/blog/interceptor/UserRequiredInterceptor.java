package com.log.blog.interceptor;

import com.log.blog.controller.UserPublicController;
import com.log.blog.entity.User;
import com.log.blog.service.UserService;
import com.log.blog.utils.HtmlEscapeUtils;
import com.log.blog.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Component
@Scope("prototype")
public class UserRequiredInterceptor implements HandlerInterceptor {
    public static final String REQUEST_KEY_CURRENT_USER = "currentUser";
    private UserService userService;
    private String errorPath;
    private Map<String, Set<String>> advancedExcludeMapping = Collections.emptyMap();

    @Autowired
    public void init(@Qualifier("userBasicService") UserService userService) {
        this.userService = userService;
    }

    public void setErrorPath(String errorPath) {
        this.errorPath = errorPath;
    }

    public void setAdvancedExcludeMapping(Set<String> config) {
        this.advancedExcludeMapping = MappingUtils.parseMapping(config);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (MappingUtils.match(advancedExcludeMapping, request))
            return true;

        HttpSession session = request.getSession();
        Object userId = session.getAttribute(UserPublicController.SESSION_KEY_USER_IDENTITY);
        if (userId instanceof String) {
            User currentUser = userService.getUser((String) userId);
            if (currentUser != null) {
                request.setAttribute(REQUEST_KEY_CURRENT_USER, HtmlEscapeUtils.escape(currentUser));
                return true;
            } else {
                session.removeAttribute(UserPublicController.SESSION_KEY_USER_IDENTITY);
            }
        }

        if (errorPath == null) {
            throw HttpClientErrorException.create(HttpStatus.UNAUTHORIZED, "", new HttpHeaders(), null, null);
        }
        request.setAttribute("redirectPath", errorPath);
        request.getRequestDispatcher("/redirect").forward(request, response);
        return false;
    }
}
