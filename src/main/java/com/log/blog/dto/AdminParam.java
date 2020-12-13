package com.log.blog.dto;

import com.log.blog.entity.Admin;
import com.log.blog.validator.annotation.BasicEmail;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class AdminParam extends Admin {
    private String adminId;

    @NotBlank(groups = {Register.class, Rename.class})
    @Pattern(regexp = "[\\w\\u4e00-\\u9fa5\\-]{1,20}", groups = {Register.class, Rename.class},
            message = "{name.invalid}")
    private String adminName;

    @NotBlank(groups = {Register.class, Login.class})
    @BasicEmail(groups = {Register.class, Login.class}, message = "{email.invalid}")
    private String adminEmail;

    @NotBlank(groups = {Register.class, Login.class})
    @Pattern(regexp = "[a-z0-9]{64}", groups = {Register.class, Login.class}, message = "{password.invalid}")
    private String adminPassword;

    @NotBlank(groups = {Register.class, ResetPassword.class})
    @Pattern(regexp = "[a-z0-9]{64}", groups = {Register.class, ResetPassword.class}, message = "{password.invalid}")
    private String adminPasswordAgain;

    @NotBlank(groups = ResetPassword.class)
    @Pattern(regexp = "[a-z0-9]{64}", groups = ResetPassword.class, message = "{password.invalid}")
    private String oldAdminPassword;

    public interface Login {
    }

    public interface Register {
    }

    public interface Rename {
    }

    public interface ResetPassword {
    }

    public AdminParam() {
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminPasswordAgain() {
        return adminPasswordAgain;
    }

    public void setAdminPasswordAgain(String adminPasswordAgain) {
        this.adminPasswordAgain = adminPasswordAgain;
    }
}
