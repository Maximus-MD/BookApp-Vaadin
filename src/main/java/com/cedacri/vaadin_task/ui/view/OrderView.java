package com.cedacri.vaadin_task.ui.view;

import com.cedacri.vaadin_task.ui.layout.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Orders")
@RolesAllowed({"USER", "ADMIN"})
@Route(value = "orders", layout = MainLayout.class)
public class OrderView extends VerticalLayout {

}
