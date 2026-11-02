package by.alromos.java.advanced.spring.boot.service;

import by.alromos.java.advanced.spring.boot.exception.NotFoundException;
import by.alromos.java.advanced.spring.boot.model.Author;
import by.alromos.java.advanced.spring.boot.model.Book;
import by.alromos.java.advanced.spring.boot.model.Genre;
import by.alromos.java.advanced.spring.boot.repository.AuthorRepository;
import by.alromos.java.advanced.spring.boot.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        .orElseThrow(() -> buildBookNotFoundByIdException(id));
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

    throw buildBookNotFoundByIdException(id);
  }

  private Set<Author> resolveAuthors(Set<Long> authorIds) {
    if (authorIds == null) {
      return new HashSet<>();
    }

    return authorIds.stream()
        .map(authorId -> authorRepository.findById(authorId)
            .orElseThrow(() -> new NotFoundException("Author not found: " + authorId))
        )
        .collect(Collectors.toSet());
  }

  private static NotFoundException buildBookNotFoundByIdException(Long id) {
    return new NotFoundException("Book not found: " + id);
  }

}
