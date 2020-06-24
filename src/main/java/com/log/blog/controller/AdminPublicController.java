package com.log.blog.controller;

import com.log.blog.dto.AdminRegister;
import com.log.blog.entity.Admin;
import com.log.blog.service.AdminPublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminPublicController {
    public final static String SESSION_KEY_ADMIN_IDENTITY = "adminIdentity";
    private AdminPublicService adminPublicService;

    @Autowired
    public void init(AdminPublicService adminPublicService) {
        this.adminPublicService = adminPublicService;
    }

    @GetMapping("/register")
    public String register() {
        return "admin-register.jsp";
    }

    @PostMapping(value = "/register")
    public String register(AdminRegister adminRegister, HttpSession session, Model model) {
        if (adminPublicService.register(adminRegister)) {
            session.setAttribute(SESSION_KEY_ADMIN_IDENTITY, adminRegister.getAdminId());
            return "redirect:/admin/login";
        } else {
            model.addAttribute("msg", "注册失败。");
            return "admin-register.jsp";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "admin-login.jsp";
    }

    @PostMapping("/login")
    public String login(Admin admin, HttpSession session, Model model) {
        String adminId = adminPublicService.loginCheck(admin);
        if (adminId != null) {
            session.setAttribute(SESSION_KEY_ADMIN_IDENTITY, adminId);
            return "redirect:/admin";
        } else {
            model.addAttribute("msg", "登陆失败。");
            return "admin-login.jsp";
        }
    }

    @RequestMapping("/error/403")
    public String error403(Model model) {
        model.addAttribute("refreshPath", "/admin/login");
        return "error-403.jsp";
    }
}
