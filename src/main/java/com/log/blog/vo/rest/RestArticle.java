package com.log.blog.vo.rest;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.Date;
import java.util.Objects;

public class RestArticle {
    @JsonView({View.Summary.class, View.Details.class})
    private String articleId;

    @JsonView({View.Summary.class, View.Details.class})
    private String authorId;

    @JsonView({View.Summary.class, View.Details.class})
    private String title;

    @JsonView(View.Details.class)
    private String content;

    @JsonView({View.Summary.class, View.Details.class})
    private Date createTime;

    @JsonView(View.Details.class)
    private String imageUrl;

    @JsonView({View.Summary.class, View.Details.class})
    private String articleUrl;

    @JsonView(View.Details.class)
    private String authorHomeUrl;

    public RestArticle() {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getAuthorHomeUrl() {
        return authorHomeUrl;
    }

    public void setAuthorHomeUrl(String authorHomeUrl) {
        this.authorHomeUrl = authorHomeUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestArticle that = (RestArticle) o;
        return Objects.equals(articleId, that.articleId) &&
                Objects.equals(authorId, that.authorId) &&
                Objects.equals(title, that.title) &&
                Objects.equals(content, that.content) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(articleUrl, that.articleUrl) &&
                Objects.equals(authorHomeUrl, that.authorHomeUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleId, authorId, title, content, createTime, imageUrl, articleUrl, authorHomeUrl);
    }
}
