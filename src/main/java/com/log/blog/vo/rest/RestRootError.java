package com.log.blog.vo.rest;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;
import java.util.Objects;

public class RestRootError extends RestError {
    @JsonView(View.Base.class)
    private List<RestError> details;

    public List<RestError> getDetails() {
        return details;
    }

    public void setDetails(List<RestError> details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestRootError that = (RestRootError) o;
        return Objects.equals(details, that.details) && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(details, super.hashCode());
    }
}
