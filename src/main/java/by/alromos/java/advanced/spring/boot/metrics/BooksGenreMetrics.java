package by.alromos.java.advanced.spring.boot.metrics;

import by.alromos.java.advanced.spring.boot.model.Genre;
import by.alromos.java.advanced.spring.boot.repository.BookRepository;
import by.alromos.java.advanced.spring.boot.repository.BookRepository.GenreCount;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import java.time.Duration;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class BooksGenreMetrics {

  private static final Object CACHE_KEY = new Object();

  private final LoadingCache<Object, Map<Genre, Long>> countsCache;

  public BooksGenreMetrics(
      BookRepository bookRepository,
      MeterRegistry meterRegistry
  ) {
    this.countsCache = Caffeine.newBuilder()
        .expireAfterWrite(Duration.ofMinutes(1))
        .build(key -> loadCounts(bookRepository));

    for (Genre genre : Genre.values()) {
      Gauge.builder("books.genre.count", this, metrics -> metrics.count(genre))
          .tag("genre", genre.name())
          .description("Number of books per genre")
          .register(meterRegistry);
    }
  }

  private long count(Genre genre) {
    return countsCache.get(CACHE_KEY).getOrDefault(genre, 0L);
  }

  private static Map<Genre, Long> loadCounts(BookRepository bookRepository) {
    return bookRepository.countBooksGroupedByGenre().stream()
        .collect(Collectors.toMap(
            GenreCount::genre,
            GenreCount::count,
            Long::sum,
            () -> new EnumMap<>(Genre.class)
        ));
  }

}
