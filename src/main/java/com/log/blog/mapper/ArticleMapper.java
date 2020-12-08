package com.log.blog.mapper;

import com.log.blog.dto.ArticleParam;
import com.log.blog.vo.ArticleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

import java.util.List;

@Mapper
public interface ArticleMapper {
    ArticleVO getArticle(@Param("id") @NonNull String id);

    List<ArticleVO> listArticles(@Param("feature") @NonNull ArticleParam feature);

    List<ArticleVO> findArticles(@Param("feature") @NonNull ArticleParam feature);

    boolean insetArticle(@Param("article") @NonNull ArticleParam article);

    boolean updateArticle(@Param("article") @NonNull ArticleParam article);

    boolean deleteArticle(@Param("articleId") @NonNull String articleId);
}
