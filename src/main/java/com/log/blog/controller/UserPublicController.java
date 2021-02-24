package com.log.blog.controller;

import com.log.blog.entity.User;
import com.log.blog.service.ArticleService;
import com.log.blog.service.UserService;
import com.log.blog.utils.HtmlEscapeUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class UserPublicController {
    public static final String SESSION_KEY_USER_IDENTITY = "userIdentity";
    private static final int LIST_ITEM_NUMBER = 10;
    private final UserService userService;
    private final ArticleService articleService;

    public UserPublicController(
            @Qualifier("userBasicService") UserService userService,
            @Qualifier("articleBasicService") ArticleService articleService
    ) {
        this.userService = userService;
        this.articleService = articleService;
    }

//    @GetMapping("/register")
//    public String register(Model model) {
//        model.addAttribute("userRegisterForm", new UserRegisterForm());
//        return "user-register.jsp";
//    }
//
//    @PostMapping(value = "/register")
//    public String register(
//            @Validated(User.Register.class) UserRegisterForm userRegisterForm,
//            BindingResult errors,
//            Model model
//    ) {
//        if (!errors.hasErrors()) {
//            passwordAgainValidator.validate(userRegisterForm, errors);
//        }
//        if (!errors.hasErrors() && userService.register(userRegisterForm)) {
//            return "redirect:/login";
//        } else {
//            model.addAttribute("userRegisterForm", HtmlEscapeUtils.escape(userRegisterForm));
//            return "user-register.jsp";
//        }
//    }
//
//    @GetMapping("/login")
//    public String login(Model model) {
//        model.addAttribute("user", new User());
//        return "user-login.jsp";
//    }
//
//    @PostMapping("/login")
//    public String login(
//            @Validated(User.Login.class) User user,
//            BindingResult errors,
//            HttpSession session,
//            Model model
//    ) {
//        if (!errors.hasErrors()) {
//            String userId = userService.loginCheck(user);
//            if (userId != null) {
//                session.setAttribute(SESSION_KEY_USER_IDENTITY, userId);
//                return "redirect:/"+ userId;
//            }
//        }
//        model.addAttribute("user", HtmlEscapeUtils.escape(user));
//        return "user-login.jsp";
//    }

//    @GetMapping("/{targetUserId:\\d{1,11}}")
//    public String home(
//            @PathVariable String targetUserId,
//            @RequestAttribute(value = UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER, required = false) User currentUser,
//            @RequestParam(required = false) Integer num,
//            @RequestParam(required = false) Integer offset,
//            Model model
//    ) {
//        User targetUser = userService.getUser(targetUserId);
//        if (targetUser == null) return null;
//        model.addAttribute("targetUser", HtmlEscapeUtils.escape(targetUser));
//
//        Article feature = new Article();
//        feature.setAuthorId(targetUserId);
//        Range range = new Range(num, LIST_ITEM_NUMBER, offset, 0);
//        List<Article> articles = articleService.search(feature, range);
//        model.addAttribute("articles", HtmlEscapeUtils.escapeArticles(articles));
//        model.addAttribute("articlesCount", articleService.searchCount(feature));
//        model.addAttribute("range", range);
//
//        boolean editable = false;
//        if (currentUser != null && currentUser.getUserId().equals(targetUserId))
//            editable = true;
//        model.addAttribute("editable", editable);
//        return "user-home.jsp";
//    }

    @RequestMapping("/error/401")
    public String error401() {
        return "error-401.jsp";
    }
}
