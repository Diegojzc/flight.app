package com.tokioschool.flight.app.store.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "application.jwt")
public record JwtConfigurationPropeties(String secret, Duration duration) {}
