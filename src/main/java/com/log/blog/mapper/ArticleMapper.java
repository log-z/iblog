package com.log.blog.mapper;

import com.log.blog.dto.ArticleParam;
import com.log.blog.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

import java.util.List;

@Mapper
public interface ArticleMapper {
    Article getArticle(@Param("id") @NonNull String id);

    List<Article> listArticles(@Param("feature") @NonNull ArticleParam feature);

    List<Article> findArticles(@Param("feature") @NonNull ArticleParam feature);

    boolean insetArticle(@Param("article") @NonNull ArticleParam article);

    boolean updateArticle(@Param("article") @NonNull ArticleParam article);

    boolean deleteArticle(@Param("articleId") @NonNull String articleId);
}
