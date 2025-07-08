package com.cedacri.vaadin_task.ui.form;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public class LoginForm extends FormLayout {

    private final H3 title = new H3("BookStore Login");

    private final TextField username = new TextField("Username");

    private final PasswordField password = new PasswordField("Password");

    private final Span errorMessage = new Span();

    private final Button loginButton = new Button("Login");

    private final Anchor registerLink = new Anchor("register", "Create account");

    public LoginForm() {
        errorMessage.setVisible(false);

        setRequiredIndicatorVisible(username,  password);

        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(title, username, password, errorMessage, loginButton, registerLink);

        setMaxWidth("500px");

        setResponsiveSteps(
                new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP)
        );

        setColspan(title, 2);
        setColspan(errorMessage, 2);
        setColspan(loginButton, 2);
        setColspan(registerLink, 2);
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(component ->
                component.setRequiredIndicatorVisible(true));
    }
}
