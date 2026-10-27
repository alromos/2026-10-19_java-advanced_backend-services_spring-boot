package by.alromos.java.advanced.spring.boot.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Book API")
                .version("1.0")
                .description("REST API for managing a catalog of books.")
        )
        .components(new Components()
            .addSecuritySchemes(
                "oauth2",
                new SecurityScheme()
                    .type(SecurityScheme.Type.OAUTH2)
                    .flows(
                        new OAuthFlows()
                            .password(
                                new OAuthFlow()
                                    .tokenUrl("/api/auth/token")
                            )
                    )
            )
        );
  }

}
