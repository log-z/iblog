package com.log.blog.validator;

import com.log.blog.dto.AdminParam;
import com.log.blog.dto.UserParam;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Objects;

// TODO: 改用HibernateValidator
//   @ScriptAssert(lang = "jexl", script = "_.password eq _.passwordAgain", alias = "_", reportOn = "passwordAgain")

@Component
public class PasswordAgainValidator implements Validator {
    private static final String ERROR_CODE = "passwordAgain.inconsistent";
    private static final List<Class<?>> SUPPORT_CLASS = List.of(
            AdminParam.class, UserParam.class
    );

    @Override
    public boolean supports(Class<?> clazz) {
        return SUPPORT_CLASS.contains(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof AdminParam) {
            AdminParam adminParam = (AdminParam) target;
            // adminPassword == adminPasswordAgain ?
            if (!Objects.equals(adminParam.getAdminPassword(), adminParam.getAdminPasswordAgain())) {
                errors.rejectValue("adminPasswordAgain", ERROR_CODE);
            }
        } else if (target instanceof UserParam) {
            UserParam userParam = (UserParam) target;
            // userPassword == userPasswordAgain ?
            if (!Objects.equals(userParam.getUserPassword(), userParam.getUserPasswordAgain())) {
                errors.rejectValue("userPasswordAgain", ERROR_CODE);
            }
        }
    }
}
