package com.tokioschool.flight.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class MvcSecurityConfiguration {

    @Bean
    public SecurityFilterChain configureMvcSecurity(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .securityMatcher("/flight/**", "/login", "/logout")
                .authorizeHttpRequests(authorizeRequests->
                        authorizeRequests
                                .requestMatchers("/login", "/logout","/flight/my-error")
                                .permitAll()
                                .requestMatchers("/flight/flights-edit")
                                .hasAuthority("ADMIN")
                                .requestMatchers("/flight/**")
                                .authenticated())
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .build();
    }

}
