package com.cedacri.vaadin_task.ui.binder;

import com.cedacri.vaadin_task.backend.dto.LoginDto;
import com.cedacri.vaadin_task.ui.form.LoginForm;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@RequiredArgsConstructor
public class LoginFormBinder {

    private final LoginForm loginForm;

    private final AuthenticationManager authenticationManager;

    public void addBindingAndValidation() {
        BeanValidationBinder<LoginDto> binder = new BeanValidationBinder<>(LoginDto.class);
        binder.bindInstanceFields(loginForm);

        binder.setStatusLabel(loginForm.getErrorMessage());

        loginForm.getLoginButton().addClickListener(event -> {
            try {
                LoginDto loginDto = new LoginDto();

                binder.writeBean(loginDto);

                Authentication auth = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getUsername(), loginDto.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(auth);
                UI.getCurrent().navigate("books");
            } catch (ValidationException exception) {
                log.error("ValidationException occurred : {}", exception.getMessage());

                loginForm.getErrorMessage().setText("Validation failed.");
                loginForm.getErrorMessage().setVisible(true);
            } catch (AuthenticationException exception) {
                log.error("UserAlreadyExistsException occurred : {}", exception.getMessage());

                loginForm.getErrorMessage().setText("Invalid username or password.");
                loginForm.getErrorMessage().setVisible(true);
            }
        });
    }
}
