package com.tokioschool.flight.app.store.security;

import com.tokioschool.flight.app.store.config.StoreConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class StoreUserDetailsServiceConfiguration {

    private final StoreConfigurationProperties storeConfigurationProperties;

    @Bean
    UserDetailsService storeUserDetailsService(){
        List<UserDetails> userDetails = storeConfigurationProperties.users()
                .stream().map(user->
                        User.builder()
                                .username(user.username())
                                .password(user.password())
                                .authorities(user.autorities().toArray(new String[0]))
                                .build()
                                ).toList();
        return new InMemoryUserDetailsManager(userDetails);
    }
}
