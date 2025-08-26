package com.project.interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Register interceptor for all /orders/** APIs
        registry.addInterceptor(new OrderValidationInterceptor())
                .addPathPatterns("/orders/**");
    }
}
