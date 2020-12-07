package com.log.blog.service.impl;

import com.log.blog.entity.Article;
import com.log.blog.mapper.ArticleMapper;
import com.log.blog.service.ArticleAdvancedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.FileSystemException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Service("articleAdvancedService")
public class ArticleAdvancedServiceImpl extends ArticleServiceImpl implements ArticleAdvancedService {
    private static final Set<String> SUPPORT_IMAGE_EXTENSIONS = Set.of("jpg", "png");

    private final ArticleMapper articleMapper;
    private final String uploadRootPath;
    private final String imagesDir;

    private static final Logger logger = LoggerFactory.getLogger(ArticleAdvancedServiceImpl.class);

    public ArticleAdvancedServiceImpl(ArticleMapper articleMapper,
                     @Value("${upload.rootPath}") String uploadRootPath,
                     @Value("${upload.article.images}") String imagesDir) {
        super(articleMapper, uploadRootPath, imagesDir);
        this.articleMapper = articleMapper;
        this.uploadRootPath = uploadRootPath;
        this.imagesDir = imagesDir;
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

            try (FileOutputStream os = new FileOutputStream(file);
                 InputStream in = image.getInputStream()) {
                in.transferTo(os);
            }

            return fileName;
        } catch (IOException e) {
            logger.error("Failed be upload image file.", e);
            return null;
        }
    }
}
