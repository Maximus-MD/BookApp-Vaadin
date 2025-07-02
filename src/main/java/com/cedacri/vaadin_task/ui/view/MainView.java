package com.cedacri.vaadin_task.ui.view;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("/")
public class MainView extends VerticalLayout {

    public MainView() {
        setSpacing(true);
        setPadding(true);

        add(new HorizontalLayout(
                new RouterLink("Books", BookView.class),
                new RouterLink("Authors", AuthorView.class)
        ));
    }
}
