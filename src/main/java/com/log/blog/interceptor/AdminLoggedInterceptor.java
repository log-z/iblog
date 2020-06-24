package com.log.blog.interceptor;

import com.log.blog.controller.AdminPublicController;
import com.log.blog.entity.Admin;
import com.log.blog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AdminLoggedInterceptor implements HandlerInterceptor {
    private AdminService adminService;

    @Autowired
    public void init(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object adminId = session.getAttribute(AdminPublicController.SESSION_KEY_ADMIN_IDENTITY);
        if (adminId instanceof String) {
            Admin currentAdmin = adminService.getAdmin((String) adminId);
            if (currentAdmin != null) {
                request.setAttribute("redirectPath", "/admin");
                request.getRequestDispatcher("/redirect").forward(request, response);
                return false;
            } else {
                session.removeAttribute(AdminPublicController.SESSION_KEY_ADMIN_IDENTITY);
            }
        }
        return true;
    }
}
