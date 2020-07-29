package com.log.blog.entity;

import java.util.Date;
import java.util.Objects;

public class Article {
    private String articleId;
    private String authorId;
    private String title;
    private String content;
    private String image;
    private Date createTime;

    public Article() {
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isBlank() {
        return articleId == null && authorId == null && title == null && content == null &&
                image == null && createTime == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(articleId, article.articleId) &&
                Objects.equals(authorId, article.authorId) &&
                Objects.equals(title, article.title) &&
                Objects.equals(content, article.content) &&
                Objects.equals(image, article.image) &&
                Objects.equals(createTime, article.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleId, authorId, title, content, image, createTime);
    }
}
