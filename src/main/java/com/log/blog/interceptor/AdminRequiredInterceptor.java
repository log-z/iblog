package com.log.blog.interceptor;

import com.log.blog.controller.AdminPublicController;
import com.log.blog.entity.Admin;
import com.log.blog.service.AdminAdvancedService;
import com.log.blog.utils.MappingUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Component
@Scope("prototype")
public class AdminRequiredInterceptor implements HandlerInterceptor {
    private final AdminAdvancedService adminAdvancedService;
    private String errorPath ;
    private Map<String, Set<String>> advancedExcludeMapping = Collections.emptyMap();

    public AdminRequiredInterceptor(AdminAdvancedService adminAdvancedService) {
        this.adminAdvancedService = adminAdvancedService;
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

        Object adminId = request.getSession().getAttribute(AdminPublicController.SESSION_KEY_ADMIN_IDENTITY);
        if (adminId instanceof String) {
            Admin currentAdmin = adminAdvancedService.getAdmin((String) adminId);
            if (currentAdmin != null) {
                request.setAttribute("currentAdmin", currentAdmin);
                return true;
            }
        }

        request.setAttribute("redirectPath", errorPath);
        request.getRequestDispatcher("/redirect").forward(request, response);
        return false;
    }
}
