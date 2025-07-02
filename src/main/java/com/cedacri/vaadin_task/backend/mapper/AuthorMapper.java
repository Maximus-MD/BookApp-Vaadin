package com.cedacri.vaadin_task.backend.mapper;

import com.cedacri.vaadin_task.backend.dto.AuthorDto;
import com.cedacri.vaadin_task.backend.entity.Author;
import com.cedacri.vaadin_task.backend.entity.Book;

import java.util.List;
import java.util.Optional;

public class AuthorMapper {
    public static Author mapToAuthor(AuthorDto authorDto) {
        Author author = Author.builder()
                .firstname(authorDto.getFirstname())
                .lastname(authorDto.getLastname())
                .build();

        List<Book> books = Optional.ofNullable(authorDto.getBooks())
                .orElse(List.of())
                .stream()
                .map(dto -> {
                    Book book = BookMapper.mapToBook(dto);
                    book.setAuthor(author);
                    return book;
                })
                .toList();

        author.setBooks(books);
        return author;
    }

    public static AuthorDto mapToAuthorDTO(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .firstname(author.getFirstname())
                .lastname(author.getLastname())
                .books(Optional.ofNullable(author.getBooks())
                        .orElse(List.of())
                        .stream()
                        .map(BookMapper::mapToBookDTO)
                        .toList())
                .build();
    }
}
