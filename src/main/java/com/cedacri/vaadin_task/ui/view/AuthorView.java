package com.cedacri.vaadin_task.ui.view;

import com.cedacri.vaadin_task.backend.dto.AuthorDto;
import com.cedacri.vaadin_task.backend.service.AuthorService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("/authors")
@PageTitle("Authors")
public class AuthorView extends VerticalLayout {

    public AuthorView(AuthorService authorService) {

        Grid<AuthorDto> grid = new Grid<>(AuthorDto.class, false);
        configureGrid(grid);

        grid.setItems(authorService.getAllAuthors());
        grid.setAllRowsVisible(true);

        add(new H1("Authors"), grid);
    }

    private void configureGrid(Grid<AuthorDto> grid) {
        grid.addColumn(AuthorDto::getFirstname).setHeader("First Name").setAutoWidth(true);
        grid.addColumn(AuthorDto::getLastname).setHeader("Last Name").setAutoWidth(true);
        grid.addColumn(author -> author.getBooks() != null ? author.getBooks().size() : 0)
                .setHeader("Books").setAutoWidth(true);
    }
}
