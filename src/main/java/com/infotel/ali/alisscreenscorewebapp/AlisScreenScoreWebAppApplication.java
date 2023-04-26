package com.infotel.ali.alisscreenscorewebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
public class AlisScreenScoreWebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlisScreenScoreWebAppApplication.class, args);
    }


    @Configuration
    public class CorsConfiguration implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**") // Allow CORS for all endpoints
                    .allowedOrigins("http://localhost:4200") // Whitelist specific origins (or use "*")
                    .allowedMethods("*") // Allow all HTTP methods
                    .allowedHeaders("*") // Allow all headers
                    .allowCredentials(true); // Allow including credentials in CORS requests

        }
    }
}

