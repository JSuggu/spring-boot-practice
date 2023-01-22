package com.example.customerservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomerSecurity {

    @Bean
    public String createToken(HttpSecurity http) throws Exception{
        return http.authorizeRequests().anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt().toString();
    }

}
