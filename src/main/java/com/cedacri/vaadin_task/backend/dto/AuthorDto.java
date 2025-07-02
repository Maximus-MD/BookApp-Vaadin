package com.cedacri.vaadin_task.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    private Long id;

    private String firstname;

    private String lastname;

    private List<BookDto> books;

    public String getFullname() {
        return firstname + " " + lastname;
    }
}
