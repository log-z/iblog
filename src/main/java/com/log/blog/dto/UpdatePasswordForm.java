package com.log.blog.dto;

import javax.validation.constraints.Pattern;

public class UpdatePasswordForm {
    @Pattern(regexp = "[a-z0-9]{64}", message = "{password.invalid}")
    private String oldPassword;

    @Pattern(regexp = "[a-z0-9]{64}", message = "{password.invalid}")
    private String newPassword;

    @Pattern(regexp = "[a-z0-9]{64}", message = "{password.invalid}")
    private String newPasswordAgain;

    public UpdatePasswordForm() {
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
