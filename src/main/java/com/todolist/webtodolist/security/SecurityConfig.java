package com.todolist.webtodolist.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(autorizar -> autorizar
                        .requestMatchers("/login",
                                "/registro",
                                "/guardarusuario",
                                "/css/**",
                                "/favicon.ico")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/tareaspublicas", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(close -> close
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)// Borra la cookie en el navegador
                        .clearAuthentication(true)// Indica al SecurityContextLogoutHandler que elimine el token de autenticación del contexto de seguridad.
                        .deleteCookies("JSESSIONID")// Borra la cookie en el navegador
                );

        return http.build();

    }

}
