package com.log.blog.vo.rest;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.Objects;

public class RestAdmin {
    @JsonView({View.Owner.class, View.Admin.class})
    private String adminId;

    @JsonView({View.Owner.class, View.Admin.class})
    private String adminName;

    @JsonView({View.Owner.class, View.Admin.class})
    private String adminEmail;

    public RestAdmin() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestAdmin restAdmin = (RestAdmin) o;
        return Objects.equals(adminId, restAdmin.adminId) &&
                Objects.equals(adminName, restAdmin.adminName) &&
                Objects.equals(adminEmail, restAdmin.adminEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adminId, adminName, adminEmail);
    }
}
