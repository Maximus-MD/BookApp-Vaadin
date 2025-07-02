package com.cedacri.vaadin_task.backend.mapper;

import com.cedacri.vaadin_task.backend.dto.BookDto;
import com.cedacri.vaadin_task.backend.entity.Book;

import static com.cedacri.vaadin_task.backend.mapper.AuthorMapper.mapToAuthor;
import static com.cedacri.vaadin_task.backend.mapper.AuthorMapper.mapToAuthorDTO;

public class BookMapper {
    public static BookDto mapToBookDTO(Book book) {
        return BookDto.builder()
                .name(book.getName())
                .description(book.getDescription())
                .pages(book.getPages())
                .published(book.getPublished())
                .price(book.getPrice())
                .category(book.getCategory())
                .availability(book.getAvailability())
                .author(mapToAuthorDTO(book.getAuthor()))
                .build();
    }

    public static Book mapToBook(BookDto bookDto) {
        return Book.builder()
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
