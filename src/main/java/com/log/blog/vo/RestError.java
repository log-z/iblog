package com.log.blog.vo;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.Objects;

public class RestError {
    @JsonView(View.Base.class)
    private String target;

    @JsonView(View.Base.class)
    private String message;

    public RestError() {
    }

    public RestError(String target, String message) {
        this.target = target;
        this.message = message;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestError)) return false;
        RestError that = (RestError) o;
        return Objects.equals(target, that.target) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(target, message);
    }
}
