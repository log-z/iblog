package com.log.blog.validator;

import com.log.blog.dto.AdminRegisterForm;
import com.log.blog.dto.UpdatePasswordForm;
import com.log.blog.dto.UserRegisterForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Objects;

public class PasswordAgainValidator implements Validator {
    private static final String ERROR_CODE = "passwordAgain.inconsistent";
    private static final List<Class<?>> SUPPORT_CLASS = List.of(
            AdminRegisterForm.class, UserRegisterForm.class, UpdatePasswordForm.class
    );

    @Override
    public boolean supports(Class<?> clazz) {
        return SUPPORT_CLASS.contains(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof AdminRegisterForm) {
            AdminRegisterForm form = (AdminRegisterForm) target;
            // adminPassword == adminPasswordAgain ?
            if (!Objects.equals(form.getAdminPassword(), form.getAdminPasswordAgain())) {
                errors.rejectValue("adminPasswordAgain", ERROR_CODE);
            }
        } else if (target instanceof UserRegisterForm) {
            UserRegisterForm form = (UserRegisterForm) target;
            // userPassword == userPasswordAgain ?
            if (!Objects.equals(form.getUserPassword(), form.getUserPasswordAgain())) {
                errors.rejectValue("userPasswordAgain", ERROR_CODE);
            }
        } else if (target instanceof UpdatePasswordForm) {
            UpdatePasswordForm form = (UpdatePasswordForm) target;
            // newPassword == newPasswordAgain ?
            if (!Objects.equals(form.getNewPassword(), form.getNewPasswordAgain())) {
                form.setNewPasswordAgain(null);
                errors.rejectValue("newPasswordAgain", ERROR_CODE);
            }
        }
    }
}
