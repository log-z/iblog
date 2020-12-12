package com.log.blog.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.log.blog.dto.UserRegisterForm;
import com.log.blog.entity.User;
import com.log.blog.service.UserService;
import com.log.blog.validator.PasswordAgainValidator;
import com.log.blog.vo.RestResult;
import com.log.blog.vo.RestUser;
import com.log.blog.vo.View;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.log.blog.controller.UserPublicController.SESSION_KEY_USER_IDENTITY;

@RestController
@RequestMapping("/api/user")
public class UserPublicRestController {
    private static final String DATA_PROPERTY_USER_INFO = "user";

    private final UserService userService;
    private final ConversionService restConversionService;
    private final Validator passwordAgainValidator;

    public UserPublicRestController(
            @Qualifier("userBasicService") UserService userService,
            @Qualifier("entity2VOConversionService") ConversionService restConversionService,
            PasswordAgainValidator passwordAgainValidator
    ) {
        this.userService = userService;
        this.restConversionService = restConversionService;
        this.passwordAgainValidator = passwordAgainValidator;
    }

    @PostMapping
    @JsonView(View.Guest.class)
    public RestResult register(
            @Validated(User.Register.class) UserRegisterForm form,
            BindingResult errors,
            @ModelAttribute RestResult result,
            HttpServletResponse response) {
        if (!errors.hasErrors()) {
            passwordAgainValidator.validate(form, errors);
        }
        if (!errors.hasErrors() && userService.register(form)) {
            response.setStatus(201);
            return result.setDateMessage("register.successful", null);
        } else {
            response.setStatus(400);
            errors.reject("register.failed");
            return result.setErrors(errors);
        }
    }

    @PostMapping("/session")
    @JsonView(View.Guest.class)
    public RestResult login(
            @Validated(User.Login.class) User user,
            BindingResult errors,
            HttpSession session,
            @ModelAttribute RestResult result,
            HttpServletResponse response
    ) {
        if (!errors.hasErrors()) {
            String userId = userService.loginCheck(user);
            if (userId != null) {
                response.setStatus(201);
                session.setAttribute(SESSION_KEY_USER_IDENTITY, userId);
                return result.setDateMessage("login.successful", null);
            }
        }
        response.setStatus(400);
        errors.reject("login.failed");
        return result.setErrors(errors);
    }

    @GetMapping("/{userId:\\d{1,11}}$base")
    @JsonView(View.Guest.class)
    public RestResult info(
            @PathVariable String userId,
            @ModelAttribute RestResult result,
            HttpServletRequest request
    ) throws NoHandlerFoundException {
        User user = userService.getUser(userId);
        if (user == null)
            throw new NoHandlerFoundException("GET", request.getRequestURI(), new HttpHeaders());

        RestUser restUser = restConversionService.convert(user, RestUser.class);
        return result.setDataProperty(DATA_PROPERTY_USER_INFO, restUser);
    }

    @RequestMapping("/error401")
    @JsonView(View.Guest.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RestResult error401(@ModelAttribute RestResult result) {
        return result.setError(null, "error.user.unauthorized", null);
    }
}
