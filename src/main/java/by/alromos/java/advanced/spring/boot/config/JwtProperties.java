package by.alromos.java.advanced.spring.boot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "java-advanced-spring-boot.security.jwt")
public record JwtProperties(String secret, Duration expiration) {}
