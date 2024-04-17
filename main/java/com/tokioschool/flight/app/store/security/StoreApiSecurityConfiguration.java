package com.tokioschool.flight.app.store.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class StoreApiSecurityConfiguration {

    @Bean
    public SecurityFilterChain configureApiConfiguration(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher("/store/api/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/store/api/auth").permitAll()
                        .requestMatchers(HttpMethod.POST, "/store/api/resources")
                        .hasAuthority("write-resource")
                        .requestMatchers(HttpMethod.DELETE, "/store/api/resource/**")
                        .hasAuthority("write-resource")
                        .requestMatchers(HttpMethod.GET, "/store/api/resources/**")
                        .hasAnyAuthority("write-resource", "read-resource")
                        .requestMatchers("/store/api/**").authenticated()
                )
                .oauth2ResourceServer(oauth2 ->oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .cors(CorsConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .build();
    }
    private  static class  CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

        @Override
        public AbstractAuthenticationToken convert(Jwt source) {
            JwtAuthenticationConverter jwtAuthenticationConverter= new JwtAuthenticationConverter();
            jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                    jwt->((List<String>) jwt.getClaim("authorities"))
                            .stream().map(s-> (GrantedAuthority) new SimpleGrantedAuthority(s)).toList());
            return jwtAuthenticationConverter.convert(source);
        }
    }
}
