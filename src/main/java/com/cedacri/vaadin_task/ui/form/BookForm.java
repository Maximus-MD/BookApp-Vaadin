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

public class BookForm extends FormLayout {

    private BookDto bookDto = new BookDto();

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
            if (this.bookDto == null) {
                this.bookDto = new BookDto();
            }

            this.bookDto.setName(name.getValue());
            this.bookDto.setDescription(description.getValue());
            this.bookDto.setPages(pages.getValue());
            this.bookDto.setPublished(published.getValue());
            this.bookDto.setPrice(price.getValue());
            this.bookDto.setCategory(category.getValue());
            this.bookDto.setAvailability(availability.getValue());

            AuthorDto authorDto = this.bookDto.getAuthor();
            if (authorDto == null) {
                authorDto = new AuthorDto();
            }

            authorDto.setFirstname(authorFirstName.getValue());
            authorDto.setLastname(authorLastName.getValue());

            this.bookDto.setAuthor(authorDto);

            listener.onSave(this.bookDto);
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
        this.bookDto = bookDto;

        name.setValue(bookDto.getName() != null ? bookDto.getName() : "");
        description.setValue(bookDto.getDescription() != null ? bookDto.getDescription() : "");
        pages.setValue(bookDto.getPages() != null ? bookDto.getPages() : 0);
        published.setValue(bookDto.getPublished());
        price.setValue(bookDto.getPrice() != null ? bookDto.getPrice() : 0.0);
        category.setValue(bookDto.getCategory());
        availability.setValue(bookDto.getAvailability());

        if (bookDto.getAuthor() != null) {
            authorFirstName.setValue(bookDto.getAuthor().getFirstname() != null ? bookDto.getAuthor().getFirstname() : "");
            authorLastName.setValue(bookDto.getAuthor().getLastname() != null ? bookDto.getAuthor().getLastname() : "");
        } else {
            authorFirstName.setValue("");
            authorLastName.setValue("");
        }
    }

}
