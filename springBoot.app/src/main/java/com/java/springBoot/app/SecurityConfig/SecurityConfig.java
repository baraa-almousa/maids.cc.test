package com.java.springBoot.app.SecurityConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/books/**").permitAll() // Allow public access to the book endpoints
                .anyRequest().authenticated() // Restrict all other endpoints
                .and()
                .csrf().disable(); // Disable CSRF if testing locally
    }
}
