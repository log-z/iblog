package com.log.blog.dto;

import com.log.blog.entity.Admin;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class AdminRegisterForm extends Admin {
    @NotBlank(groups = Register.class)
    @Pattern(regexp = "[a-z0-9]{64}", groups = Register.class, message = "{password.invalid}")
    private String adminPasswordAgain;

    public String getAdminPasswordAgain() {
        return adminPasswordAgain;
    }

    public void setAdminPasswordAgain(String adminPasswordAgain) {
        this.adminPasswordAgain = adminPasswordAgain;
    }
}
