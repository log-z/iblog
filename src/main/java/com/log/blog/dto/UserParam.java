package com.log.blog.dto;

import com.log.blog.validator.annotation.BasicEmail;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@ScriptAssert(groups = {UserParam.Register.class, UserParam.ResetPassword.class}, lang = "jexl",
        script = "_.userPassword eq _.userPasswordAgain", alias = "_", reportOn = "userPasswordAgain",
        message = "{passwordAgain.inconsistent}")
public class UserParam {

    private String userId;

    @NotBlank(groups = {Register.class, Rename.class})
    @Pattern(regexp = "[\\w\\u4e00-\\u9fa5\\-]{1,20}", groups = {Register.class, Rename.class},
            message = "{name.invalid}")
    private String userName;

    @NotBlank(groups = {Register.class, Login.class})
    @BasicEmail(groups = {Register.class, Login.class}, message = "{email.invalid}")
    private String userEmail;

    @NotBlank(groups = {Register.class, Login.class, ResetPassword.class})
    @Pattern(regexp = "[a-z0-9]{64}", groups = {Register.class, Login.class}, message = "{password.invalid}")
    private String userPassword;

    @NotBlank(groups = {Register.class, ResetPassword.class})
    @Pattern(regexp = "[a-z0-9]{64}", groups = {Register.class, ResetPassword.class}, message = "{password.invalid}")
    private String userPasswordAgain;

    @NotBlank(groups = ResetPassword.class)
    @Pattern(regexp = "[a-z0-9]{64}", groups = ResetPassword.class, message = "{password.invalid}")
    private String oldUserPassword;

    private boolean fuzzySearch = false;

    @Valid
    private PageRange pageRange = new PageRange();

    public interface Login {
    }

    public interface Register {
    }

    public interface Rename {
    }

    public interface ResetPassword {
    }

    public UserParam() { }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPasswordAgain() {
        return userPasswordAgain;
    }

    public void setUserPasswordAgain(String userPasswordAgain) {
        this.userPasswordAgain = userPasswordAgain;
    }

    public String getOldUserPassword() {
        return oldUserPassword;
    }

    public void setOldUserPassword(String oldUserPassword) {
        this.oldUserPassword = oldUserPassword;
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
