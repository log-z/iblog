package com.log.blog.controller;

import com.log.blog.dto.AdminRegister;
import com.log.blog.entity.Admin;
import com.log.blog.service.AdminPublicService;
import com.log.blog.utils.HtmlEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminPublicController {
    public final static String SESSION_KEY_ADMIN_IDENTITY = "adminIdentity";
    private AdminPublicService adminPublicService;
    private Validator passwordAgainValidator;

    @Autowired
    public void init(AdminPublicService adminPublicService, @Qualifier("passwordAgainValidator") Validator passwordAgainValidator) {
        this.adminPublicService = adminPublicService;
        this.passwordAgainValidator = passwordAgainValidator;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("adminRegister", new AdminRegister());
        return "admin-register.jsp";
    }

    @PostMapping(value = "/register")
    public String register(
            @Validated(Admin.register.class) AdminRegister adminRegister,
            BindingResult errors,
            HttpSession session,
            Model model
    ) {
        if (!errors.hasErrors()) {
            passwordAgainValidator.validate(adminRegister, errors);
        }
        if (!errors.hasErrors() && adminPublicService.register(adminRegister)) {
            session.setAttribute(SESSION_KEY_ADMIN_IDENTITY, adminRegister.getAdminId());
            return "redirect:/admin/login";
        } else {
            model.addAttribute("adminRegister", HtmlEscapeUtils.escape(adminRegister));
            return "admin-register.jsp";
        }
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin-login.jsp";
    }

    @PostMapping("/login")
    public String login(
            @Validated(Admin.login.class) Admin admin,
            BindingResult errors,
            HttpSession session,
            Model model
    ) {
        if (!errors.hasErrors()) {
            String adminId = adminPublicService.loginCheck(admin);
            if (adminId != null) {
                session.setAttribute(SESSION_KEY_ADMIN_IDENTITY, adminId);
                return "redirect:/admin";
            }
        }
        model.addAttribute("admin", HtmlEscapeUtils.escape(admin));
        return "admin-login.jsp";
    }

    @RequestMapping("/error/403")
    public String error403(Model model) {
        model.addAttribute("refreshPath", "/admin/login");
        return "error-403.jsp";
    }
}
