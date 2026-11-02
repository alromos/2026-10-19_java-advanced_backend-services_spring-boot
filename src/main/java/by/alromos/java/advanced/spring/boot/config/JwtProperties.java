package by.alromos.java.advanced.spring.boot.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(
    prefix = "java-advanced-spring-boot.security.jwt"
)
public record JwtProperties(
    @NotBlank
    String secret,
    @NotNull
    Duration expiration
) {

}
