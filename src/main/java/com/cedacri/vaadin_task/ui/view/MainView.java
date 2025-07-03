package com.cedacri.vaadin_task.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("/home")
@PageTitle("Home")
public class MainView extends VerticalLayout {

    public MainView() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSpacing(true);
        setPadding(true);
        setSizeFull();
        getStyle().set("background-color", "#f5f5f5");

        H1 title = new H1("Book Management System");
        title.getStyle().set("color", "#2e3d49");

        Span subTitle = new Span("Select an option");
        subTitle.getStyle().set("font-size", "1.2em").set("color", "#555");

        Button booksButton = new Button("📘 Books");
        booksButton.addClickListener(event -> booksButton.getUI().ifPresent(
                ui -> ui.navigate(BookView.class)));

        Button authorsButton = new Button("👤 Authors");
        authorsButton.addClickListener(
                event -> authorsButton.getUI().ifPresent(
                        ui -> ui.navigate(AuthorView.class))
        );

        booksButton.setWidth("200px");
        authorsButton.setWidth("200px");

        booksButton.getStyle().set("font-size", "1.1em");
        authorsButton.getStyle().set("font-size", "1.1em");

        HorizontalLayout actions = new HorizontalLayout(booksButton, authorsButton);
        actions.setSpacing(true);
        actions.setJustifyContentMode(JustifyContentMode.CENTER);

        add(title, subTitle, actions);
    }
}
