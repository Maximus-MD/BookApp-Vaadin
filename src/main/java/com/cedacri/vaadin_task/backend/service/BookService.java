package com.cedacri.vaadin_task.backend.service;

import com.cedacri.vaadin_task.backend.dto.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks();

    BookDto getBookByName(String name);

    BookDto saveBook(BookDto bookDto);

    void deleteBookByName(String name);
}
