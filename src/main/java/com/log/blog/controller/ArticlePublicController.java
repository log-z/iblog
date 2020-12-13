package com.log.blog.controller;

import com.log.blog.dto.ArticleParam;
import com.log.blog.dto.PageRange;
import com.log.blog.dto.ValidatorGroup;
import com.log.blog.entity.Article;
import com.log.blog.entity.User;
import com.log.blog.service.ArticleService;
import com.log.blog.service.UserService;
import com.log.blog.utils.ConversionUtils;
import com.log.blog.utils.HtmlEscapeUtils;
import com.log.blog.vo.ArticleVO;
import com.log.blog.vo.PageVO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@Scope("prototype")
public class ArticlePublicController {
    private final ArticleService articleService;
    private final UserService userService;
    private final ConversionService entity2VOConversionService;

    public ArticlePublicController(@Qualifier("articleBasicService") ArticleService articleService,
                                   @Qualifier("userBasicService") UserService userService,
                                   @Qualifier("entity2VOConversionService") ConversionService entity2VOConversionService) {
        this.articleService = articleService;
        this.userService = userService;
        this.entity2VOConversionService = entity2VOConversionService;
    }

    @GetMapping("/")
    public String portal(
            @Validated(ValidatorGroup.Querying.class) PageRange pageRange,
            BindingResult errors,
            Model model
    ) {
        if (errors.hasErrors()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());
        }

        ArticleParam feature = new ArticleParam();
        feature.setPageRange(pageRange);
        List<Article> articles = articleService.listArticles(feature);
        List<ArticleVO> articleVOList = ConversionUtils.convertList(entity2VOConversionService, articles, ArticleVO.class);

        model.addAttribute("articles", HtmlEscapeUtils.escapeArticles(articleVOList));
        model.addAttribute("range", entity2VOConversionService.convert(articles, PageVO.class));
        return "article-search.jsp";
    }

    @GetMapping("/s")
    public String search(
            @Validated(ValidatorGroup.Querying.class) PageRange pageRange,
            BindingResult errors,
            String keyword,
            Model model
    ) {
        if (errors.hasErrors()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
        if (keyword == null || keyword.isBlank()) {
            return "redirect:/";
        }

        ArticleParam feature = new ArticleParam();
        feature.setTitle(keyword);
        feature.setContent(keyword);
        feature.setPageRange(pageRange);
        feature.setFuzzySearch(true);
        List<Article> articles = articleService.listArticles(feature);
        List<ArticleVO> articleVOList = ConversionUtils.convertList(entity2VOConversionService, articles, ArticleVO.class);

        model.addAttribute("articles", HtmlEscapeUtils.escapeArticles(articleVOList));
        model.addAttribute("keyword", HtmlEscapeUtils.escape(keyword));
        model.addAttribute("range", entity2VOConversionService.convert(articles, PageVO.class));
        return "article-search.jsp";
    }

//    @GetMapping("/article/{articleId:[A-Za-z\\d]{32}}")
//    public String article(@PathVariable String articleId, Model model) {
//        Article article = articleService.getArticle(articleId);
//        if (article == null)
//            return null;
//
//        ArticleVO articleVO = entity2VOConversionService.convert(article, ArticleVO.class);
//        assert articleVO != null;
//        User user = userService.getUser(articleVO.getAuthorId());
//        model.addAttribute("article", HtmlEscapeUtils.escape(articleVO));
//        model.addAttribute("author", HtmlEscapeUtils.escape(user));
//        return "article-content.jsp";
//    }

    @GetMapping("/article/image/{name}.{suffix}")
    public void image(
            @PathVariable String name,
            @PathVariable String suffix,
            HttpServletResponse response
    ) throws IOException {
        String contentType = switch (suffix) {
            case "jpg" -> MediaType.IMAGE_JPEG_VALUE;
            case "png" -> MediaType.IMAGE_PNG_VALUE;
            default -> null;
        };
        response.setHeader("Content-Type", contentType);

        String image = name + "." + suffix;
        if (!articleService.sendImage(image, response.getOutputStream())) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }
}
