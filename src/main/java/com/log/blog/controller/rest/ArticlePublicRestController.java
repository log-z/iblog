package com.log.blog.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.log.blog.dto.ArticleParam;
import com.log.blog.dto.ValidatorGroup;
import com.log.blog.entity.Article;
import com.log.blog.service.ArticleService;
import com.log.blog.utils.ConversionUtils;
import com.log.blog.vo.ArticleVO;
import com.log.blog.vo.PageVO;
import com.log.blog.vo.RestResult;
import com.log.blog.vo.View;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticlePublicRestController {
    private static final String DATA_PROPERTY_ARTICLE_INFO = "article";
    private static final String DATA_PROPERTY_ARTICLE_LIST = "articles";
    private static final String DATA_PROPERTY_RANGE = "range";

    private final ArticleService articleService;
    private final ConversionService entity2VOConversionService;

    public ArticlePublicRestController(
            @Qualifier("articleBasicService") ArticleService articleService,
            @Qualifier("entity2VOConversionService") ConversionService entity2VOConversionService
    ) {
        this.articleService = articleService;
        this.entity2VOConversionService = entity2VOConversionService;
    }

    @GetMapping("/{articleId:[A-Za-z\\d]{32}}")
    @JsonView(View.Details.class)
    public RestResult info(
            @PathVariable String articleId,
            @ModelAttribute RestResult result,
            HttpServletRequest request
    ) throws NoHandlerFoundException {
        Article article = articleService.getArticle(articleId);
        final ArticleVO articleVO = entity2VOConversionService.convert(article, ArticleVO.class);
        if (article == null)
            throw new NoHandlerFoundException("GET", request.getRequestURI(), new HttpHeaders());

        return result.setDataProperty(DATA_PROPERTY_ARTICLE_INFO, articleVO);
    }

    @GetMapping("/list")
    @JsonView(View.Summary.class)
    public RestResult list(
            @Validated(ValidatorGroup.Querying.class) ArticleParam feature,
            BindingResult errors,
            @ModelAttribute RestResult result,
            HttpServletResponse response
    ) {
        if (errors.hasErrors()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return result.setErrors(errors);
        }

        List<Article> articles = articleService.listArticles(feature);
        List<ArticleVO> articleVOList = ConversionUtils.convertList(entity2VOConversionService, articles, ArticleVO.class);
        PageVO pageVO = entity2VOConversionService.convert(articles, PageVO.class);
        return result.setDataProperty(DATA_PROPERTY_RANGE, pageVO)
                .setDataProperty(DATA_PROPERTY_ARTICLE_LIST, articleVOList);
    }
}
