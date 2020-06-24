package com.log.blog.controller;

import com.log.blog.entity.Article;
import com.log.blog.entity.User;
import com.log.blog.interceptor.UserRequiredInterceptor;
import com.log.blog.service.ArticlePublicService;
import com.log.blog.service.ArticleService;
import com.log.blog.utils.HtmlEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ArticleController {
    private ArticleService articleService;
    private ArticlePublicService articlePublicService;

    @Autowired
    public void init(ArticleService articleService, ArticlePublicService articlePublicService) {
        this.articleService = articleService;
        this.articlePublicService = articlePublicService;
    }

    @GetMapping({"/edit/", "/edit/{articleId:[A-Za-z\\d]{32}}"})
    public String edit(@PathVariable(required = false) String articleId, Model model) {
        Article article;
        if (articleId == null) {
            article = new Article();
        } else {
            article = articlePublicService.getArticle(articleId);
        }
        model.addAttribute("article", HtmlEscapeUtils.escape(article));
        return "article-editor.jsp";
    }

    @PostMapping({"/update/", "/update/{articleId:[A-Za-z\\d]{32}}"})
    public String update(
            @RequestAttribute(UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER) User user,
            @PathVariable(required = false) String articleId,
            String title,
            @RequestParam(required = false) String content,
            MultipartFile image,
            Model model
    ) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setImage(articleService.uploadImage(image));
        boolean successful = articleId == null
                ? articleService.insertArticle(user.getUserId(), article)
                : articleService.updateArticle(articleId, article);
        model.addAttribute("successful", successful);
        return "article-updated.jsp";
    }
}
