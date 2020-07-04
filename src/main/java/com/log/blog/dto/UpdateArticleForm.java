package com.log.blog.dto;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public class UpdateArticleForm {
    @Length(max = 40, message = "{article.title.tooLong}")
    private String title;

    @Length(max = 1000, message = "{article.content.tooLong}")
    private String content;

    private MultipartFile image;

    public UpdateArticleForm() {
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
