package com.log.blog.interceptor;

import com.log.blog.controller.UserPublicController;
import com.log.blog.entity.User;
import com.log.blog.service.UserService;
import com.log.blog.utils.HtmlEscapeUtils;
import com.log.blog.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Component
@Scope("prototype")
public class UserOptionalInterceptor implements HandlerInterceptor {
    private final UserService userService;
    private Map<String, Set<String>> advancedMapping = Collections.emptyMap();

    public UserOptionalInterceptor(@Qualifier("userBasicService") UserService userService) {
        this.userService = userService;
    }

    public void setAdvancedMapping(Set<String> config) {
        this.advancedMapping = MappingUtils.parseMapping(config);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!MappingUtils.match(advancedMapping, request))
            return true;

        HttpSession session = request.getSession();
        Object userId = session.getAttribute(UserPublicController.SESSION_KEY_USER_IDENTITY);
        if (userId instanceof String) {
            User currentUser = userService.getUser((String) userId);
            if (currentUser != null) {
                request.setAttribute(UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER, HtmlEscapeUtils.escape(currentUser));
            } else {
                session.removeAttribute(UserPublicController.SESSION_KEY_USER_IDENTITY);
            }
        }
        return true;
    }
}
