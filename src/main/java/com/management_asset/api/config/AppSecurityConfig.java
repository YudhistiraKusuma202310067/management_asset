package com.management_asset.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class AppSecurityConfig {
    // melakukan auth secara otomatis oleh spring
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // RBAC akses sesuai role
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((auth) -> {
                    try {
                        auth
                                .antMatchers("/api/loaning/approver-1/").hasAuthority("manager")
                                .antMatchers("/api/loaning/approver-2/").hasAuthority("procurement")
                                .antMatchers("/api/loaning/approver").hasAuthority("procurement")
                                .antMatchers("/api/loaning/approver").hasAuthority("manager")
                                .antMatchers("/api/user-management/changePassword",
                                        "/api/user-management/updateUserRole", "/api/loaning/borrower/**",
                                        "/api/loaning", "/api/asset/borrowed/**", "/api/loan-status-history/**")
                                .authenticated()
                                .anyRequest().permitAll()
                                .and()
                                .httpBasic();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        // config.addAllowedOrigin("http://localhost:3000"); "menambah origin baru
        // punya akses api siapa aja white list"
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
