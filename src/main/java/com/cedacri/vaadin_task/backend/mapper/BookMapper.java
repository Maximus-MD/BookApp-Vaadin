package com.cedacri.vaadin_task.backend.mapper;

import com.cedacri.vaadin_task.backend.dto.AuthorDto;
import com.cedacri.vaadin_task.backend.dto.BookDto;
import com.cedacri.vaadin_task.backend.entity.Book;

import static com.cedacri.vaadin_task.backend.mapper.AuthorMapper.mapToAuthor;

public class BookMapper {
    public static BookDto mapToBookDTO(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .description(book.getDescription())
                .pages(book.getPages())
                .published(book.getPublished())
                .price(book.getPrice())
                .category(book.getCategory())
                .availability(book.getAvailability())
                .author(AuthorDto.builder()
                        .id(book.getAuthor().getId())
                        .firstname(book.getAuthor().getFirstname())
                        .lastname(book.getAuthor().getLastname())
                        .build())
                .build();
    }

    public static Book mapToBook(BookDto bookDto) {
        return Book.builder()
                .id(bookDto.getId())
                .name(bookDto.getName())
                .description(bookDto.getDescription())
                .pages(bookDto.getPages())
                .published(bookDto.getPublished())
                .price(bookDto.getPrice())
                .category(bookDto.getCategory())
                .availability(bookDto.getAvailability())
                .author(mapToAuthor(bookDto.getAuthor()))
                .build();
    }
}
