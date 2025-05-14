package com.example.communication_service.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthConverter authConverter;

    public SecurityConfig(JwtAuthConverter authConverter) {
        this.authConverter = authConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // POST, PUT, DELETE -> only ADMI
                    .requestMatchers(HttpMethod.POST, "/api/announcements/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/announcements/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/announcements/**").hasRole("ADMIN")
                    
                    // GET -> authenticated users (USER or ADMIN)
                    .requestMatchers(HttpMethod.GET, "/api/announcements/**").authenticated()
                    .requestMatchers("/chat/**").permitAll()
                    .requestMatchers("/ws/**").permitAll() // ou .authenticated() selon ton besoin

                // Allow access to eureka dashboard without authentication
                .requestMatchers("/eureka/**").permitAll()
                .requestMatchers("/eureka/web").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(authConverter)
                )
            );

        return http.build();
    }
}
