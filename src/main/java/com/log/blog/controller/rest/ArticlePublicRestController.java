package com.log.blog.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.log.blog.dto.Range;
import com.log.blog.entity.Article;
import com.log.blog.service.ArticleService;
import com.log.blog.vo.rest.RestArticle;
import com.log.blog.vo.rest.RestRange;
import com.log.blog.vo.rest.RestResult;
import com.log.blog.vo.rest.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/article")
public class ArticlePublicRestController {
    private static final String DATA_PROPERTY_ARTICLE_INFO = "article";
    private static final String DATA_PROPERTY_ARTICLE_LIST = "articles";
    private static final String DATA_PROPERTY_RANGE = "range";

    private ArticleService articleService;
    private ConversionService restConversionService;

    @Autowired
    public void init(@Qualifier("articleBasicService") ArticleService articleService,
                     @Qualifier("restConverterService") ConversionService restConversionService) {
        this.articleService = articleService;
        this.restConversionService = restConversionService;
    }

    @GetMapping("/{articleId:[A-Za-z\\d]{32}}")
    @JsonView(View.Details.class)
    public RestResult info(
            @PathVariable String articleId,
            @ModelAttribute RestResult result,
            HttpServletRequest request
    ) throws NoHandlerFoundException {
        Article article = articleService.getArticle(articleId);
        if (article == null)
            throw new NoHandlerFoundException("GET", request.getRequestURI(), new HttpHeaders());

        RestArticle restArticle = restConversionService.convert(article, RestArticle.class);
        return result.setDataProperty(DATA_PROPERTY_ARTICLE_INFO, restArticle);
    }

    @GetMapping("/list")
    @JsonView(View.Summary.class)
    public RestResult list(
            @Validated Range range,
            BindingResult errors,
            @RequestParam(required = false) String keyword,
            @ModelAttribute RestResult result,
            HttpServletResponse response
    ) {
        if (errors.hasErrors()) {
            response.setStatus(400);
            return result.setErrors(errors);
        }

        long count;
        List<Article> articles;
        if (keyword == null) {
            count = articleService.getArticlesCount();
            articles = articleService.getArticles(range);
        } else {
            count = articleService.searchCount(keyword);
            articles = articleService.search(keyword, range);
        }

        RestRange restRange = restConversionService.convert(range, RestRange.class);
        assert restRange != null;
        restRange.setTotal(count);

        List<RestArticle> restArticles = articles.stream()
                .map(article -> {
                    RestArticle restArticle = restConversionService.convert(article, RestArticle.class);
                    if (restArticle == null) restArticle = new RestArticle();
                    return restArticle;
                }).collect(Collectors.toUnmodifiableList());

        return result.setDataProperty(DATA_PROPERTY_RANGE, restRange)
                .setDataProperty(DATA_PROPERTY_ARTICLE_LIST, restArticles);
    }
}
