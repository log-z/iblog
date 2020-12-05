package com.log.blog.converter;

import com.log.blog.entity.Article;
import com.log.blog.vo.rest.RestArticle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class Article2RestArticleConverter implements Converter<Article, RestArticle> {
    private String contextPath;
    public void setContextPath(@Value("#{servletContext.contextPath}") String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public RestArticle convert(Article source) {
        RestArticle restArticle = new RestArticle();
        restArticle.setArticleId(source.getArticleId());
        restArticle.setAuthorId(source.getAuthorId());
        restArticle.setTitle(source.getTitle());
        restArticle.setContent(source.getContent());
        restArticle.setCreateTime(source.getCreateTime());
        if (source.getArticleId() != null)
            restArticle.setArticleUrl(contextPath + "/m/article#" + source.getArticleId());
        if (source.getAuthorId() != null)
            restArticle.setAuthorHomeUrl(contextPath + "/m/user#" + source.getAuthorId());
        if (source.getImage() != null)
            restArticle.setImageUrl(contextPath + "/article/image/" + source.getImage());
        return restArticle;
    }
}
