package com.log.blog.entity;

import com.log.blog.validator.annotation.BasicEmail;

import javax.validation.constraints.Pattern;
import java.util.Objects;

public class User {
    private String userId;

    @Pattern(regexp = "[\\w-]{1,20}", groups = register.class, message = "{name.invalid}")
    private String userName;

    @BasicEmail(groups = {register.class, login.class}, message = "{email.invalid}")
    private String userEmail;

    @Pattern(regexp = "[a-z0-9]{64}", groups = {register.class, login.class}, message = "{password.invalid}")
    private String userPassword;

    public interface login {
    }

    public interface register {
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(userEmail, user.userEmail) &&
                Objects.equals(userPassword, user.userPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, userEmail, userPassword);
    }
}
