package by.alromos.java.advanced.spring.boot.repository;

import by.alromos.java.advanced.spring.boot.model.Book;
import by.alromos.java.advanced.spring.boot.model.Genre;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {

  @Query("SELECT new by.alromos.java.advanced.spring.boot.repository.BookRepository$GenreCount(g, COUNT(b)) "
      + "FROM Book b JOIN b.genres g GROUP BY g")
  List<GenreCount> countBooksGroupedByGenre();

  record GenreCount(Genre genre, long count) {

  }

}
