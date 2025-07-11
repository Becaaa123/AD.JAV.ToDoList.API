package br.com.rebeca.ToDoList.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/usuario/**")
                .allowedOrigins("https://becaaa123.github.io/ToDoList/")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}