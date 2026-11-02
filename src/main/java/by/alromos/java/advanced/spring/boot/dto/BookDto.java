package by.alromos.java.advanced.spring.boot.dto;

import java.util.List;

public record BookDto(
    Long id,
    String name,
    List<AuthorDto> authors,
    List<String> genres
) {

}
