package com.log.blog.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.log.blog.dto.AdminRegisterForm;
import com.log.blog.entity.Admin;
import com.log.blog.service.AdminService;
import com.log.blog.validator.PasswordAgainValidator;
import com.log.blog.vo.rest.RestResult;
import com.log.blog.vo.rest.View;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.log.blog.controller.AdminPublicController.SESSION_KEY_ADMIN_IDENTITY;

@RestController
@RequestMapping("/api/admin")
public class AdminPublicRestController {
    private final AdminService adminService;
    private final Validator passwordAgainValidator;

    public AdminPublicRestController(@Qualifier("adminBasicService") AdminService adminService,
                                     PasswordAgainValidator passwordAgainValidator) {
        this.adminService = adminService;
        this.passwordAgainValidator = passwordAgainValidator;
    }

    @PostMapping
    @JsonView(View.Guest.class)
    public RestResult register(
            @Validated(Admin.Register.class) AdminRegisterForm form,
            BindingResult errors,
            @ModelAttribute RestResult result,
            HttpServletResponse response
    ) {
        if (!errors.hasErrors()) {
            passwordAgainValidator.validate(form, errors);
        }
        if (!errors.hasErrors() && adminService.register(form)) {
            response.setStatus(201);
            return result.setDateMessage("register.successful", null);
        } else {
            response.setStatus(400);
            errors.reject("register.failed");
            return result.setErrors(errors);
        }
    }

    @PostMapping("/session")
    @JsonView(View.Guest.class)
    public RestResult login(
            @Validated(Admin.Login.class) Admin admin,
            BindingResult errors,
            HttpSession session,
            @ModelAttribute RestResult result,
            HttpServletResponse response
    ) {
        if (!errors.hasErrors()) {
            String adminId = adminService.loginCheck(admin);
            if (adminId != null) {
                // 登陆成功
                response.setStatus(201);
                session.setAttribute(SESSION_KEY_ADMIN_IDENTITY, adminId);
                return result.setDateMessage("login.successful", null);
            } else {
                // 账号或密码错误
                response.setStatus(401);
                return result.setError(null, "login.authorization.failure", null);
            }
        }
        // 表单格式错误
        response.setStatus(400);
        errors.reject("login.failed");
        return result.setErrors(errors);
    }

    @RequestMapping("/error401")
    @JsonView(View.Guest.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RestResult error401(@ModelAttribute RestResult result) {
        return result.setError(null, "error.admin.unauthorized", null);
    }
}
