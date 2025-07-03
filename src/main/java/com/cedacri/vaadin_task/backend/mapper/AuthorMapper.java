package com.cedacri.vaadin_task.backend.mapper;

import com.cedacri.vaadin_task.backend.dto.AuthorDto;
import com.cedacri.vaadin_task.backend.dto.BookDto;
import com.cedacri.vaadin_task.backend.entity.Author;
import com.cedacri.vaadin_task.backend.entity.Book;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AuthorMapper {
    public static Author mapToAuthor(AuthorDto authorDto) {
        List<Book> books = Optional.ofNullable(authorDto.getBooks())
                .orElse(Collections.emptyList())
                .stream()
                .map(BookMapper::mapToBook)
                .toList();

        return Author.builder()
                .firstname(authorDto.getFirstname())
                .lastname(authorDto.getLastname())
                .books(books)
                .build();
    }

    public static AuthorDto mapToAuthorDTO(Author author) {
        List<BookDto> books = author.getBooks().stream()
                .map(BookMapper::mapToBookDTO)
                .toList();

        return AuthorDto.builder()
                .id(author.getId())
                .firstname(author.getFirstname())
                .lastname(author.getLastname())
                .books(books)
                .build();
    }

    public static AuthorDto mapToAuthorDtoWithoutBooks(Author author) {
        if (author == null) return null;

        return AuthorDto.builder()
                .id(author.getId())
                .firstname(author.getFirstname())
                .lastname(author.getLastname())
                .build();
    }

    public static AuthorDto mapToAuthorDtoWithBooks(Author author) {
        if (author == null) return null;

        return AuthorDto.builder()
                .id(author.getId())
                .firstname(author.getFirstname())
                .lastname(author.getLastname())
                .books(author.getBooks() != null
                        ? author.getBooks().stream().map(BookMapper::mapToBookDTO).toList()
                        : List.of())
                .build();
    }
}
