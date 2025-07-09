package com.cedacri.vaadin_task.ui.view;

import com.cedacri.vaadin_task.ui.layout.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Statistics")
@RolesAllowed({"USER", "ADMIN"})
@Route(value = "statistics", layout = MainLayout.class)
public class StatisticView extends VerticalLayout {
}
