package com.log.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Date;

public class ArticleVO {
    @JsonView({View.Summary.class, View.Details.class})
    private String articleId;

    @JsonView({View.Summary.class, View.Details.class})
    private String authorId;

    @JsonView({View.Summary.class, View.Details.class})
    private String title;

    @JsonView(View.Details.class)
    private String content;

    @JsonView({View.Summary.class, View.Details.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    @JsonView(View.Details.class)
    private String imageName;

    @JsonView(View.Details.class)
    private String imageUrl;

    @JsonView({View.Summary.class, View.Details.class})
    private String articleUrl;

    @JsonView(View.Details.class)
    private String authorHomeUrl;

    public ArticleVO() {
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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
}
