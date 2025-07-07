package com.cedacri.vaadin_task.ui.view;

import com.cedacri.vaadin_task.backend.dto.BookDto;
import com.cedacri.vaadin_task.backend.service.BookService;
import com.cedacri.vaadin_task.ui.form.BookForm;
import com.cedacri.vaadin_task.ui.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "books", layout = MainLayout.class)
@PageTitle("Books")
public class BookView extends VerticalLayout {

    private final BookService bookService;
    private final Grid<BookDto> grid = new Grid<>(BookDto.class, false);
    private final TextField searchField = new TextField("Search book by Name");
    private final Dialog formDialog = new Dialog();
    private final BookForm bookForm = new BookForm(this::saveBook);
    private BookDto selectedBook;

    public BookView(BookService bookService) {
        this.bookService = bookService;

        configureGrid();
        configureDialog();

        Button searchButton = new Button("Search", e -> searchBooks());
        Button addButton = new Button("Add", e -> openForm(new BookDto()));
        Button updateButton = new Button("Update", e -> {
            if (selectedBook != null) {
                openForm(selectedBook);
            }
        });

        // Confirmation for delete book
        Dialog confirmDialog = new Dialog();
        confirmDialog.setHeaderTitle("Confirm delete");

        Span confirmText = new Span("Are you sure you want to delete this book?");
        Button confirm = new Button("Yes", e -> {
            deleteBook();
            confirmDialog.close();
        });

        Button cancel = new Button("Cancel", e -> confirmDialog.close());

        confirm.getElement().getThemeList().add("primary error");
        cancel.getElement().getThemeList().add("tertiary");

        HorizontalLayout dialogButtons = new HorizontalLayout(confirm, cancel);
        VerticalLayout dialogContent = new VerticalLayout(confirmText, dialogButtons);
        confirmDialog.add(dialogContent);

        Button deleteButton = new Button("Delete", e -> {
            if (selectedBook != null) {
                confirmDialog.open();
            } else {
                Notification.show("No book selected!");
            }
        });

        HorizontalLayout actions = new HorizontalLayout(searchField, searchButton, addButton, updateButton, deleteButton);
        actions.setAlignItems(Alignment.END);

        add(actions, grid);
        refreshGrid();
    }

    private void configureGrid() {
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.asSingleSelect().addValueChangeListener(
                e -> selectedBook = e.getValue()
        );

        grid.addColumn(BookDto::getName).setHeader("Book Name").setAutoWidth(true);
        grid.addColumn(BookDto::getDescription).setHeader("Description").setAutoWidth(true);
        grid.addColumn(BookDto::getPages).setHeader("Pages").setAutoWidth(true);
        grid.addColumn(BookDto::getPublished).setHeader("Published").setAutoWidth(true);
        grid.addColumn(BookDto::getPrice).setHeader("Price").setAutoWidth(true);
        grid.addColumn(BookDto::getCategory).setHeader("Category").setAutoWidth(true);
        grid.addColumn(BookDto::getAvailability).setHeader("Availability").setAutoWidth(true);
        grid.addColumn(book -> book.getAuthor() != null ? book.getAuthor().getFullname() : "N/A")
                .setHeader("Author").setAutoWidth(true);
    }

    private void configureDialog() {
        formDialog.setWidth("500px");
        formDialog.add(bookForm);
        formDialog.setModal(true);
        formDialog.setCloseOnOutsideClick(true);
        add(formDialog);
    }

    private void openForm(BookDto bookDto) {
        bookForm.setBookDto(bookDto);
        formDialog.open();
    }

    private void saveBook(BookDto bookDto) {
        bookService.saveBook(bookDto);
        formDialog.close();
        refreshGrid();
        Notification.show("Book saved!");
    }

    private void deleteBook() {
        if (selectedBook == null) {
            Notification.show("No book selected!", 3000, Notification.Position.MIDDLE);
            return;
        }

        bookService.deleteBookByName(selectedBook.getName());
        Notification.show("Book deleted!");
        refreshGrid();
        selectedBook = null;
    }

    private void searchBooks() {
        String name = searchField.getValue();
        List<BookDto> books = bookService.getAllBooks().stream()
                .filter(book -> book.getName() != null && book.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();

        grid.setItems(books);
    }

    private void refreshGrid() {
        grid.setItems(bookService.getAllBooks());
    }
}
