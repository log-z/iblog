package com.log.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {
    @RequestMapping("/redirect")
    String redirect(@RequestAttribute(required = false) String redirectPath) {
        if (redirectPath == null)
            return null;
        return "redirect:" + redirectPath;
    }
}
