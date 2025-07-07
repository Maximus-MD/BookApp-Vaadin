package com.cedacri.vaadin_task.ui.view;

import com.cedacri.vaadin_task.ui.form.RegistrationForm;
import com.cedacri.vaadin_task.ui.form.RegistrationFormBinder;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("register")
@PageTitle("Registration")
public class RegistrationView extends VerticalLayout {
    public RegistrationView() {
        RegistrationForm registrationForm = new RegistrationForm();
        setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);

        add(registrationForm);

        RegistrationFormBinder registrationFormBinder = new RegistrationFormBinder(registrationForm);
        registrationFormBinder.addBindingAndValidation();
    }
}
