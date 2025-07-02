package com.cedacri.vaadin_task.backend.service.impl;

import com.cedacri.vaadin_task.backend.dto.BookDto;
import com.cedacri.vaadin_task.backend.entity.Book;
import com.cedacri.vaadin_task.backend.exception.BookNotFoundException;
import com.cedacri.vaadin_task.backend.mapper.BookMapper;
import com.cedacri.vaadin_task.backend.repository.BookRepository;
import com.cedacri.vaadin_task.backend.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cedacri.vaadin_task.backend.mapper.BookMapper.mapToBook;
import static com.cedacri.vaadin_task.backend.mapper.BookMapper.mapToBookDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional
    public void saveBook(BookDto bookDto) {
        Book book = mapToBook(bookDto);
        log.info("Trying to save book : {}", book);
        bookRepository.save(book);
        log.info("Book was saved to database : {}", book);
    }

    @Override
    @Transactional
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::mapToBookDTO)
                .toList();
    }

    @Override
    @Transactional
    public BookDto getBookByName(String name) {
        log.info("Trying to find book by name: {}", name);

        Book book = bookRepository.findByName(name)
                .orElseThrow(() -> new BookNotFoundException(String.format("Book %s not found.", name)));

        return mapToBookDTO(book);
    }

    @Override
    @Transactional
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(String.format("Book id:%d not found.", bookId)));

        log.info("Trying to delete book : {}", book.getName());
        bookRepository.deleteById(bookId);
        log.info("Book was deleted : {}", book.getName());
    }
}
