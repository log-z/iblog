package com.log.blog.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.log.blog.controller.AdminPublicController;
import com.log.blog.entity.Admin;
import com.log.blog.service.AdminAdvancedService;
import com.log.blog.vo.AdminVO;
import com.log.blog.vo.RestResult;
import com.log.blog.vo.View;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
    private static final String DATA_PROPERTY_ADMIN_INFO = "admin";
    private final AdminAdvancedService adminAdvancedService;
    private final ConversionService entity2VOConversionService;

    public AdminRestController(AdminAdvancedService adminAdvancedService,
                               @Qualifier("entity2VOConversionService") ConversionService entity2VOConversionService) {
        this.adminAdvancedService = adminAdvancedService;
        this.entity2VOConversionService = entity2VOConversionService;
    }

    @DeleteMapping("/session")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpSession session) {
        session.removeAttribute(AdminPublicController.SESSION_KEY_ADMIN_IDENTITY);
    }

    @GetMapping
    @JsonView(View.Admin.class)
    public RestResult self(
            @SessionAttribute(AdminPublicController.SESSION_KEY_ADMIN_IDENTITY) String adminId,
            @ModelAttribute RestResult result
    ) {
        Admin admin = adminAdvancedService.getAdmin(adminId);
        AdminVO adminVO = entity2VOConversionService.convert(admin, AdminVO.class);
        return result.setDataProperty(DATA_PROPERTY_ADMIN_INFO, adminVO);
    }

    @GetMapping("/{adminId:\\d{0,11}}")
    @JsonView(View.Admin.class)
    public RestResult info(
            @PathVariable String adminId,
            @ModelAttribute RestResult result,
            HttpServletRequest request
    ) throws NoHandlerFoundException {
        Admin admin = adminAdvancedService.getAdmin(adminId);
        if (admin == null)
            throw new NoHandlerFoundException("GET", request.getRequestURI(), new HttpHeaders());

        AdminVO adminVO = entity2VOConversionService.convert(admin, AdminVO.class);
        return result.setDataProperty(DATA_PROPERTY_ADMIN_INFO, adminVO);
    }
}
