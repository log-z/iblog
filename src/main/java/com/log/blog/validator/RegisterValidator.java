package com.log.blog.validator;

import com.log.blog.dto.AdminRegister;
import com.log.blog.dto.UserRegister;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Objects;

public class RegisterValidator implements Validator {
    private static final List<Class<?>> SUPPORT_CLASS = List.of(AdminRegister.class, UserRegister.class);

    @Override
    public boolean supports(Class<?> clazz) {
        return SUPPORT_CLASS.contains(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof AdminRegister) {
            AdminRegister adminRegister = (AdminRegister) target;
            // adminPassword == adminPasswordAgain ?
            if (!Objects.equals(adminRegister.getAdminPassword(), adminRegister.getAdminPasswordAgain())) {
                errors.rejectValue("adminPasswordAgain", "passwordAgain.inconsistent");
            }
        } else if (target instanceof UserRegister) {
            UserRegister userRegister = (UserRegister) target;
            // userPassword == userPasswordAgain ?
            if (!Objects.equals(userRegister.getUserPassword(), userRegister.getUserPasswordAgain())) {
                errors.rejectValue("userPasswordAgain", "passwordAgain.inconsistent");
            }
        }
    }
}
