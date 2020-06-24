package com.log.blog.interceptor;

import com.log.blog.controller.AdminPublicController;
import com.log.blog.entity.Admin;
import com.log.blog.service.AdminService;
import com.log.blog.utils.HtmlEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminRequiredInterceptor implements HandlerInterceptor {
    private AdminService adminService;

    @Autowired
    public void init(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object adminId = request.getSession().getAttribute(AdminPublicController.SESSION_KEY_ADMIN_IDENTITY);
        if (adminId instanceof String) {
            Admin currentAdmin = adminService.getAdmin((String) adminId);
            if (currentAdmin != null) {
                request.setAttribute("currentAdmin", HtmlEscapeUtils.escape(currentAdmin));
                return true;
            }
        }

        request.getRequestDispatcher("/admin/error/403").forward(request, response);
        return false;
    }
}
