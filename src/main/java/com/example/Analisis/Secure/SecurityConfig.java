package com.example.Analisis.Secure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas
                        .requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                        // Rutas para Administrador
                        .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
                        // Rutas para Asesor
                        .requestMatchers("/asesor/**").hasRole("ASESOR")
                        // Rutas para Cliente
                        .requestMatchers("/cliente/**").hasRole("CLIENTE")
                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/login-success", true)
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied"));

        return http.build();
    }
}
