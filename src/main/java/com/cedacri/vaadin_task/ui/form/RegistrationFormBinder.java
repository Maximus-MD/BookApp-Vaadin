package com.cedacri.vaadin_task.ui.form;

import com.cedacri.vaadin_task.backend.dto.RegisterDto;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RegistrationFormBinder {

    private final RegistrationForm registrationForm;

    private boolean enablePasswordValidation;

    public void addBindingAndValidation() {
        BeanValidationBinder<RegisterDto> binder = new BeanValidationBinder<>(RegisterDto.class);
        binder.bindInstanceFields(registrationForm);

        binder.forField(registrationForm.getPassword())
                .withValidator(this::passwordValidator).bind("password");

        registrationForm.getConfirmPassword().addValueChangeListener(e -> {
            enablePasswordValidation = true;

            binder.validate();
        });

        binder.setStatusLabel(registrationForm.getErrorMessageField());

        registrationForm.getSubmitButton().addClickListener(event -> {
           try {
               RegisterDto registerDto = new RegisterDto();

               binder.writeBean(registerDto);

               showSuccess(registerDto);
           } catch (ValidationException exception) {
               log.error("ValidationException occurred : {}", exception.getMessage());

               Notification.show("Validation failed. Please correct the errors.", 3000, Notification.Position.MIDDLE)
                       .addThemeVariants(NotificationVariant.LUMO_ERROR);
           }
        });
    }

    private ValidationResult passwordValidator(String actualPassword, ValueContext ctx) {
        if (actualPassword == null || actualPassword.length() < 8) {
            return ValidationResult.error("Password should be at least 8 characters long");
        }

        if(!enablePasswordValidation) {
            enablePasswordValidation = true;
            return ValidationResult.ok();
        }

        String expectedPassword = registrationForm.getConfirmPassword().getValue();

        if(actualPassword.equals(expectedPassword)) {
            return ValidationResult.ok();
        }

        return ValidationResult.error("Password do not match.");
    }

    private void showSuccess(RegisterDto registerDto) {
        Notification notification =
                Notification.show("Data saved, welcome " + registerDto.getUsername());

        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}
