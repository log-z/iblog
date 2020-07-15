package com.log.blog.service.impl;

import com.log.blog.entity.Article;
import com.log.blog.mapper.ArticleMapper;
import com.log.blog.service.ArticleAdvancedService;
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
import java.util.Set;
import java.util.UUID;

@Service("articleAdvancedService")
public class ArticleAdvancedServiceImpl extends ArticleServiceImpl implements ArticleAdvancedService {
    private static final Set<String> SUPPORT_IMAGE_EXTENSIONS = Set.of("jpg", "png");

    private ArticleMapper articleMapper;
    private String uploadRootPath;
    private String imagesDir;

    @Autowired
    public void init(ArticleMapper articleMapper, Environment environment) {
        super.init(articleMapper, environment);
        this.articleMapper = articleMapper;
        this.uploadRootPath = environment.getProperty("upload.rootPath");
        this.imagesDir = environment.getProperty("upload.article.images");
    }

    @Override
    public String createId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public String insertArticle(@NonNull String authorId, @NonNull Article article) {
        article.setArticleId(createId());
        article.setAuthorId(authorId);
        article.setCreateTime(new Date());
        try {
            articleMapper.insetArticle(article);
            return article.getArticleId();
        } catch (SQLException e) {
            return null;
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
    public boolean deleteArticle(@NonNull String articleId) {
        try {
            articleMapper.deleteArticle(articleId);
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

        String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
        if (!SUPPORT_IMAGE_EXTENSIONS.contains(ext.toLowerCase()))
            return null;

        fileName = createId() + "." + ext;
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
