package com.log.blog.mapper;

import com.log.blog.dto.Range;
import com.log.blog.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

import java.sql.SQLException;
import java.util.List;

public interface ArticleMapper {
    Article getArticle(@Param("id") @NonNull String id) throws SQLException;

    List<Article> getAllArticle(@Param("range") Range range) throws SQLException;

    List<Article> findArticles(@Param("feature") @NonNull Article feature, @Param("range") @NonNull Range range)
            throws SQLException;

    void insetArticle(@Param("article") @NonNull Article article) throws SQLException;

    void updateArticle(@Param("article") @NonNull Article article) throws SQLException;

    void deleteArticle(@Param("articleId") @NonNull String articleId) throws SQLException;
}
