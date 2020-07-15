package com.log.blog.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class NameForm {
    @NotBlank
    @Pattern(regexp = "[\\w\\u4e00-\\u9fa5\\-]{1,20}", message = "{name.invalid}")
    private String name;

    public NameForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
