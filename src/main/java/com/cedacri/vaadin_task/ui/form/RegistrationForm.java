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
public class RegistrationForm extends FormLayout {

    private final H3 title = new H3("BookStore Signup");

    private final TextField username = new TextField("Username");

    private final TextField email = new TextField("Email");

    private final PasswordField password = new PasswordField("Password");

    private final PasswordField confirmPassword = new PasswordField("Confirm password");

    private final Span errorMessage = new Span();

    private final Button registerButton = new Button("Register");

    private final Anchor loginLink = new Anchor("login", "Have an account?");

    public RegistrationForm() {
        errorMessage.setVisible(false);
        setRequiredIndicatorVisible(username, email, password, confirmPassword);
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        loginLink.getStyle()
                .set("font-size", "var(--lumo-font-size-s)")
                .set("margin-top", "10px")
                .set("text-align", "center")
                .set("color", "gray");

        add(title, username, email, password,
                confirmPassword, errorMessage, registerButton, loginLink);

        setMaxWidth("500px");

        setResponsiveSteps(
                new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP)
        );

        setColspan(title, 2);
        setColspan(username, 2);
        setColspan(email, 2);
        setColspan(errorMessage, 2);
        setColspan(registerButton, 2);
        setColspan(loginLink, 2);
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(component ->
                component.setRequiredIndicatorVisible(true));
    }
}
