package by.alromos.java.advanced.spring.boot.controller;

import by.alromos.java.advanced.spring.boot.dto.BookRequest;
import by.alromos.java.advanced.spring.boot.model.Book;
import by.alromos.java.advanced.spring.boot.service.BookService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SecurityRequirement(name = "oauth2")
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

  private final BookService service;

  @GetMapping
  public List<Book> getAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public Book getById(
      @PathVariable
      Long id
  ) {
    return service.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Book create(
      @RequestBody
      BookRequest request
  ) {
    return service.create(request.name(), request.authorIds(), request.genres());
  }

  @PutMapping("/{id}")
  public Book update(
      @PathVariable
      Long id,
      @RequestBody
      BookRequest request
  ) {
    return service.update(
        id,
        request.name(),
        request.authorIds(),
        request.genres()
    );
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(
      @PathVariable
      Long id
  ) {
    service.delete(id);
  }

}
