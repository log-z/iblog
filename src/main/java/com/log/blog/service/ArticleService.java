package com.log.blog.service;

import com.log.blog.dto.ArticleParam;
import com.log.blog.vo.ArticleVO;
import org.springframework.lang.NonNull;

import java.io.OutputStream;
import java.util.List;

public interface ArticleService {
    List<ArticleVO> listArticles(@NonNull ArticleParam feature);
    ArticleVO getArticle(@NonNull String articleId);
    boolean sendImage(@NonNull String image, @NonNull OutputStream outputStream);
}
