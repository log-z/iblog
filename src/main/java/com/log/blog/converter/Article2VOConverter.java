package com.log.blog.converter;

import com.log.blog.entity.Article;
import com.log.blog.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class Article2VOConverter implements Converter<Article, ArticleVO> {
    private String contextPath = "";

    public void setContextPath(@Value("#{servletContext.contextPath}") String contextPath) {
        this.contextPath = contextPath == null ? "" : contextPath;
    }

    @Override
    public ArticleVO convert(Article source) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setArticleId(source.getArticleId());
        articleVO.setAuthorId(source.getAuthorId());
        articleVO.setTitle(source.getTitle());
        articleVO.setContent(source.getContent());
        articleVO.setCreateTime(source.getCreateTime());
        if (source.getArticleId() != null)
            articleVO.setArticleUrl(contextPath + "/m/article#" + source.getArticleId());
        if (source.getAuthorId() != null)
            articleVO.setAuthorHomeUrl(contextPath + "/m/user#" + source.getAuthorId());
        if (source.getImage() != null)
            articleVO.setImageUrl(contextPath + "/article/image/" + source.getImage());
        return articleVO;
    }
}
