package by.alromos.java.advanced.spring.boot.repository;

import by.alromos.java.advanced.spring.boot.model.Book;
import by.alromos.java.advanced.spring.boot.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {

  @Query("SELECT COUNT(b) FROM Book b WHERE :genre MEMBER OF b.genres")
  long countByGenre(@Param("genre") Genre genre);

}
