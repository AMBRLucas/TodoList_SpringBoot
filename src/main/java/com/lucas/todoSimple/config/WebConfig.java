package com.lucas.todoSimple.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{
    
    @SuppressWarnings("null")
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
        .allowedOrigins("http://127.0.0.1:5500") 
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true);
    }

}
