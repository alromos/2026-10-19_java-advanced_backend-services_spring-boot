package by.alromos.java.advanced.spring.boot.controller;

import by.alromos.java.advanced.spring.boot.dto.AuthorDto;
import by.alromos.java.advanced.spring.boot.dto.BookRequest;
import by.alromos.java.advanced.spring.boot.dto.BookDto;
import by.alromos.java.advanced.spring.boot.model.Author;
import by.alromos.java.advanced.spring.boot.model.Book;
import by.alromos.java.advanced.spring.boot.model.Genre;
import by.alromos.java.advanced.spring.boot.service.BookService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;
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
import org.springframework.web.server.ResponseStatusException;

@SecurityRequirement(name = "oauth2")
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

  private final BookService service;

  @GetMapping
  public List<BookDto> getAll() {
    var books = service.findAll();

    return books.stream()
        .map(BookController::mapBookToDto)
        .toList();
  }

  @GetMapping("/{id}")
  public BookDto getById(
      @PathVariable
      Long id
  ) {
    var book = service.findById(id);

    return mapBookToDto(book);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookDto create(
      @Valid
      @RequestBody
      BookRequest request
  ) {
    var book = service.create(
        request.name(),
        request.authorIds(),
        mapStringSetToGenreSet(request.genres())
    );

    return mapBookToDto(book);
  }

  @PutMapping("/{id}")
  public BookDto update(
      @PathVariable
      Long id,
      @Valid
      @RequestBody
      BookRequest request
  ) {
    var book = service.update(
        id,
        request.name(),
        request.authorIds(),
        mapStringSetToGenreSet(request.genres())
    );

    return mapBookToDto(book);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(
      @PathVariable
      Long id
  ) {
    service.delete(id);
  }

  private static BookDto mapBookToDto(Book book) {
    return new BookDto(
        book.getId(),
        book.getName(),
        mapAuthorSetToDtoList(book.getAuthors()),
        mapGenreSetToStringList(book.getGenres())
    );
  }

  private static List<AuthorDto> mapAuthorSetToDtoList(Set<Author> authors) {
    return authors.stream()
        .map(author -> new AuthorDto(
            author.getId(),
            author.getFirstName(),
            author.getLastName()
        ))
        .sorted(
            Comparator.comparing(AuthorDto::firstName)
                .thenComparing(AuthorDto::lastName)
        )
        .toList();
  }

  private static Genre mapStringToGenre(String genre) {
    try {
      return Genre.valueOf(genre);
    } catch (IllegalArgumentException e) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "Invalid genre: " + genre
      );
    }
  }

  private static Set<Genre> mapStringSetToGenreSet(Set<String> genreStringSet) {
    return genreStringSet.stream()
        .map(BookController::mapStringToGenre)
        .collect(Collectors.toSet());
  }

  private static List<String> mapGenreSetToStringList(Set<Genre> genres) {
    return genres.stream()
        .map(Genre::name)
        .sorted()
        .toList();
  }

}
