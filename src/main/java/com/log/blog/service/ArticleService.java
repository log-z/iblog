package com.log.blog.service;

import com.log.blog.entity.Article;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleService {
    String createId();
    boolean insertArticle(@NonNull String authorId, @NonNull Article article);
    boolean updateArticle(@NonNull String articleId, @NonNull Article article);
    String uploadImage(@NonNull MultipartFile image);
}
