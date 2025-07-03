package com.cedacri.vaadin_task.backend.service;

import com.cedacri.vaadin_task.backend.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAllAuthors();

    AuthorDto getAuthorByLastName(String lastname);

    void saveAuthor(AuthorDto authorDto);

    void deleteAuthorById(Long authorId);
}
