package com.log.blog.service.impl;

import com.log.blog.entity.Article;
import com.log.blog.mapper.ArticleMapper;
import com.log.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleMapper articleMapper;
    private String uploadRootPath;
    private String imagesDir;

    @Autowired
    public void init(ArticleMapper articleMapper, Environment environment) {
        this.articleMapper = articleMapper;
        this.uploadRootPath = environment.getProperty("upload.rootPath");
        this.imagesDir = environment.getProperty("upload.article.images");
    }

    @Override
    public String createId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public boolean insertArticle(@NonNull String authorId, @NonNull Article article) {
        article.setArticleId(createId());
        article.setAuthorId(authorId);
        article.setCreateTime(new Date());
        try {
            articleMapper.insetArticle(article);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateArticle(@NonNull String articleId, @NonNull Article article) {
        article.setArticleId(articleId);
        article.setAuthorId(null);
        article.setCreateTime(null);
        try {
            articleMapper.updateArticle(article);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public String uploadImage(@NonNull MultipartFile image) {
        String fileName = image.getOriginalFilename();
        if (image.isEmpty() || fileName == null)
            return null;

        String ext = fileName.substring(fileName.lastIndexOf('.'));
        fileName = createId() + ext;
        String path = uploadRootPath + imagesDir + fileName;
        File file = new File(path);
        try {
            if (!file.exists() && !file.createNewFile())
                throw new FileSystemException("failed be create image file [" + path + ']');
            image.transferTo(file);
            return fileName;
        } catch (IOException e) {
            return null;
        }
    }
}
