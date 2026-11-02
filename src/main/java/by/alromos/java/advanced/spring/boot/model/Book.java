package by.alromos.java.advanced.spring.boot.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(
    name = "books"
)
public class Book {

  @Id
  @GeneratedValue(
      strategy = GenerationType.IDENTITY
  )
  @Column(
      name = "id"
  )
  private Long id;

  @Column(
      name = "name",
      nullable = false
  )
  private String name;

  @ManyToMany(
      fetch = FetchType.EAGER
  )
  @JoinTable(
      name = "book_authors",
      joinColumns = @JoinColumn(
          name = "book_id"
      ),
      inverseJoinColumns = @JoinColumn(
          name = "author_id"
      )
  )
  private Set<Author> authors = new HashSet<>();

  @ElementCollection(
      fetch = FetchType.EAGER
  )
  @CollectionTable(
      name = "book_genres",
      joinColumns = @JoinColumn(
          name = "book_id"
      )
  )
  @Enumerated(EnumType.STRING)
  @Column(
      name = "genre"
  )
  private Set<Genre> genres = new HashSet<>();

}
