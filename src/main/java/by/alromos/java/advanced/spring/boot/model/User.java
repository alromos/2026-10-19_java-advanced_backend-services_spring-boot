package by.alromos.java.advanced.spring.boot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    name = "users"
)
public class User {

  @Id
  @GeneratedValue(
      strategy = GenerationType.IDENTITY
  )
  @Column(
      name = "id"
  )
  private Long id;

  @Column(
      name = "username"
  )
  private String username;

  @Column(
      name = "password"
  )
  private String password;

  @ManyToMany(
      fetch = FetchType.EAGER
  )
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(
          name = "user_id"
      ),
      inverseJoinColumns = @JoinColumn(
          name = "role_id"
      )
  )
  private Set<Role> roles = new HashSet<>();

}
