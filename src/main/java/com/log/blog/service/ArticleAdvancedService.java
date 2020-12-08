package com.log.blog.service;

import com.log.blog.dto.ArticleParam;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleAdvancedService extends ArticleService {
    String createId();
    String insertArticle(@NonNull ArticleParam articleParam);
    boolean updateArticle(@NonNull ArticleParam articleParam);
    boolean deleteArticle(@NonNull String articleId);
    String uploadImage(@NonNull MultipartFile image);
}
