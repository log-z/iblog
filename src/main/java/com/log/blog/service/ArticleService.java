package com.log.blog.service;

import com.log.blog.dto.ArticleParam;
import com.log.blog.entity.Article;
import org.springframework.lang.NonNull;

import java.io.OutputStream;
import java.util.List;

public interface ArticleService {
    List<Article> listArticles(@NonNull ArticleParam feature);
    Article getArticle(@NonNull String articleId);
    boolean sendImage(@NonNull String image, @NonNull OutputStream outputStream);
}
