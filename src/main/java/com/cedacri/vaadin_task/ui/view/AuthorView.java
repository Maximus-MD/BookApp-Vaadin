package com.cedacri.vaadin_task.ui.view;

import com.cedacri.vaadin_task.backend.dto.AuthorDto;
import com.cedacri.vaadin_task.backend.dto.BookDto;
import com.cedacri.vaadin_task.backend.service.AuthorService;
import com.cedacri.vaadin_task.ui.layout.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.stream.Collectors;

@PageTitle("Authors")
@RolesAllowed({"USER", "ADMIN"})
@Route(value = "authors", layout = MainLayout.class)
public class AuthorView extends VerticalLayout {

    public AuthorView(AuthorService authorService) {
        Grid<AuthorDto> grid = new Grid<>(AuthorDto.class, false);
        configureGrid(grid);

        grid.setItems(authorService.getAllAuthors());
        grid.setAllRowsVisible(true);

        add(new H1("Authors"), grid);
    }

    private void configureGrid(Grid<AuthorDto> grid) {
        grid.addColumn(AuthorDto::getFullname).setHeader("Author Name").setAutoWidth(true);
        grid.addColumn(author -> author.getBooks()
                        .stream()
                        .map(BookDto::getName)
                        .collect(Collectors.joining(", ")))
                .setHeader("Book Name").setAutoWidth(true);
    }
}
