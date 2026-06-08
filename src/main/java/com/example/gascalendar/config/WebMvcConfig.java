package com.example.gascalendar.config;

import com.example.gascalendar.entity.enums.UserRole;
import com.example.gascalendar.entity.enums.UserStatus;
import com.example.gascalendar.interceptor.AdminInterceptor;
import com.example.gascalendar.interceptor.SessionAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final SessionAuthInterceptor sessionAuthInterceptor;
    private final AdminInterceptor adminInterceptor;

    public WebMvcConfig(SessionAuthInterceptor sessionAuthInterceptor, AdminInterceptor adminInterceptor) {
        this.sessionAuthInterceptor = sessionAuthInterceptor;
        this.adminInterceptor = adminInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionAuthInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/auth/login",
                        "/api/auth/register"
                );

        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/api/admin/**");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(
                String.class,
                UserRole.class,
                value -> UserRole.valueOf(value.toUpperCase())
        );
        registry.addConverter(
                String.class,
                UserStatus.class,
                value -> UserStatus.valueOf(value.toUpperCase())
        );
    }
}
