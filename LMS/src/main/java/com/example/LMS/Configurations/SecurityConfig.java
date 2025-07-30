package com.example.LMS.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity // enables @PreAuthorize and role checks
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // login, register, etc.
                        .requestMatchers("/api/books/**").hasAnyRole("ADMIN", "STUDENT")
                        .requestMatchers("/api/authors/**").hasAnyRole("ADMIN", "STUDENT")
                        .requestMatchers("/api/publisher/**").hasAnyRole("ADMIN", "STUDENT")
                        .requestMatchers("/api/category/**").hasAnyRole("ADMIN", "STUDENT")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }


}
