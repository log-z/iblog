package com.log.blog.dto;

import com.log.blog.entity.Admin;

import javax.validation.constraints.Pattern;

public class AdminRegister extends Admin {
    @Pattern(regexp = "[a-z0-9]{64}", groups = register.class, message = "{password.invalid}")
    private String adminPasswordAgain;

    public String getAdminPasswordAgain() {
        return adminPasswordAgain;
    }

    public void setAdminPasswordAgain(String adminPasswordAgain) {
        this.adminPasswordAgain = adminPasswordAgain;
    }
}
