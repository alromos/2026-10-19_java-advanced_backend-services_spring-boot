package by.alromos.java.advanced.spring.boot.repository;

import by.alromos.java.advanced.spring.boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

}
