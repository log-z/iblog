package com.log.blog.entity;

import java.util.Objects;

public class Admin {
    private String adminId;

    private String adminName;

    private String adminEmail;

    private String adminPassword;

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
