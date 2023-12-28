package com.group.brain_web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests( authorize -> authorize
                        .requestMatchers("/css/**","/images/**","/js/**").permitAll()
                )
                .authorizeHttpRequests( authorize -> authorize
                        /*Admin Mapping*/
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        /*welcome mapping*/
                        .requestMatchers("/","/contact","/about").permitAll()
                        /*auth mapping*/
                        .requestMatchers("/register", "/confirm").permitAll()
                        /*password reset mapping*/
                        .requestMatchers("/password/reset/**").permitAll()

                        .requestMatchers("/email/**").permitAll()

                        .anyRequest()
                        .authenticated()
                )
                .formLogin( form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .successHandler(successHandler())
                        .permitAll()
                )
                .logout( logout -> logout
                        .logoutSuccessUrl("/?logout")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
