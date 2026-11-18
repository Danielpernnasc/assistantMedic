package com.trainday.microServiceAssistantMedic.infra.Config;

import com.trainday.microServiceAssistantMedic.infra.Security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import static org.springframework.security.web.util.matcher.RegexRequestMatcher.regexMatcher;

@Configuration
public class SecurityConfig {

    private static final String AUTH = "/auth/*";
    private static final String DOCTOR_SEARCH = "/Doctor/searchdoctor/*";
    private static final String DOCTOR_POST = "/Doctor";
    private static final String DOCTOR = "DOCTOR";
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }
    @Bean
    @Order(1)
    public SecurityFilterChain swaggerSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(swaggerRequestMatcher())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }

    @SuppressWarnings("java:S4502")
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Authentication uses the Authorization header, not session cookies.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/actuator/**",
                                "/error",
                                "/auth/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, DOCTOR_SEARCH).hasRole(DOCTOR)
                        .requestMatchers(HttpMethod.GET, DOCTOR_POST).hasRole(DOCTOR)
                        .requestMatchers(HttpMethod.POST, AUTH).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private OrRequestMatcher swaggerRequestMatcher() {
        return new OrRequestMatcher(
                regexMatcher("^/v3/api-docs(/.*)?$"),
                regexMatcher("^/swagger-ui(/.*)?$"),
                regexMatcher("^/swagger-ui\\.html$"),
                regexMatcher("^/swagger-resources(/.*)?$"),
                regexMatcher("^/webjars(/.*)?$"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
