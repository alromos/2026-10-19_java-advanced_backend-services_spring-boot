package by.alromos.java.advanced.spring.boot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(
    name = "authors"
)
public class Author {

  @Id
  @GeneratedValue(
      strategy = GenerationType.IDENTITY
  )
  @Column(
      name = "id"
  )
  private Long id;

  @Column(
      name = "firstName",
      nullable = false
  )
  private String firstName;

  @Column(
      name = "lastName",
      nullable = false
  )
  private String lastName;

}
