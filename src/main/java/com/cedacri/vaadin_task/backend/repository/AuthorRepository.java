package com.cedacri.vaadin_task.backend.repository;

import com.cedacri.vaadin_task.backend.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByLastname(String lastname);

}
