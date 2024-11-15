package com.java.springBoot.app.SecurityConfig.JWT;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // In a real app, fetch user details from a database
        if ("user".equals(username)) {
            return new User("user", "{noop}password", new ArrayList<>()); // Password: "password"
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
