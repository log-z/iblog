package com.log.blog.controller;

import com.log.blog.dto.ArticleForm;
import com.log.blog.entity.Article;
import com.log.blog.entity.User;
import com.log.blog.interceptor.UserRequiredInterceptor;
import com.log.blog.service.ArticleAdvancedService;
import com.log.blog.utils.HtmlEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class ArticleController {
    private final ArticleAdvancedService articleAdvancedService;

    public ArticleController(ArticleAdvancedService articleAdvancedService) {
        this.articleAdvancedService = articleAdvancedService;
    }

    @GetMapping({"/edit/", "/edit/{articleId:[A-Za-z\\d]{32}}"})
    public String edit(@PathVariable(required = false) String articleId, Model model) {
        ArticleForm form = new ArticleForm();
        if (articleId != null) {
            Article article = articleAdvancedService.getArticle(articleId);
            if (article != null) {
                form.setTitle(article.getTitle());
                form.setContent(article.getContent());
            }
        }
        model.addAttribute("form", HtmlEscapeUtils.escape(form));
        return "article-editor.jsp";
    }

    @PostMapping({"/update/", "/update/{articleId:[A-Za-z\\d]{32}}"})
    public String update(
            @RequestAttribute(UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER) User user,
            @PathVariable(required = false) String articleId,
            @Validated(ArticleForm.Updating.class) @ModelAttribute("form") ArticleForm form,
            BindingResult errors,
            Model model
    ) {
        if (!errors.hasErrors()) {
            // 准备文章载体
            Article article = new Article();
            article.setTitle(form.getTitle());
            article.setContent(form.getContent());
            article.setImage(articleAdvancedService.uploadImage(form.getImage()));
            // 创建或更新文章
            boolean successful = articleId == null
                    ? articleAdvancedService.insertArticle(user.getUserId(), article) != null
                    : articleAdvancedService.updateArticle(articleId, article);
            model.addAttribute("successful", successful);
            return "article-updated.jsp";
        }
        return "article-editor.jsp";
    }
}
