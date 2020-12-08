package com.log.blog.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.log.blog.dto.ArticleParam;
import com.log.blog.dto.ValidatorGroup;
import com.log.blog.entity.User;
import com.log.blog.service.ArticleAdvancedService;
import com.log.blog.utils.AuthenticationUtils;
import com.log.blog.vo.ArticleVO;
import com.log.blog.vo.RestResult;
import com.log.blog.vo.View;
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

    private final ArticleAdvancedService articleAdvancedService;

    public ArticleRestController(ArticleAdvancedService articleAdvancedService) {
        this.articleAdvancedService = articleAdvancedService;
    }

    @PostMapping
    @JsonView(View.Base.class)
    public RestResult create(
            @RequestAttribute(REQUEST_KEY_CURRENT_USER) User user,
            @Validated(ValidatorGroup.Creating.class) ArticleParam articleParam,
            BindingResult errors,
            @ModelAttribute RestResult result,
            HttpServletResponse response
    ) {
        if (errors.hasErrors()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return result.setErrors(errors);
        }

        articleParam.setAuthorId(user.getUserId());
        String articleId = articleAdvancedService.insertArticle(articleParam);
        response.setStatus(HttpStatus.CREATED.value());
        return result.setDataProperty(DATA_PROPERTY_ARTICLE_ID, articleId);
    }

    @PutMapping("/{articleId:[A-Za-z\\d]{32}}")
    @JsonView(View.Base.class)
    public RestResult update(
            @RequestAttribute(REQUEST_KEY_CURRENT_USER) User user,
            @PathVariable String articleId,
            @Validated(ValidatorGroup.Updating.class) ArticleParam articleParam,
            BindingResult errors,
            @ModelAttribute RestResult result,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws NoHandlerFoundException {
        if (errors.hasErrors()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return result.setErrors(errors);
        }

        ArticleVO articleVO = articleAdvancedService.getArticle(articleId);
        if (articleVO == null)
            throw new NoHandlerFoundException("PUT", request.getRequestURI(), new HttpHeaders());

        AuthenticationUtils.checkOwnerAuthentication(user.getUserId(), articleVO.getAuthorId());

        articleParam.setArticleId(articleId);
        articleAdvancedService.updateArticle(articleParam);
        response.setStatus(HttpStatus.NO_CONTENT.value());
        return null;
    }

    @DeleteMapping("/{articleId:[A-Za-z\\d]{32}}")
    @JsonView(View.Base.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RestResult delete(
            @PathVariable String articleId,
            @ModelAttribute RestResult result,
            HttpServletRequest request
    ) throws NoHandlerFoundException {
        ArticleVO articleVO = articleAdvancedService.getArticle(articleId);
        if (articleVO == null)
            throw new NoHandlerFoundException("PUT", request.getRequestURI(), new HttpHeaders());

        articleAdvancedService.deleteArticle(articleId);
        return null;
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
