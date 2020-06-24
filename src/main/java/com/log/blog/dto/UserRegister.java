package com.log.blog.dto;


import com.log.blog.entity.User;

public class UserRegister extends User {
    private String userPasswordAgain;

    public String getUserPasswordAgain() {
        return userPasswordAgain;
    }

    public void setUserPasswordAgain(String userPasswordAgain) {
        this.userPasswordAgain = userPasswordAgain;
    }
}
