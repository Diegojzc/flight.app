package com.tokioschool.flight.app.store.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StoreConfigurationProperties.class)
public class StoreConfiguration {

}
