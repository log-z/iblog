package com.log.blog.config;

import com.fasterxml.jackson.core.JsonEncoding;
import com.log.blog.converter.*;
import org.hibernate.validator.HibernateValidator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Set;


@Configuration
@MapperScan("com.log.blog.mapper")
public class BlogConfig implements WebMvcConfigurer {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LocalValidatorFactoryBean globalValidator(MessageSource messageSource) {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }

    @Bean
    public ConversionServiceFactoryBean restConverterService(Admin2RestAdminConverter admin2RestAdminConverter,
                                                             User2RestUserConverter user2RestUserConverter,
                                                             Article2RestArticleConverter article2RestArticleConverter,
                                                             Page2PageRangeVOConverter page2PageRangeConverter) {
        ConversionServiceFactoryBean converterService = new ConversionServiceFactoryBean();
        converterService.setConverters(Set.of(
                admin2RestAdminConverter,
                user2RestUserConverter,
                article2RestArticleConverter,
                page2PageRangeConverter
        ));
        return converterService;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/m/*.html").addResourceLocations("classpath:/html/");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // JSP
        registry.jsp("/templates/", "");

        // Restful API
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        jsonView.setEncoding(JsonEncoding.UTF8);
        registry.enableContentNegotiation(jsonView);
    }
}
