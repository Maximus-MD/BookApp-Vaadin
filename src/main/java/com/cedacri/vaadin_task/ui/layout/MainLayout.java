package com.cedacri.vaadin_task.ui.layout;

import com.cedacri.vaadin_task.ui.view.AuthorView;
import com.cedacri.vaadin_task.ui.view.BookView;
import com.cedacri.vaadin_task.ui.view.OrderView;
import com.cedacri.vaadin_task.ui.view.StatisticView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {

    public MainLayout() {
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("\uD83D\uDCDA Online Book Store");

        title.getStyle().set("font-size", "var(--lumo-font-size-1)").set("margin", "0");

        addToNavbar(toggle, title);

        SideNav nav = new SideNav();
        nav.addItem(new SideNavItem("Orders", OrderView.class, new Icon(VaadinIcon.CLIPBOARD)));
        nav.addItem(new SideNavItem("Authors", AuthorView.class, new Icon(VaadinIcon.USER)));
        nav.addItem(new SideNavItem("Products", BookView.class, new Icon(VaadinIcon.BOOK)));
        nav.addItem(new SideNavItem("Statistics", StatisticView.class, new Icon(VaadinIcon.CHART)));

        Scroller scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.SMALL);

        addToDrawer(scroller);
    }
}
