package com.log.blog.config;

import org.springframework.context.annotation.*;


@Configuration
@PropertySource("/WEB-INF/application.properties")
@ImportResource("/WEB-INF/config/beans.xml")
@ComponentScan(basePackages = {"com.log.blog.service", "com.log.blog.controller"})
public class BlogConfig {
}
