package com.cedacri.vaadin_task.ui.view;

import com.cedacri.vaadin_task.backend.dto.CartItemDto;
import com.cedacri.vaadin_task.backend.security.SecurityService;
import com.cedacri.vaadin_task.backend.service.CartItemService;
import com.cedacri.vaadin_task.ui.layout.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Orders")
@RolesAllowed({"USER", "ADMIN"})
@Route(value = "orders", layout = MainLayout.class)
public class OrderView extends VerticalLayout {

    private final CartItemService cartItemService;

    private final SecurityService securityService;

    private final Span totalPriceSpan = new Span();

    private final Grid<CartItemDto> grid = new Grid<>(CartItemDto.class, false);

    private CartItemDto selectedItem;

    public OrderView(CartItemService cartItemService, SecurityService securityService) {
        this.cartItemService = cartItemService;
        this.securityService = securityService;

        configureGrid();
        add(grid, createRemoveButton());

        refreshGrid();
    }

    private Component createRemoveButton() {
        Button removeButton = new Button("Remove from Cart", e -> removeOrder());

        removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        return removeButton;
    }

    private void configureGrid() {
        grid.addColumn(CartItemDto::getBookName).setHeader("Book Name").setAutoWidth(true);
        grid.addColumn(CartItemDto::getQuantity).setHeader("Quantity").setAutoWidth(true);
        grid.addColumn(CartItemDto::getPrice).setHeader("Price").setAutoWidth(true);
        grid.addColumn(CartItemDto::getFinalPrice).setHeader("Final price").setAutoWidth(true);

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.asSingleSelect().addValueChangeListener(e -> selectedItem = e.getValue());

        totalPriceSpan.getStyle().set("font-weight", "bold");
        add(grid, totalPriceSpan);
    }

    private void updateTotalPrice() {
        double total = grid.getListDataView()
                .getItems()
                .mapToDouble(CartItemDto::getFinalPrice)
                .sum();
        totalPriceSpan.setText("Total: " + total + " lei");
    }

    private void removeOrder() {
        if(selectedItem != null) {
            cartItemService.deleteFromCartById(selectedItem.getId());
            Notification.show(selectedItem.getBookName() + " removed from cart.", 3000, Notification.Position.BOTTOM_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_CONTRAST);
            refreshGrid();
        } else {
            Notification.show("Select an item to remove.", 3000, Notification.Position.BOTTOM_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_CONTRAST);
        }
    }

    private void refreshGrid() {
        String username = securityService.getAuthenticatedUser().getUsername();
        grid.setItems(cartItemService.getCartItems(username));
        updateTotalPrice();
    }
}
