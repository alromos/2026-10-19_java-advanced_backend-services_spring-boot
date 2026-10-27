package by.alromos.java.advanced.spring.boot.repository;

import by.alromos.java.advanced.spring.boot.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
