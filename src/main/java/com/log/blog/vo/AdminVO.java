package com.log.blog.vo;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.Objects;

public class AdminVO {
    @JsonView({View.Owner.class, View.Admin.class})
    private String adminId;

    @JsonView({View.Owner.class, View.Admin.class})
    private String adminName;

    @JsonView({View.Owner.class, View.Admin.class})
    private String adminEmail;

    public AdminVO() {
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
        AdminVO adminVO = (AdminVO) o;
        return Objects.equals(adminId, adminVO.adminId) &&
                Objects.equals(adminName, adminVO.adminName) &&
                Objects.equals(adminEmail, adminVO.adminEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adminId, adminName, adminEmail);
    }
}
