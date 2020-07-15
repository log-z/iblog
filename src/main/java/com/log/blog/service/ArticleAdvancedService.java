package com.log.blog.service;

import com.log.blog.entity.Article;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleAdvancedService extends ArticleService {
    String createId();
    String insertArticle(@NonNull String authorId, @NonNull Article article);
    boolean updateArticle(@NonNull String articleId, @NonNull Article article);
    boolean deleteArticle(@NonNull String articleId);
    String uploadImage(@NonNull MultipartFile image);
}
