package com.cedacri.vaadin_task.backend.dto;

import com.cedacri.vaadin_task.backend.entity.enums.BookAvailability;
import com.cedacri.vaadin_task.backend.entity.enums.BookCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;

    private String name;

    private String description;

    private Integer pages;

    private LocalDate published;

    private Double price;

    private BookCategory category;

    private BookAvailability availability;

    private AuthorDto author;
}
