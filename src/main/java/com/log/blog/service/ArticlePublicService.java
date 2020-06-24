package com.log.blog.service;

import com.log.blog.dto.Range;
import com.log.blog.entity.Article;
import org.springframework.lang.NonNull;

import java.io.OutputStream;
import java.util.List;

public interface ArticlePublicService {
    List<Article> getArticles(@NonNull Range range);
    Article getArticle(@NonNull String articleId);
    List<Article> search(@NonNull String keyword, @NonNull Range range);
    List<Article> search(@NonNull Article feature, @NonNull Range range);
    boolean sendImage(@NonNull String image, @NonNull OutputStream outputStream);
}
