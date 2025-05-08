package com.example.service_evenement.config;
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
                // POST, PUT, DELETE -> only ADMIN
                .requestMatchers(HttpMethod.POST, "/events/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/events/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/events/**").hasRole("ADMIN")
                
                // GET -> authenticated users (USER or ADMIN)
                .requestMatchers(HttpMethod.GET, "/events/**").authenticated()
                
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
