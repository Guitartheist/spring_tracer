package com.example.demo.config;

import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Component
public class AppConfig {

  @Bean
  public BCryptPasswordEncoder encodePassword() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    ArrayList<String> allowedOrigins = new ArrayList<String>();
    allowedOrigins.add("http://localhost:3000/");
    allowedOrigins.add("https://localhost:3000/");
    config.setAllowedOrigins(allowedOrigins);
    ArrayList<String> exposedHeaders = new ArrayList<String>();
    exposedHeaders.add("Authorization");
    exposedHeaders.add("noAccountCartID");
    config.setExposedHeaders(exposedHeaders);
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}