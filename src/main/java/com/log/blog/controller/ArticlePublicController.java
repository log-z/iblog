package com.log.blog.controller;

import com.log.blog.dto.Range;
import com.log.blog.entity.Article;
import com.log.blog.entity.User;
import com.log.blog.service.ArticlePublicService;
import com.log.blog.service.UserPublicService;
import com.log.blog.utils.HtmlEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

@Controller
public class ArticlePublicController {
    private static final int LIST_ITEM_NUMBER = 10;
    private ArticlePublicService articlePublicService;
    private UserPublicService userPublicService;

    @Autowired
    public void init(ArticlePublicService articlePublicService, UserPublicService userPublicService) {
        this.articlePublicService = articlePublicService;
        this.userPublicService = userPublicService;
    }

    @GetMapping("/index")
    public String portal(
            @RequestParam(required = false) Integer num,
            @RequestParam(required = false) Integer offset,
            Model model
    ) {
        Range range = new Range(num, LIST_ITEM_NUMBER, offset, 0);
        List<Article> articles = articlePublicService.getArticles(range);
        model.addAttribute("articles", HtmlEscapeUtils.escapeArticles(articles));
        return "article-search.jsp";
    }

    @GetMapping("/s")
    public String search(
            @RequestParam(required = false) Integer num,
            @RequestParam(required = false) Integer offset,
            String keyword,
            Model model
    ) {
        Range range = new Range(num, LIST_ITEM_NUMBER, offset, 0);
        List<Article> articles = articlePublicService.search(keyword, range);
        model.addAttribute("articles", HtmlEscapeUtils.escapeArticles(articles));
        model.addAttribute("keyword", HtmlEscapeUtils.escape(keyword));
        return "article-search.jsp";
    }

    @GetMapping("/article/{articleId:[A-Za-z\\d]{32}}")
    public String article(@PathVariable String articleId, Model model) {
        Article article = articlePublicService.getArticle(articleId);
        if (article == null)
            return null;

        User user = userPublicService.getUser(article.getAuthorId());
        model.addAttribute("article", HtmlEscapeUtils.escape(article));
        model.addAttribute("author", HtmlEscapeUtils.escape(user));
        return "article-content.jsp";
    }

    @GetMapping("/article/image/{name}.{suffix}")
    public void image(
            @PathVariable String name,
            @PathVariable String suffix,
            HttpServletResponse response
    ) throws IOException {
        response.setHeader("Content-Type", switch (suffix) {
            case "jpg" -> MediaType.IMAGE_JPEG_VALUE;
            case "png" -> MediaType.IMAGE_PNG_VALUE;
            default -> null;
        });
        String image = name + "." + suffix;
        if (!articlePublicService.sendImage(image, response.getOutputStream())) {
            response.setStatus(404);
        }
    }
}
