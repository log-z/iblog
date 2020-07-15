package com.log.blog.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class PasswordForm {
    @NotBlank
    @Pattern(regexp = "[a-z0-9]{64}", message = "{password.invalid}")
    private String oldPassword;

    @NotBlank
    @Pattern(regexp = "[a-z0-9]{64}", message = "{password.invalid}")
    private String newPassword;

    @NotBlank
    @Pattern(regexp = "[a-z0-9]{64}", message = "{password.invalid}")
    private String newPasswordAgain;

    public PasswordForm() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordAgain() {
        return newPasswordAgain;
    }

    public void setNewPasswordAgain(String newPasswordAgain) {
        this.newPasswordAgain = newPasswordAgain;
    }
}
