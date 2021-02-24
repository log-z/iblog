package com.log.blog.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.log.blog.dto.*;
import com.log.blog.entity.User;
import com.log.blog.service.UserAdvancedService;
import com.log.blog.utils.AuthenticationUtils;
import com.log.blog.utils.ConversionUtils;
import com.log.blog.vo.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import static com.log.blog.controller.UserPublicController.SESSION_KEY_USER_IDENTITY;
import static com.log.blog.interceptor.UserRequiredInterceptor.REQUEST_KEY_CURRENT_USER;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
    private static final String DATA_PROPERTY_USER_INFO = "user";
    private static final String DATA_PROPERTY_USER_LIST = "users";
    private static final String DATA_PROPERTY_PAGE_RANGE = "pageRange";

    private final UserAdvancedService userAdvancedService;
    private final ConversionService entity2VOConversionService;

    public UserRestController(
            UserAdvancedService userAdvancedService,
            @Qualifier("entity2VOConversionService") ConversionService entity2VOConversionService
    ) {
        this.userAdvancedService = userAdvancedService;
        this.entity2VOConversionService = entity2VOConversionService;
    }

    @DeleteMapping("/session")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpSession session) {
        session.removeAttribute(SESSION_KEY_USER_IDENTITY);
    }

    @GetMapping("/{userId:\\d{1,11}}")
    @JsonView(View.Owner.class)
    public RestResult info(
            @PathVariable String userId,
            @RequestAttribute(value = REQUEST_KEY_CURRENT_USER, required = false) User user,
            @ModelAttribute RestResult result,
            HttpServletResponse response
    ) throws IOException {
        if (user == null || !userId.equals(user.getUserId()))
            response.sendRedirect("/api/user/" + userId + "$base");
        return self(user, result);
    }

    @GetMapping
    @JsonView(View.Owner.class)
    public RestResult self(
            @RequestAttribute(REQUEST_KEY_CURRENT_USER) User user,
            @ModelAttribute RestResult result
    ) {
        UserVO userVO = entity2VOConversionService.convert(user, UserVO.class);
        return result.setDataProperty(DATA_PROPERTY_USER_INFO, userVO);
    }

    @DeleteMapping("/{userId:\\d{1,11}}")
    @JsonView(View.Base.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RestResult delete(
            @PathVariable String userId,
            @ModelAttribute RestResult result,
            HttpServletRequest request
    ) throws NoHandlerFoundException {
        if (userAdvancedService.getUser(userId) == null) {
            throw new NoHandlerFoundException("DELETE", request.getRequestURI(), new HttpHeaders());
        } else {
            userAdvancedService.deleteUser(userId);
            return null;
        }
    }

    @PutMapping("/{userId:\\d{1,11}}/name")
    @JsonView(View.Base.class)
    public RestResult updateName(
            @PathVariable String userId,
            @RequestAttribute(REQUEST_KEY_CURRENT_USER) User user,
            @ModelAttribute RestResult result,
            @Validated(UserParam.Rename.class) UserParam userParam,
            BindingResult errors,
            HttpServletResponse response
    ) {
        if (errors.hasErrors()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            errors.reject("update.name.failed");
            return result.setErrors(errors);
        }

        AuthenticationUtils.checkOwnerAuthentication(user.getUserId(), userId);
        boolean successful = userAdvancedService.updateName(userId, userParam.getUserName());
        if (successful) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return null;
        } else {
            throw new RuntimeException();
        }
    }

    @PutMapping("/{userId:\\d{1,11}}/password")
    @JsonView(View.Base.class)
    public RestResult updatePassword(
            @PathVariable String userId,
            @RequestAttribute(value = REQUEST_KEY_CURRENT_USER) User user,
            @ModelAttribute RestResult result,
            @Validated(UserParam.ResetPassword.class) UserParam userParam,
            BindingResult errors,
            HttpServletResponse response
    ) {
        if (!errors.hasErrors()) {
            AuthenticationUtils.checkOwnerAuthentication(user.getUserId(), userId);
            boolean successful = userAdvancedService.updatePassword(
                    userId, userParam.getOldUserPassword(), userParam.getUserPassword()
            );
            if (successful) {
                response.setStatus(HttpStatus.NO_CONTENT.value());
                return null;
            } else {
                throw new RuntimeException();
            }
        }

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        errors.reject("update.password.failed");
        return result.setErrors(errors);
    }

    @GetMapping("/list")
    @JsonView(View.Guest.class)
    public RestResult list(
            @ModelAttribute RestResult result,
            @Validated(ValidatorGroup.Querying.class) UserParam userParam,
            BindingResult errors,
            HttpServletResponse response
    ) {
        if (errors.hasErrors()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return result.setErrors(errors);
        }

        List<User> users = userAdvancedService.listUsers(userParam);
        List<UserVO> userVOList = ConversionUtils.convertList(entity2VOConversionService, users, UserVO.class);
        PageVO pageVO = entity2VOConversionService.convert(users, PageVO.class);

        return result.setDataProperty(DATA_PROPERTY_PAGE_RANGE, pageVO)
                .setDataProperty(DATA_PROPERTY_USER_LIST, userVOList);
    }

    @GetMapping("/list$admin")
    @JsonView(View.Admin.class)
    public RestResult listThemForAdmin(
            @ModelAttribute RestResult result,
            @Validated(ValidatorGroup.Querying.class) UserParam userParam,
            BindingResult errors,
            HttpServletResponse response
    ) {
        return list(result, userParam, errors, response);
    }
}
