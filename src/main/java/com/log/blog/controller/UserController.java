package com.log.blog.controller;

import com.log.blog.dto.UserParam;
import com.log.blog.entity.User;
import com.log.blog.interceptor.UserRequiredInterceptor;
import com.log.blog.service.UserAdvancedService;
import com.log.blog.utils.HtmlEscapeUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private final UserAdvancedService userAdvancedService;
    private final Validator passwordAgainValidator;

    public UserController(UserAdvancedService userAdvancedService,
                          @Qualifier("passwordAgainValidator") Validator passwordAgainValidator) {
        this.userAdvancedService = userAdvancedService;
        this.passwordAgainValidator = passwordAgainValidator;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(UserPublicController.SESSION_KEY_USER_IDENTITY);
        return "redirect:/login";
    }

    @GetMapping("/{userId:\\d{1,11}}/info")
    public String showInfo(
            @PathVariable String userId,
            @RequestAttribute(UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER) User user
    ) {
        if (user.getUserId().equals(userId))
            return "user-info.jsp";
        return null;
    }

//    @GetMapping("/{userId:\\d{1,11}}/update-name")
//    public String updateName(
//            @PathVariable String userId,
//            @RequestAttribute(UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER) User user,
//            Model model
//    ) {
//        if (user.getUserId().equals(userId)) {
//            UserParam userParam = new UserParam();
//            userParam.setUserName(user.getUserName());
//            model.addAttribute("form", HtmlEscapeUtils.escape(userParam));
//            return "user-update-name.jsp";
//        }
//        return null;  // 无权限
//    }

    @PostMapping("/{userId:\\d{1,11}}/update-name")
    public String updateName(
            @PathVariable String userId,
            @RequestAttribute(UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER) User user,
            @Validated(UserParam.Rename.class) @ModelAttribute("form") UserParam userParam,
            BindingResult errors,
            Model model
    ) {
        if (!errors.hasErrors()) {
            if (!user.getUserId().equals(userId)) {
                return null;  // 无权限
            } else {
                if (userAdvancedService.updateName(userId, userParam.getUserName()))
                    return "redirect:/{userId}/info";
                model.addAttribute("msg", "修改姓名失败。");
            }
        }
        return "user-update-name.jsp";
    }

    @GetMapping("/{userId:\\d{1,11}}/update-password")
    public String updatePassword(
            @PathVariable String userId,
            @RequestAttribute(UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER) User user,
            Model model
    ) {
        if (user.getUserId().equals(userId)) {
            model.addAttribute("form", new UserParam());
            return "user-update-password.jsp";
        }
        return null;  // 无权限
    }

    @PostMapping("/{userId:\\d{1,11}}/update-password")
    public String updatePassword(
            @PathVariable String userId,
            @RequestAttribute(UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER) User user,
            @Validated(UserParam.ResetPassword.class) @ModelAttribute("form") UserParam userParam,
            BindingResult errors,
            Model model
    ) {
        if (!errors.hasErrors()) {
            passwordAgainValidator.validate(userParam, errors);
            if (!errors.hasErrors()) {
                if (!user.getUserId().equals(userId))
                    return null;  // 无权限

                if (userAdvancedService.updatePassword(
                        userId, userParam.getOldUserPassword(), userParam.getUserPassword())) {
                    return "redirect:/{userId}/info";
                }
                model.addAttribute("msg", "修改密码失败。");
            }
        }
        return "user-update-password.jsp";
    }
}
