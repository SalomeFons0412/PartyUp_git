package com.example.PartyUp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // ✅ ESTA LÍNEA ES CLAVE
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/**").permitAll() // ✅ PERMITIR TODO TEMPORALMENTE
                .anyRequest().authenticated()
            );
        
        return http.build();
    }
}