package by.alromos.java.advanced.spring.boot.metrics;

import by.alromos.java.advanced.spring.boot.model.Genre;
import by.alromos.java.advanced.spring.boot.repository.BookRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class BooksGenreMetrics {

  public BooksGenreMetrics(
      BookRepository bookRepository,
      MeterRegistry meterRegistry
  ) {
    for (Genre genre : Genre.values()) {
      Gauge.builder("books.genre.count", bookRepository, repo -> repo.countByGenre(genre))
          .tag("genre", genre.name())
          .description("Number of books per genre")
          .register(meterRegistry);
    }
  }

}
