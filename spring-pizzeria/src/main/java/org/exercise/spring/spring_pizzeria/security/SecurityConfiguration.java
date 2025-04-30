package org.exercise.spring.spring_pizzeria.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    @SuppressWarnings("removal")
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests()
                .requestMatchers("/pizze/create", "/pizze/edit/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/pizze/**").hasAuthority("ADMIN")
                .requestMatchers("/categories", "/categories/**").hasAuthority("ADMIN")
                .requestMatchers("/", "/pizze", "/pizze/**").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/**").permitAll()
                // attiva il form di login automatico di Spring
                .and().formLogin()
                // attiva il form di logout automatico di Spring
                .and().logout()
                // gestisce accessi negati/403
                .and().exceptionHandling();

        return httpSecurity.build();
    }

    // caricare i dati dell'utente dal database durante il login
    @Bean
    DatabaseUserDetailsService userDetailService() {
        return new DatabaseUserDetailsService();
    }

    // delega la responsabilità del password encoder al db
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailService());

        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;

    }

}
