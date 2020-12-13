package com.log.blog.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.log.blog.dto.AdminParam;
import com.log.blog.service.AdminService;
import com.log.blog.validator.PasswordAgainValidator;
import com.log.blog.vo.RestResult;
import com.log.blog.vo.View;
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
            @Validated(AdminParam.Register.class) AdminParam adminParam,
            BindingResult errors,
            @ModelAttribute RestResult result,
            HttpServletResponse response
    ) {
        if (!errors.hasErrors()) {
            passwordAgainValidator.validate(adminParam, errors);
        }
        if (!errors.hasErrors() && adminService.register(adminParam)) {
            response.setStatus(HttpStatus.CREATED.value());
            return result.setDateMessage("register.successful", null);
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            errors.reject("register.failed");
            return result.setErrors(errors);
        }
    }

    @PostMapping("/session")
    @JsonView(View.Guest.class)
    public RestResult login(
            @Validated(AdminParam.Login.class) AdminParam adminParam,
            BindingResult errors,
            HttpSession session,
            @ModelAttribute RestResult result,
            HttpServletResponse response
    ) {
        if (!errors.hasErrors()) {
            String adminId = adminService.loginCheck(adminParam);
            if (adminId != null) {
                // 登陆成功
                response.setStatus(HttpStatus.CREATED.value());
                session.setAttribute(SESSION_KEY_ADMIN_IDENTITY, adminId);
                return result.setDateMessage("login.successful", null);
            } else {
                // 账号或密码错误
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return result.setError(null, "login.authorization.failure", null);
            }
        }
        // 表单格式错误
        response.setStatus(HttpStatus.BAD_REQUEST.value());
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
