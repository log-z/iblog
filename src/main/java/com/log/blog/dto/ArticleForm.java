package com.log.blog.dto;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ArticleForm {
    @NotBlank(groups = {Creating.class})
    @Length(max = 40, groups = {Creating.class, Updating.class}, message = "{article.title.tooLong}")
    private String title;

    @Length(max = 1000, groups = {Creating.class, Updating.class}, message = "{article.content.tooLong}")
    private String content;

    private MultipartFile image;

    @Pattern(regexp = "[a-z0-9]{32}\\.(jpg|png)",
            groups = {Creating.class, Updating.class},
            message = "{article.imageName.invalid}")
    private String imageName;

    public interface Creating { }
    public interface Updating { }

    public ArticleForm() {
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
