package com.log.blog.controller;

import com.log.blog.service.ArticleAdvancedService;
import com.log.blog.service.UserAdvancedService;
import com.log.blog.utils.HtmlEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final int LIST_ITEM_NUMBER = 20;
    private final UserAdvancedService userAdvancedService;
    private final ArticleAdvancedService articleAdvancedService;

    public AdminController(UserAdvancedService userAdvancedService, ArticleAdvancedService articleAdvancedService) {
        this.userAdvancedService = userAdvancedService;
        this.articleAdvancedService = articleAdvancedService;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(AdminPublicController.SESSION_KEY_ADMIN_IDENTITY);
        return "redirect:/admin/login";
    }

    @GetMapping
    public String portal() {
        return "admin-portal.jsp";
    }

//    @GetMapping("/users")
//    public String showUsers(
//            @RequestParam(required = false) Integer num,
//            @RequestParam(required = false) Integer offset,
//            Model model
//    ) {
//        Range range = new Range(num, LIST_ITEM_NUMBER, offset, 0);
//        List<User> users = userAdvancedService.listUsers(range);
//        model.addAttribute("users", HtmlEscapeUtils.escapeUsers(users));
//        model.addAttribute("usersCount", userAdvancedService.getUsersCount());
//        model.addAttribute("range", range);
//        return "admin-show-users.jsp";
//    }

//    @GetMapping("/articles")
//    public String showArticles(
//           @RequestParam(required = false) Integer num,
//           @RequestParam(required = false) Integer offset,
//           Model model
//    ) {
//        Range range = new Range(num, LIST_ITEM_NUMBER, offset, 0);
//        List<Article> articles = articleAdvancedService.getArticles(range);
//        model.addAttribute("articles", HtmlEscapeUtils.escapeArticles(articles));
//        model.addAttribute("articlesCount", articleAdvancedService.getArticlesCount());
//        model.addAttribute("range", range);
//        return "admin-show-articles.jsp";
//    }

    @GetMapping("/delete-user")
    public String deleteUser(String userId, Model model) {
        boolean successful = userAdvancedService.deleteUser(userId);
        model.addAttribute("successful", successful);
        model.addAttribute("userId", HtmlEscapeUtils.escape(userId));
        return "admin-delete-user.jsp";
    }

    @GetMapping("/delete-article")
    public String deleteArticle(String articleId, Model model) {
        boolean successful = articleAdvancedService.deleteArticle(articleId);
        model.addAttribute("successful", successful);
        model.addAttribute("articleId", HtmlEscapeUtils.escape(articleId));
        return "admin-delete-article.jsp";
    }
}
