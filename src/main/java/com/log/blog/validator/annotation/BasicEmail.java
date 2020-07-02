package com.log.blog.validator.annotation;

import org.hibernate.validator.constraints.Length;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Email;
import java.lang.annotation.*;

@Length
@Email(regexp = "\\w{0,64}@\\w+(\\.\\w+)+")
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface BasicEmail {
    @OverridesAttribute(constraint = Email.class) String message() default "{javax.validation.constraints.Email.message}";
    @OverridesAttribute(constraint = Length.class) int min() default 5;
    @OverridesAttribute(constraint = Length.class) int max() default 320;
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
