package com.log.blog.dto;

import com.log.blog.entity.User;

import javax.validation.constraints.Pattern;

public class UserRegisterForm extends User {
    @Pattern(regexp = "[a-z0-9]{64}", groups = register.class, message = "{password.invalid}")
    private String userPasswordAgain;

    public String getUserPasswordAgain() {
        return userPasswordAgain;
    }

    public void setUserPasswordAgain(String userPasswordAgain) {
        this.userPasswordAgain = userPasswordAgain;
    }
}
