package by.alromos.java.advanced.spring.boot.dto;

import by.alromos.java.advanced.spring.boot.model.Genre;
import java.util.Set;

public record BookRequest(String name, Set<Long> authorIds, Set<Genre> genres) {

}
