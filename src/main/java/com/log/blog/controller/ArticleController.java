package com.log.blog.controller;

import com.log.blog.dto.ArticleParam;
import com.log.blog.dto.ValidatorGroup;
import com.log.blog.entity.User;
import com.log.blog.interceptor.UserRequiredInterceptor;
import com.log.blog.service.ArticleAdvancedService;
import com.log.blog.utils.HtmlEscapeUtils;
import com.log.blog.vo.ArticleVO;
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
        ArticleVO articleVO = articleId != null ?
                articleAdvancedService.getArticle(articleId) :
                new ArticleVO();
        model.addAttribute("form", HtmlEscapeUtils.escape(articleVO));
        return "article-editor.jsp";
    }

    @PostMapping({"/update/", "/update/{articleId:[A-Za-z\\d]{32}}"})
    public String update(
            @RequestAttribute(UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER) User user,
            @PathVariable(required = false) String articleId,
            @Validated(ValidatorGroup.Updating.class) @ModelAttribute("form") ArticleParam articleParam,
            BindingResult errors,
            Model model
    ) {
        if (!errors.hasErrors()) {
            // 创建或更新文章
            boolean successful;
            if (articleId == null) {
                articleParam.setAuthorId(user.getUserId());
                successful = articleAdvancedService.insertArticle(articleParam) != null;
            } else {
                articleParam.setArticleId(articleId);
                successful = articleAdvancedService.updateArticle(articleParam);
            }
            model.addAttribute("successful", successful);
            return "article-updated.jsp";
        }
        return "article-editor.jsp";
    }
}
