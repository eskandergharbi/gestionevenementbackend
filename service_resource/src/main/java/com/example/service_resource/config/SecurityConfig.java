package com.example.service_resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthConverter authConverter;

    public SecurityConfig(JwtAuthConverter authConverter) {
        this.authConverter = authConverter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("http://localhost:2000"); // Allow your frontend origin
        configuration.addAllowedHeader("*"); // Allow all headers
        configuration.addAllowedMethod("*"); // Allow all methods
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors() // Enable CORS
            .and()
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Allow preflight requests
                // POST, PUT, DELETE -> only Admin
                .requestMatchers(HttpMethod.POST, "/api/registrations/register/**").hasRole("Admin")
                .requestMatchers(HttpMethod.POST, "/api/registrations/cancel/**").hasRole("Admin")
                .requestMatchers(HttpMethod.POST, "/api/users/**").hasRole("Admin")
                .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("Admin")
                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("Admin")
                .requestMatchers(HttpMethod.GET, "/api/users/**").authenticated()
                // Eureka dashboard
                .requestMatchers("/eureka/**").permitAll()
                .requestMatchers("/eureka/web").permitAll()
                .anyRequest().authenticated() // All other requests require authentication
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(authConverter))
            );

        return http.build();
    }
}