package com.log.blog.vo.rest;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.Objects;

public class RestUser {
    @JsonView({View.Owner.class, View.Admin.class, View.Guest.class})
    private String userId;

    @JsonView({View.Owner.class, View.Admin.class, View.Guest.class})
    private String userName;

    @JsonView({View.Owner.class, View.Admin.class})
    private String userEmail;

    @JsonView({View.Owner.class, View.Admin.class, View.Guest.class})
    private String userHomeUrl;

    public RestUser() {
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

    public String getUserHomeUrl() {
        return userHomeUrl;
    }

    public void setUserHomeUrl(String userHomeUrl) {
        this.userHomeUrl = userHomeUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestUser restUser = (RestUser) o;
        return Objects.equals(userId, restUser.userId) &&
                Objects.equals(userName, restUser.userName) &&
                Objects.equals(userEmail, restUser.userEmail) &&
                Objects.equals(userHomeUrl, restUser.userHomeUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, userEmail, userHomeUrl);
    }
}
