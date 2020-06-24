package com.log.blog.controller;

import com.log.blog.entity.User;
import com.log.blog.interceptor.UserRequiredInterceptor;
import com.log.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public void init(UserService userService) {
        this.userService = userService;
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
            @RequestAttribute(UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER) User user
    ) {
        if (user.getUserId().equals(userId))
            return "user-update-name.jsp";
        return null;
    }

    @PostMapping("/{userId:\\d+}/update-name")
    public String updateName(
            @PathVariable String userId,
            @RequestAttribute(UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER) User user,
            String userName,
            Model model
    ) {
        if (user.getUserId().equals(userId)) {
            if (userService.updateName(userId, userName)) {
                return "redirect:/{userId}/info";
            } else {
                model.addAttribute("msg", "修改姓名失败。");
                return "user-update-name.jsp";
            }
        }
        return null;
    }

    @GetMapping("/{userId:\\d+}/update-password")
    public String updatePassword(
            @PathVariable String userId,
            @RequestAttribute(UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER) User user
    ) {
        if (user.getUserId().equals(userId))
            return "user-update-password.jsp";
        return null;
    }

    @PostMapping("/{userId:\\d+}/update-password")
    public String updatePassword(
            @PathVariable String userId,
            @RequestAttribute(UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER) User user,
            String oldPassword,
            String newPassword,
            Model model
    ) {
        if (user.getUserId().equals(userId)) {
            if (userService.updatePassword(userId, oldPassword, newPassword)) {
                return "redirect:/{userId}/info";
            } else {
                model.addAttribute("msg", "修改密码失败。");
                return "user-update-password.jsp";
            }
        }
        return null;
    }
}
