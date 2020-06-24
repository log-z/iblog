package com.log.blog.controller;

import com.log.blog.dto.Range;
import com.log.blog.dto.UserRegister;
import com.log.blog.entity.Article;
import com.log.blog.entity.User;
import com.log.blog.interceptor.UserRequiredInterceptor;
import com.log.blog.service.ArticlePublicService;
import com.log.blog.service.UserPublicService;
import com.log.blog.utils.HtmlEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserPublicController {
    public static final String SESSION_KEY_USER_IDENTITY = "userIdentity";
    private static final int LIST_ITEM_NUMBER = 10;
    private UserPublicService userPublicService;
    private ArticlePublicService articlePublicService;

    @Autowired
    public void init(UserPublicService userPublicService, ArticlePublicService articlePublicService) {
        this.userPublicService = userPublicService;
        this.articlePublicService = articlePublicService;
    }

    @GetMapping("/register")
    public String register() {
        return "user-register.jsp";
    }

    @PostMapping(value = "/register")
    public String register(UserRegister userRegister, HttpSession session, Model model) {
        if (userPublicService.register(userRegister)) {
            session.setAttribute(SESSION_KEY_USER_IDENTITY, userRegister.getUserId());
            return "redirect:/login";
        } else {
            model.addAttribute("msg", "注册失败。");
            return "user-register.jsp";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "user-login.jsp";
    }

    @PostMapping("/login")
    public String login(User user, HttpSession session, Model model) {
        String userId = userPublicService.loginCheck(user);
        if (userId != null) {
            session.setAttribute(SESSION_KEY_USER_IDENTITY, userId);
            return "redirect:/"+ userId;
        } else {
            model.addAttribute("msg", "登陆失败。");
            return "user-login.jsp";
        }
    }

    @GetMapping("/{targetUserId:\\d+}")
    public String home(
            @PathVariable String targetUserId,
            @RequestAttribute(value = UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER, required = false) User currentUser,
            @RequestParam(required = false) Integer num,
            @RequestParam(required = false) Integer offset,
            Model model
    ) {
        User targetUser = userPublicService.getUser(targetUserId);
        if (targetUser == null) return null;
        model.addAttribute("targetUser", HtmlEscapeUtils.escape(targetUser));

        Article feature = new Article();
        feature.setAuthorId(targetUserId);
        Range range = new Range(num, LIST_ITEM_NUMBER, offset, 0);
        List<Article> articles = articlePublicService.search(feature, range);
        model.addAttribute("articles", HtmlEscapeUtils.escapeArticles(articles));

        boolean editable = false;
        if (currentUser != null && currentUser.getUserId().equals(targetUserId))
            editable = true;
        model.addAttribute("editable", editable);
        return "user-home.jsp";
    }

    @RequestMapping("/error/403")
    public String error403() {
        return "error-403.jsp";
    }
}
