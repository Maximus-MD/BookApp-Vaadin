package com.cedacri.vaadin_task.ui.config;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.AppShellSettings;
import com.vaadin.flow.server.PWA;
import org.springframework.stereotype.Component;

@Component
@PWA(name = "BookStore", shortName = "BookStore", iconPath = "icons/bookstore_logo.ico")
public class AppShellConfig implements AppShellConfigurator {
    @Override
    public void configurePage(AppShellSettings settings) {
        settings.addFavIcon("icon", "src/main/frontend/icons/bookstore_logo.ico", "360x360");
    }
}
