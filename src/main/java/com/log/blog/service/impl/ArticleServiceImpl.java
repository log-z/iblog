package com.log.blog.service.impl;

import com.log.blog.dto.Range;
import com.log.blog.entity.Article;
import com.log.blog.mapper.ArticleMapper;
import com.log.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Service("articleBasicService")
public class ArticleServiceImpl implements ArticleService {
    private final ArticleMapper articleMapper;
    private final String uploadRootPath;
    private final String imagesDir;

    public ArticleServiceImpl(ArticleMapper articleMapper,
                              @Value("${upload.rootPath}") String uploadRootPath,
                              @Value("${upload.article.images}") String imagesDir) {
        this.articleMapper = articleMapper;
        this.uploadRootPath = uploadRootPath;
        this.imagesDir = imagesDir;
    }

    @Override
    public Article getArticle(@NonNull String articleId) {
        try {
            return articleMapper.getArticle(articleId);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Article> getArticles(@NonNull Range range) {
        try {
            return articleMapper.getAllArticles(range);
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public long getArticlesCount() {
        try {
            return articleMapper.getArticlesCount();
        } catch (SQLException e) {
            return -1;
        }
    }

    @Override
    public List<Article> search(@NonNull String keyword, @NonNull Range range) {
        Article feature = new Article();
        feature.setTitle(keyword);
        feature.setContent(keyword);
        return search(feature, range);
    }

    @Override
    public List<Article> search(@NonNull Article feature, @NonNull Range range) {
        try {
            return articleMapper.findArticles(feature, range);
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public long searchCount(@NonNull String keyword) {
        Article feature = new Article();
        feature.setTitle(keyword);
        feature.setContent(keyword);
        return searchCount(feature);
    }

    @Override
    public long searchCount(@NonNull Article feature) {
        try {
            return articleMapper.findArticlesCount(feature);
        } catch (SQLException e) {
            return -1;
        }
    }

    @Override
    public boolean sendImage(@NonNull String image, @NonNull OutputStream outputStream) {
        File file = new File(uploadRootPath + imagesDir + image);
        if (!file.exists())
            return false;
        try {
            FileInputStream inputStream = new FileInputStream(file);
            inputStream.transferTo(outputStream);
            inputStream.close();
            outputStream.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
