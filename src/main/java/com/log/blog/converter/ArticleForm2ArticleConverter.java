package com.log.blog.converter;

import com.log.blog.dto.ArticleForm;
import com.log.blog.entity.Article;
import org.springframework.core.convert.converter.Converter;

public class ArticleForm2ArticleConverter implements Converter<ArticleForm, Article> {
    @Override
    public Article convert(ArticleForm source) {
        Article article = new Article();
        article.setTitle(source.getTitle());
        article.setContent(source.getContent());
        article.setImage(source.getImageName());
        return article;
    }
}
