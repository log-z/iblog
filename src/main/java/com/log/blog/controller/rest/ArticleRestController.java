package com.log.blog.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.log.blog.dto.ArticleForm;
import com.log.blog.entity.Article;
import com.log.blog.entity.User;
import com.log.blog.service.ArticleAdvancedService;
import com.log.blog.utils.AuthenticationUtils;
import com.log.blog.vo.rest.RestResult;
import com.log.blog.vo.rest.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.log.blog.interceptor.UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER;

@RestController
@RequestMapping("/api/article")
public class ArticleRestController {
    private static final String DATA_PROPERTY_ARTICLE_ID = "articleId";
    private static final String DATA_PROPERTY_IMAGE_NAME = "imageName";

    private ArticleAdvancedService articleAdvancedService;
    private ConversionService restConversionService;

    @Autowired
    public void init(ArticleAdvancedService articleAdvancedService,
                     @Qualifier("restConverterService") ConversionService restConversionService) {
        this.articleAdvancedService = articleAdvancedService;
        this.restConversionService = restConversionService;
    }

    @PostMapping
    @JsonView(View.Base.class)
    public RestResult create(
            @RequestAttribute(REQUEST_KEY_CURRENT_USER) User user,
            @Validated(ArticleForm.Creating.class) ArticleForm form,
            BindingResult errors,
            @ModelAttribute RestResult result,
            HttpServletResponse response
    ) {
        if (errors.hasErrors()) {
            response.setStatus(400);
            return result.setErrors(errors);
        }

        Article article = restConversionService.convert(form, Article.class);
        assert article != null;
        String articleId = articleAdvancedService.insertArticle(user.getUserId(), article);
        if (articleId == null) {
            throw new RuntimeException();
        } else {
            response.setStatus(201);
            return result.setDataProperty(DATA_PROPERTY_ARTICLE_ID, articleId);
        }
    }

    @PutMapping("/{articleId:[A-Za-z\\d]{32}}")
    @JsonView(View.Base.class)
    public RestResult update(
            @RequestAttribute(REQUEST_KEY_CURRENT_USER) User user,
            @PathVariable String articleId,
            @Validated(ArticleForm.Updating.class) ArticleForm form,
            BindingResult errors,
            @ModelAttribute RestResult result,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws NoHandlerFoundException {
        if (errors.hasErrors()) {
            response.setStatus(400);
            return result.setErrors(errors);
        }

        Article article = articleAdvancedService.getArticle(articleId);
        if (article == null)
            throw new NoHandlerFoundException("PUT", request.getRequestURI(), new HttpHeaders());

        AuthenticationUtils.checkOwnerAuthentication(user.getUserId(), article.getAuthorId());

        Article newArticle = restConversionService.convert(form, Article.class);
        assert newArticle != null;
        boolean successful = articleAdvancedService.updateArticle(articleId, newArticle);
        if (successful) {
            response.setStatus(204);
            return null;
        }
        throw new RuntimeException();
    }

    @DeleteMapping("/{articleId:[A-Za-z\\d]{32}}")
    @JsonView(View.Base.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RestResult delete(
            @PathVariable String articleId,
            @ModelAttribute RestResult result,
            HttpServletRequest request
    ) throws NoHandlerFoundException {
        Article article = articleAdvancedService.getArticle(articleId);
        if (article == null)
            throw new NoHandlerFoundException("PUT", request.getRequestURI(), new HttpHeaders());

        boolean successful = articleAdvancedService.deleteArticle(articleId);
        if (successful)
            return null;
        throw new RuntimeException();
    }

    @PostMapping("/upload-image")
    @JsonView(View.Base.class)
    @ResponseStatus(HttpStatus.CREATED)
    public RestResult uploadImage(MultipartFile image, @ModelAttribute RestResult result) {
        String imageName = articleAdvancedService.uploadImage(image);
        if (imageName == null)
            throw new RuntimeException();
        return result.setDataProperty(DATA_PROPERTY_IMAGE_NAME, imageName);
    }
}
