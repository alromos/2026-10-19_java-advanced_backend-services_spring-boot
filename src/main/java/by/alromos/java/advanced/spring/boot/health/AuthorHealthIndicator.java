package by.alromos.java.advanced.spring.boot.health;

import by.alromos.java.advanced.spring.boot.repository.AuthorRepository;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class AuthorHealthIndicator implements HealthIndicator {

  private final AuthorRepository authorRepository;

  public AuthorHealthIndicator(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  @Override
  public Health health() {
    var count = authorRepository.count();

    if (count > 0) {
      return Health.up()
          .withDetail("authorsCount", count)
          .build();
    }

    return Health.down()
        .withDetail("message", "No authors found in the database")
        .build();
  }

}
