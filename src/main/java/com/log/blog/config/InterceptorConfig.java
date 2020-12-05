package com.log.blog.config;

import com.log.blog.interceptor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;


@Configuration
@PropertySource("/application.properties")
public class InterceptorConfig implements WebMvcConfigurer {

    // JSP - userRequiredInterceptor
    private UserRequiredInterceptor uri4Jsp;
    // JSP - userOptionalInterceptor
    private UserOptionalInterceptor uoi4Jsp;
    // JSP - userLoggedInterceptor
    private UserLoggedInterceptor uli4Jsp;
    // JSP - adminRequiredInterceptor
    private AdminRequiredInterceptor ari4Jsp;
    // JSP - adminLoggedInterceptor
    private AdminLoggedInterceptor ali4Jsp;
    // Restful API - restUserRequiredInterceptor
    private UserRequiredInterceptor uri4Rest;
    // Restful API - userOptionalInterceptor
    private UserOptionalInterceptor uoi4Rest;
    // Restful API - adminRequiredInterceptor
    private AdminRequiredInterceptor ari4Rest;
    // Static - userRequiredInterceptor
    private UserRequiredInterceptor uri4Static;
    // Static - adminRequiredInterceptor
    private AdminRequiredInterceptor ari4Static;

    @Autowired
    public void setUri4Jsp(UserRequiredInterceptor userRequiredInterceptor) {
        uri4Jsp = userRequiredInterceptor;
        uri4Jsp.setErrorPath("/error/401");
    }

    @Autowired
    public void setUoi4Jsp(UserOptionalInterceptor userOptionalInterceptor) {
        uoi4Jsp = userOptionalInterceptor;
    }

    @Autowired
    public void setUli4Jsp(UserLoggedInterceptor userLoggedInterceptor) {
        uli4Jsp = userLoggedInterceptor;
    }

    @Autowired
    public void setAri4Jsp(AdminRequiredInterceptor adminRequiredInterceptor) {
        ari4Jsp = adminRequiredInterceptor;
        ari4Jsp.setErrorPath("/admin/error/401");
    }

    @Autowired
    public void setAli4Jsp(AdminLoggedInterceptor adminLoggedInterceptor) {
        ali4Jsp = adminLoggedInterceptor;
    }

    @Autowired
    public void setUri4Rest(UserRequiredInterceptor userRequiredInterceptor) {
        uri4Rest = userRequiredInterceptor;
        uri4Rest.setErrorPath("/api/user/error401");
        uri4Rest.setAdvancedExcludeMapping(Set.of(
                "/api/user                                -> POST",
                "/api/user/session                        -> POST",
                "/api/user/{userId:\\d{1,11}}             -> GET, DELETE",  // DELETE由管理员拦截
                "/api/user/{userId:\\d{1,11}}$base        -> GET",
                "/api/user/list*                          -> GET",          // GET由管理员拦截
                "/api/article/{articleId:[A-Za-z\\d]{32}} -> GET, DELETE",  // DELETE由管理员拦截
                "/api/article/list                        -> GET</value>"
        ));
    }

    @Autowired
    public void setUoi4Rest(UserOptionalInterceptor userOptionalInterceptor) {
        uoi4Rest = userOptionalInterceptor;
        uoi4Rest.setAdvancedMapping(Set.of(
                "/api/user/{userId:\\d{1,11}} -> GET"
        ));
    }

    @Autowired
    public void setAri4Rest(AdminRequiredInterceptor adminRequiredInterceptor) {
        ari4Rest = adminRequiredInterceptor;
        ari4Rest.setErrorPath("/api/admin/error401");
        ari4Rest.setAdvancedExcludeMapping(Set.of(
                "/api/admin                                 -> POST",
                "/api/admin/session                         -> POST",
                "/api/user/{userId:\\d{1,11}}               -> GET",              // GET由用户拦截
                "/api/article/{articleId:[A-Za-z\\d]{32}}   -> GET, POST, PUT"
        ));
    }

    @Autowired
    public void setUri4Static(UserRequiredInterceptor userRequiredInterceptor) {
        uri4Static = userRequiredInterceptor;
        uri4Static.setErrorPath("/m/user-login.html");
    }

    @Autowired
    public void setAri4Static(AdminRequiredInterceptor adminRequiredInterceptor) {
        ari4Static = adminRequiredInterceptor;
        ari4Static.setErrorPath("/m/admin-login.html");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // JSP - userRequiredInterceptor
        registry.addInterceptor(uri4Jsp)
                .addPathPatterns(
                        "/{userId:\\d+}/**",
                        "/edit/**",
                        "/update/**",
                        "/logout"
                ).excludePathPatterns("/{userId:\\d+}");

        // JSP - userOptionalInterceptor
        registry.addInterceptor(uoi4Jsp)
                .addPathPatterns(
                        "/",
                        "/{userId:\\d+}",
                        "/article/{articleId:[A-Za-z\\d]{32}}",
                        "/s"
                );

        // JSP - userLoggedInterceptor
        registry.addInterceptor(uli4Jsp)
                .addPathPatterns(
                        "/login",
                        "/register"
                );

        // JSP - adminRequiredInterceptor
        registry.addInterceptor(ari4Jsp)
                .addPathPatterns(
                        "/admin",
                        "/admin/**"
                ).excludePathPatterns(
                        "/admin/error/*",
                        "/admin/login",
                        "/admin/register"
                );

        // JSP - adminLoggedInterceptor
        registry.addInterceptor(ali4Jsp)
                .addPathPatterns(
                        "/admin/login",
                        "/admin/register"
                );

        // Restful API - userRequiredInterceptor
        registry.addInterceptor(uri4Rest)
                .addPathPatterns(
                        "/api/user",
                        "/api/user/**",
                        "/api/article",
                        "/api/article/**"
                ).excludePathPatterns("/api/user/error*");

        // Restful API - userOptionalInterceptor
        registry.addInterceptor(uoi4Rest)
                .addPathPatterns("/api/user/{userId:\\d{1,11}}");

        // Restful API - adminRequiredInterceptor
        registry.addInterceptor(ari4Rest)
                .addPathPatterns(
                        "/api/admin",
                        "/api/admin/**",
                        "/api/user/{userId:\\d{1,11}}",
                        "/api/user/list*",
                        "/api/article/{articleId:[A-Za-z\\d]{32}}"
                ).excludePathPatterns("/api/admin/error*");

        // Static - userRequiredInterceptor
        registry.addInterceptor(uri4Static)
                .addPathPatterns(
                        "/m/user*.html",
                        "/m/article*.html"
                ).excludePathPatterns(
                        "/m/user.html",
                        "/m/user-login.html",
                        "/m/user-register.html",
                        "/m/article.html"
                );

        // Static - adminRequiredInterceptor
        registry.addInterceptor(ari4Static)
                .addPathPatterns("/m/admin*.html")
                .excludePathPatterns(
                        "/m/admin-login.html",
                        "/m/admin-register.html"
                );
    }
}
