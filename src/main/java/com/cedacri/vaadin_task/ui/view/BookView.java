package com.cedacri.vaadin_task.ui.view;

import com.cedacri.vaadin_task.backend.dto.BookDto;
import com.cedacri.vaadin_task.backend.entity.enums.BookAvailability;
import com.cedacri.vaadin_task.backend.security.SecurityService;
import com.cedacri.vaadin_task.backend.service.BookService;
import com.cedacri.vaadin_task.backend.service.CartItemService;
import com.cedacri.vaadin_task.ui.form.BookForm;
import com.cedacri.vaadin_task.ui.layout.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@PageTitle("Books")
@RolesAllowed({"USER", "ADMIN"})
@Route(value = "books", layout = MainLayout.class)
public class BookView extends VerticalLayout {

    private final BookService bookService;

    private final Grid<BookDto> grid = new Grid<>(BookDto.class, false);

    private final TextField searchField = new TextField("Search book by Name");

    private final Dialog formDialog = new Dialog();

    private final BookForm bookForm = new BookForm(this::saveBook);

    private BookDto selectedBook;

    public BookView(CartItemService cartItemService, BookService bookService, SecurityService securityService) {
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
                Notification.show("No book selected!", 3000, Notification.Position.BOTTOM_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        HorizontalLayout actions = new HorizontalLayout(searchField, searchButton);
        actions.setAlignItems(Alignment.END);

        // Userii au layout diferit in dependenta de rolul lor(USER, ADMIN)
        if (isAdmin(securityService)) {
            actions.add(addButton, updateButton, deleteButton);
            add(actions, grid);
        } else {
            Button addToCart = new Button("Add to cart", e -> {
                if (selectedBook != null && selectedBook.getAvailability().equals(BookAvailability.AVAILABLE)) {
                    cartItemService.addToCart(
                            securityService.getAuthenticatedUser().getUsername(),
                            selectedBook.getId()
                    );
                    Notification.show("Book " + selectedBook.getName() + " was added to cart!", 3000, Notification.Position.BOTTOM_CENTER)
                            .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                } else if (selectedBook != null && selectedBook.getAvailability().equals(BookAvailability.UNAVAILABLE)) {
                    Notification.show("Book is unavailable.", 3000, Notification.Position.BOTTOM_CENTER)
                            .addThemeVariants(NotificationVariant.LUMO_WARNING);
                } else {
                    Notification.show("Please, select available books.", 3000, Notification.Position.BOTTOM_CENTER)
                            .addThemeVariants(NotificationVariant.LUMO_WARNING);
                }
            });

            add(actions, grid, addToCart);
        }

        refreshGrid();
    }

    private Component createStatusIcon(BookAvailability availability) {
        Icon icon;

        if (availability == BookAvailability.AVAILABLE) {
            icon = VaadinIcon.CHECK.create();
            icon.getElement().getThemeList().add("badge success");
            icon.setColor("green");
        } else {
            icon = VaadinIcon.CLOSE_SMALL.create();
            icon.getElement().getThemeList().add("badge error");
            icon.setColor("red");
        }

        return icon;
    }

    private void configureGrid() {
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.asSingleSelect().addValueChangeListener(
                e -> selectedBook = e.getValue());

        grid.addColumn(BookDto::getName).setHeader("Book Name").setAutoWidth(true);
        grid.addColumn(BookDto::getDescription).setHeader("Description").setAutoWidth(true);
        grid.addColumn(BookDto::getPages).setHeader("Pages").setAutoWidth(true);
        grid.addColumn(BookDto::getPublished).setHeader("Published").setAutoWidth(true);
        grid.addColumn(BookDto::getPrice).setHeader("Price").setAutoWidth(true);
        grid.addColumn(BookDto::getCategory).setHeader("Category").setAutoWidth(true);
        grid.addComponentColumn(bookDto -> createStatusIcon(bookDto.getAvailability()))
                .setHeader("Availability").setAutoWidth(true);
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
            Notification.show("No book selected!", 3000, Notification.Position.BOTTOM_CENTER);
            return;
        }

        bookService.deleteBookByName(selectedBook.getName());
        Notification.show("Book deleted!", 3000, Notification.Position.BOTTOM_CENTER)
                .addThemeVariants(NotificationVariant.LUMO_CONTRAST);
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

    private boolean isAdmin(SecurityService securityService) {
        return securityService.getAuthenticatedUser()
                .getAuthorities()
                .stream()
                .anyMatch(user ->
                        user.getAuthority().equals("ROLE_ADMIN"));

    }
}
