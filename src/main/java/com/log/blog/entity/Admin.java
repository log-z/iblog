package com.log.blog.entity;

import com.log.blog.validator.annotation.BasicEmail;

import javax.validation.constraints.Pattern;
import java.util.Objects;

public class Admin {
    private String adminId;

    @Pattern(regexp = "[\\w\\u4e00-\\u9fa5\\-]{1,20}", groups = register.class, message = "{name.invalid}")
    private String adminName;

    @BasicEmail(groups = {register.class, login.class}, message = "{email.invalid}")
    private String adminEmail;

    @Pattern(regexp = "[a-z0-9]{64}", groups = {register.class, login.class}, message = "{password.invalid}")
    private String adminPassword;

    public interface login {
    }

    public interface register {
    }

    public Admin() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(adminId, admin.adminId) &&
                Objects.equals(adminName, admin.adminName) &&
                Objects.equals(adminEmail, admin.adminEmail) &&
                Objects.equals(adminPassword, admin.adminPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adminId, adminName, adminEmail, adminPassword);
    }
}
