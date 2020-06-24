package com.log.blog.dto;

import com.log.blog.entity.Admin;

public class AdminRegister extends Admin {
    private String adminPasswordAgain;

    public String getAdminPasswordAgain() {
        return adminPasswordAgain;
    }

    public void setAdminPasswordAgain(String adminPasswordAgain) {
        this.adminPasswordAgain = adminPasswordAgain;
    }
}
