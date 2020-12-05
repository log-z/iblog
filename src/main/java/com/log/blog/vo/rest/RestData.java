package com.log.blog.vo.rest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Scope("prototype")
public class RestData extends HashMap<String, Object> {
}
