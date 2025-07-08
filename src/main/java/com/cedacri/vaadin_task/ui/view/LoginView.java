package com.cedacri.vaadin_task.ui.view;

import com.cedacri.vaadin_task.ui.binder.LoginFormBinder;
import com.cedacri.vaadin_task.ui.form.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.authentication.AuthenticationManager;

@Route("login")
@PageTitle("Login")
public class LoginView extends VerticalLayout {
    public LoginView(AuthenticationManager authenticationManager) {
        LoginForm loginForm = new LoginForm();
        setHorizontalComponentAlignment(Alignment.CENTER, loginForm);

        add(loginForm);

        LoginFormBinder loginFormBinder = new LoginFormBinder(loginForm, authenticationManager);
        loginFormBinder.addBindingAndValidation();
    }
}
