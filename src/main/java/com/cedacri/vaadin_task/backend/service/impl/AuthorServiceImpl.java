package com.cedacri.vaadin_task.backend.service.impl;

import com.cedacri.vaadin_task.backend.dto.AuthorDto;
import com.cedacri.vaadin_task.backend.entity.Author;
import com.cedacri.vaadin_task.backend.exception.BookNotFoundException;
import com.cedacri.vaadin_task.backend.mapper.AuthorMapper;
import com.cedacri.vaadin_task.backend.repository.AuthorRepository;
import com.cedacri.vaadin_task.backend.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cedacri.vaadin_task.backend.mapper.AuthorMapper.mapToAuthor;
import static com.cedacri.vaadin_task.backend.mapper.AuthorMapper.mapToAuthorDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public void saveAuthor(AuthorDto authorDto) {
        Author author = mapToAuthor(authorDto);
        log.info("Saving author : {}", author);
        authorRepository.save(author);
    }

    @Override
    @Transactional
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(AuthorMapper::mapToAuthorDTO)
                .toList();
    }

    @Override
    @Transactional
    public AuthorDto getAuthorByLastName(String lastname) {
        log.info("Trying to get author lastname: {}", lastname);

        Author author = authorRepository.findByLastname(lastname)
                .orElseThrow(() -> new BookNotFoundException(String.format("Author %s not found.", lastname)));

        StringBuilder fullname = new StringBuilder();
        fullname.append(author.getFirstname()).append(" ").append(author.getLastname());

        log.info("Author was found : {}", fullname);

        return mapToAuthorDTO(author);
    }

    @Override
    @Transactional
    public void deleteAuthorById(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new BookNotFoundException(String.format("Author id:%d not found.", authorId)));

        log.info("Trying to delete book : {}", author.getLastname());
        authorRepository.deleteById(authorId);
        log.info("Book was deleted : {}", author.getLastname());
    }
}
