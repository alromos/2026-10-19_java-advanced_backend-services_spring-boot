package by.alromos.java.advanced.spring.boot.health;

import by.alromos.java.advanced.spring.boot.repository.AuthorRepository;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class BookManagementReadinessStateHealthIndicator implements HealthIndicator {

  private final AuthorRepository authorRepository;

  public BookManagementReadinessStateHealthIndicator(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  @Override
  public Health health() {
    try {
      var count = authorRepository.count();

      if (count > 0) {
        return Health.up()
            .withDetail("authorsCount", count)
            .build();
      }
    } catch (Exception e) {
      return Health.outOfService()
          .withDetail("message", "Failed to count authors")
          .build();
    }


    return Health.outOfService()
        .withDetail("message", "No authors found in the database")
        .build();
  }

}
