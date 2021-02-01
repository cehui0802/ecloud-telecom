package com.telecom.ecloud.base.samples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wacxhs
 * @date 2018-07-11
 */
@Configuration
@ComponentScan({"com.telecom.ecloudframework.*","com.telecom.ecloud.*"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class OAApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(OAApplication.class, args);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowCredentials(true)
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
