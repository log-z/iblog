package com.log.blog.controller;

import com.log.blog.dto.UpdateNameForm;
import com.log.blog.dto.UpdatePasswordForm;
import com.log.blog.entity.User;
import com.log.blog.interceptor.UserRequiredInterceptor;
import com.log.blog.service.UserService;
import com.log.blog.utils.HtmlEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserService userService;
    private Validator passwordAgainValidator;

    @Autowired
    public void init(UserService userService, @Qualifier("passwordAgainValidator") Validator passwordAgainValidator) {
        this.userService = userService;
        this.passwordAgainValidator = passwordAgainValidator;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(UserPublicController.SESSION_KEY_USER_IDENTITY);
        return "redirect:/login";
    }

    @GetMapping("/{userId:\\d+}/info")
    public String showInfo(
            @PathVariable String userId,
            @RequestAttribute(UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER) User user
    ) {
        if (user.getUserId().equals(userId))
            return "user-info.jsp";
        return null;
    }

    @GetMapping("/{userId:\\d+}/update-name")
    public String updateName(
            @PathVariable String userId,
            @RequestAttribute(UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER) User user,
            Model model
    ) {
        if (user.getUserId().equals(userId)) {
            UpdateNameForm form = new UpdateNameForm();
            form.setName(user.getUserName());
            model.addAttribute("form", HtmlEscapeUtils.escape(form));
            return "user-update-name.jsp";
        }
        return null;  // 无权限
    }

    @PostMapping("/{userId:\\d+}/update-name")
    public String updateName(
            @PathVariable String userId,
            @RequestAttribute(UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER) User user,
            @Validated @ModelAttribute("form") UpdateNameForm form,
            BindingResult errors,
            Model model
    ) {
        if (!errors.hasErrors()) {
            if (!user.getUserId().equals(userId)) {
                return null;  // 无权限
            } else {
                if (userService.updateName(userId, form.getName()))
                    return "redirect:/{userId}/info";
                model.addAttribute("msg", "修改姓名失败。");
            }
        }
        return "user-update-name.jsp";
    }

    @GetMapping("/{userId:\\d+}/update-password")
    public String updatePassword(
            @PathVariable String userId,
            @RequestAttribute(UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER) User user,
            Model model
    ) {
        if (user.getUserId().equals(userId)) {
            model.addAttribute("form", new UpdatePasswordForm());
            return "user-update-password.jsp";
        }
        return null;  // 无权限
    }

    @PostMapping("/{userId:\\d+}/update-password")
    public String updatePassword(
            @PathVariable String userId,
            @RequestAttribute(UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER) User user,
            @Validated @ModelAttribute("form") UpdatePasswordForm form,
            BindingResult errors,
            Model model
    ) {
        if (!errors.hasErrors()) {
            passwordAgainValidator.validate(form, errors);
            if (!errors.hasErrors()) {
                if (!user.getUserId().equals(userId)) {
                    return null;  // 无权限
                } else {
                    if (userService.updatePassword(userId, form.getOldPassword(), form.getNewPassword()))
                        return "redirect:/{userId}/info";
                    model.addAttribute("msg", "修改密码失败。");
                }
            }
        }
        return "user-update-password.jsp";
    }
}
