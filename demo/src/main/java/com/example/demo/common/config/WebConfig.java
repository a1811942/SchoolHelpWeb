package com.example.demo.common.config;

import com.example.demo.common.intercept.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private AuthInterceptor authInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/demo/**").
                excludePathPatterns("/demo/student/login","/demo/student/register","/demo/student/email",
                        "/demo/photo/down","/demo/UpdateAndDown/down","/demo/like/**",
                        "/demo/**");
    }
}
