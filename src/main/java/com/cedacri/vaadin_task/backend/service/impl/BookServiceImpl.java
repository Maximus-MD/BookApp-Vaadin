package com.cedacri.vaadin_task.backend.service.impl;

import com.cedacri.vaadin_task.backend.dto.AuthorDto;
import com.cedacri.vaadin_task.backend.dto.BookDto;
import com.cedacri.vaadin_task.backend.entity.Author;
import com.cedacri.vaadin_task.backend.entity.Book;
import com.cedacri.vaadin_task.backend.exception.BookNotFoundException;
import com.cedacri.vaadin_task.backend.mapper.BookMapper;
import com.cedacri.vaadin_task.backend.repository.AuthorRepository;
import com.cedacri.vaadin_task.backend.repository.BookRepository;
import com.cedacri.vaadin_task.backend.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.cedacri.vaadin_task.backend.mapper.AuthorMapper.mapToAuthor;
import static com.cedacri.vaadin_task.backend.mapper.BookMapper.mapToBook;
import static com.cedacri.vaadin_task.backend.mapper.BookMapper.mapToBookDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public BookDto saveBook(BookDto bookDto) {
        AuthorDto authorDto = bookDto.getAuthor();

        Optional<Author> existing = authorRepository.findByLastname(authorDto.getLastname());

        Author author = new Author();
        if (existing.isPresent()) {
            author = existing.get();
        } else {
            log.info("Trying to save author : {}", author);
            author = authorRepository.save(mapToAuthor(authorDto));
        }

        Book book = mapToBook(bookDto);
        book.setAuthor(author);

        log.info("Trying to save book : {}", book);

        bookRepository.save(book);

        log.info("Book was saved to database : {}", book);

        return mapToBookDTO(book);
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
    public void deleteBookByName(String name) {
        Book book = bookRepository.findByName(name)
                .orElseThrow(() -> new BookNotFoundException(String.format("Book %s not found.", name)));

        log.info("Trying to delete book : {}", book.getName());
        bookRepository.deleteByName(name);
        log.info("Book was deleted : {}", book.getName());
    }
}
