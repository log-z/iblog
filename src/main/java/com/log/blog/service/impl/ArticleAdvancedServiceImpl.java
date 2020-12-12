package com.log.blog.service.impl;

import com.log.blog.dto.ArticleParam;
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
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Service("articleAdvancedService")
public class ArticleAdvancedServiceImpl extends ArticleServiceImpl implements ArticleAdvancedService {
    private static final Set<String> SUPPORT_IMAGE_EXTENSIONS = Set.of("jpg", "png");

    private final ArticleMapper articleMapper;
    private final String imagesDir;

    private static final Logger logger = LoggerFactory.getLogger(ArticleAdvancedServiceImpl.class);

    public ArticleAdvancedServiceImpl(ArticleMapper articleMapper,
                     @Value("${upload.article.images}") String imagesDir) {
        super(articleMapper, imagesDir);
        this.articleMapper = articleMapper;
        this.imagesDir = imagesDir;
    }

    @Override
    public String createId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public String insertArticle(@NonNull ArticleParam articleParam) {
        articleParam.setArticleId(createId());
        articleParam.setCreateTime(new Date());

        articleMapper.insetArticle(articleParam);
        return articleParam.getArticleId();
    }

    @Override
    public boolean updateArticle(@NonNull ArticleParam articleParam) {
        articleParam.setAuthorId(null);
        articleParam.setCreateTime(null);

        return articleMapper.updateArticle(articleParam);
    }

    @Override
    public boolean deleteArticle(@NonNull String articleId) {
        return articleMapper.deleteArticle(articleId);
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
        String path = imagesDir + fileName;
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
