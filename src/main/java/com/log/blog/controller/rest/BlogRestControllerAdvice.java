package com.log.blog.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.log.blog.vo.rest.RestResult;
import com.log.blog.vo.rest.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Locale;

@RestControllerAdvice (basePackages = "com.log.blog.controller.rest")
public class BlogRestControllerAdvice {
    private static final String PATTERN_REQUEST_ID = "[A-Za-z0-9]{0,1024}";
    private RestResult result;

    @Autowired
    public void setResult(RestResult result) {
        this.result = result;
    }

    @ModelAttribute
    public void initModel(Model model, @RequestParam(required = false) String requestId, Locale locale) {
        if (requestId != null && !requestId.matches(PATTERN_REQUEST_ID))
            requestId = null;
        RestResult result = this.result.setRequestId(requestId).setLocale(locale);
        model.addAttribute("restResult", result);
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    @JsonView(View.Base.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public RestResult error403() {
        return result.setError(null, "error.forbidden", null);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @JsonView(View.Base.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestResult error404() {
        return result.setError(null, "error.notFound", null);
    }

    @ExceptionHandler
    @JsonView(View.Base.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResult error500(Exception e) {
        e.printStackTrace();
        return result.setError(null, "error.internalServerError", null);
    }
}
