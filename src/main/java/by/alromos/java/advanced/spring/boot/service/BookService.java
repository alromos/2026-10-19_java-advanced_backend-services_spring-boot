package by.alromos.java.advanced.spring.boot.service;

import by.alromos.java.advanced.spring.boot.model.Author;
import by.alromos.java.advanced.spring.boot.model.Book;
import by.alromos.java.advanced.spring.boot.model.Genre;
import by.alromos.java.advanced.spring.boot.repository.AuthorRepository;
import by.alromos.java.advanced.spring.boot.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;

  @Transactional(
      readOnly = true
  )
  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  @Transactional(
      readOnly = true
  )
  public Book findById(Long id) {
    return bookRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Book not found: " + id
        ));
  }

  @Transactional
  public Book create(String name, Set<Long> authorIds, Set<Genre> genres) {
    Book book = new Book();
    book.setName(name);
    book.setAuthors(resolveAuthors(authorIds));
    book.setGenres(genres != null ? genres : new HashSet<>());

    return bookRepository.save(book);
  }

  @Transactional
  public Book update(Long id, String name, Set<Long> authorIds, Set<Genre> genres) {
    Book book = findById(id);
    book.setName(name);
    book.setAuthors(resolveAuthors(authorIds));
    book.setGenres(genres != null ? genres : new HashSet<>());

    return bookRepository.save(book);
  }

  @Transactional
  public void delete(Long id) {
    if (bookRepository.existsById(id)) {
      bookRepository.deleteById(id);
      return;
    }

    throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Book not found: " + id
    );
  }

  private Set<Author> resolveAuthors(Set<Long> authorIds) {
    if (authorIds == null) {
      return new HashSet<>();
    }

    return authorIds.stream()
        .map(authorId -> authorRepository.findById(authorId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Author not found: " + authorId
            ))
        )
        .collect(Collectors.toSet());
  }

}
