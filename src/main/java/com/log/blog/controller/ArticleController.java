package com.log.blog.controller;

import com.log.blog.dto.UpdateArticleForm;
import com.log.blog.entity.Article;
import com.log.blog.entity.User;
import com.log.blog.interceptor.UserRequiredInterceptor;
import com.log.blog.service.ArticlePublicService;
import com.log.blog.service.ArticleService;
import com.log.blog.utils.HtmlEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        UpdateArticleForm form = new UpdateArticleForm();
        if (articleId != null) {
            Article article = articlePublicService.getArticle(articleId);
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
            @Validated @ModelAttribute("form") UpdateArticleForm form,
            BindingResult errors,
            Model model
    ) {
        if (!errors.hasErrors()) {
            // 准备文章载体
            Article article = new Article();
            article.setTitle(form.getTitle());
            article.setContent(form.getContent());
            article.setImage(articleService.uploadImage(form.getImage()));
            // 创建或更新文章
            boolean successful = articleId == null
                    ? articleService.insertArticle(user.getUserId(), article)
                    : articleService.updateArticle(articleId, article);
            model.addAttribute("successful", successful);
            return "article-updated.jsp";
        }
        return "article-editor.jsp";
    }
}
