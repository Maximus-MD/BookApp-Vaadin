package com.cedacri.vaadin_task.ui.view;

import com.cedacri.vaadin_task.backend.dto.BookDto;
import com.cedacri.vaadin_task.backend.service.BookService;
import com.cedacri.vaadin_task.ui.form.BookForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("/books")
@PageTitle("Books")
public class BookView extends VerticalLayout {

    private final BookService bookService;
    private final Grid<BookDto> grid = new Grid<>(BookDto.class, false);
    private final TextField searchField = new TextField("Search book by Name");

    public BookView(BookService bookService) {
        this.bookService = bookService;

        BookForm bookForm = new BookForm(this::saveBook);

        Button searchButton = new Button("Search", e -> searchBooks());

        configureGrid(grid);

        add(new HorizontalLayout(searchField, searchButton), bookForm, grid);

        grid.setItems(bookService.getAllBooks());
        grid.setAllRowsVisible(true);
    }

    private void configureGrid(Grid<BookDto> grid) {
        grid.addColumn(BookDto::getName).setHeader("Name").setAutoWidth(true);
        grid.addColumn(BookDto::getDescription).setHeader("Description").setAutoWidth(true);
        grid.addColumn(BookDto::getPages).setHeader("Pages").setAutoWidth(true);
        grid.addColumn(BookDto::getPublished).setHeader("Published").setAutoWidth(true);
        grid.addColumn(BookDto::getPrice).setHeader("Price").setAutoWidth(true);
        grid.addColumn(BookDto::getCategory).setHeader("Category").setAutoWidth(true);
        grid.addColumn(BookDto::getAvailability).setHeader("Availability").setAutoWidth(true);
        grid.addColumn(book -> book.getAuthor() != null ? book.getAuthor().getFullname() : "N/A")
                .setHeader("Author").setAutoWidth(true);
    }

    private void searchBooks() {
        String name = searchField.getValue();
        List<BookDto> books = bookService.getAllBooks().stream()
                .filter(book -> book.getName() != null && book.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();

        grid.setItems(books);
    }

    private void saveBook(BookDto bookDto) {
        bookService.saveBook(bookDto);
        grid.setItems(bookService.getAllBooks());
        Notification.show("Book saved!");
    }
}
