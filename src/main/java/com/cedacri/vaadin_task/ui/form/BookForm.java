package com.cedacri.vaadin_task.ui.form;

import com.cedacri.vaadin_task.backend.dto.AuthorDto;
import com.cedacri.vaadin_task.backend.dto.BookDto;
import com.cedacri.vaadin_task.backend.entity.enums.BookAvailability;
import com.cedacri.vaadin_task.backend.entity.enums.BookCategory;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class BookForm extends FormLayout {

    private final TextField name = new TextField("Name");
    private final TextField description = new TextField("Description");
    private final IntegerField pages = new IntegerField("Pages");
    private final DatePicker published = new DatePicker("Published");
    private final NumberField price = new NumberField("Price");
    private final ComboBox<BookCategory> category = new ComboBox<>("Category");
    private final ComboBox<BookAvailability> availability = new ComboBox<>("Availability");
    private final TextField authorFirstName = new TextField("Author First Name");
    private final TextField authorLastName = new TextField("Author Last Name");

    private final Button save = new Button("Save");

    public interface BookSaveListener {
        void onSave(BookDto bookDto);
    }

    public BookForm(BookSaveListener listener) {
        category.setItems(BookCategory.values());
        availability.setItems(BookAvailability.values());

        save.addClickListener(e -> {
            BookDto bookDto = new BookDto();
            bookDto.setName(name.getValue());
            bookDto.setDescription(description.getValue());
            bookDto.setPages(pages.getValue());
            bookDto.setPublished(published.getValue());
            bookDto.setPrice(price.getValue());
            bookDto.setCategory(category.getValue());
            bookDto.setAvailability(availability.getValue());

            AuthorDto authorDto = new AuthorDto();
            authorDto.setFirstname(authorFirstName.getValue());
            authorDto.setLastname(authorLastName.getValue());
            bookDto.setAuthor(authorDto);

            listener.onSave(bookDto);
        });

        add(name, description, pages, published, price, category, availability,
                authorFirstName, authorLastName, save);
    }

    public void clear() {
        name.clear();
        description.clear();
        pages.clear();
        published.clear();
        price.clear();
        category.clear();
        availability.clear();
        authorFirstName.clear();
        authorLastName.clear();
    }

    public void setBookDto(BookDto bookDto) {
        if (bookDto == null) {
            clear();
        } else {
            name.setValue(bookDto.getName());
            description.setValue(bookDto.getDescription());
            pages.setValue(bookDto.getPages());
            published.setValue(bookDto.getPublished());
            price.setValue(bookDto.getPrice());
            category.setValue(bookDto.getCategory());
            availability.setValue(bookDto.getAvailability());
            authorFirstName.setValue(bookDto.getAuthor().getFirstname());
            authorLastName.setValue(bookDto.getAuthor().getLastname());
        }
    }
}
