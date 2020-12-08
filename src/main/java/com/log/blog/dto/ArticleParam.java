package com.log.blog.dto;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

import com.log.blog.dto.ValidatorGroup.Creating;
import com.log.blog.dto.ValidatorGroup.Updating;
import com.log.blog.dto.ValidatorGroup.Querying;

public class ArticleParam {
    private String articleId;

    private String authorId;

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

    private Date createTime;

    private boolean fuzzySearch = false;

    @Valid
    @NotNull(groups = Querying.class)
    private PageRange pageRange;

    public ArticleParam() {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isFuzzySearch() {
        return fuzzySearch;
    }

    public void setFuzzySearch(boolean fuzzySearch) {
        this.fuzzySearch = fuzzySearch;
    }

    public PageRange getPageRange() {
        return pageRange;
    }

    public void setPageRange(PageRange pageRange) {
        this.pageRange = pageRange;
    }

}
