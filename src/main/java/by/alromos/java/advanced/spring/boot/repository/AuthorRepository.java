package by.alromos.java.advanced.spring.boot.repository;

import by.alromos.java.advanced.spring.boot.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
