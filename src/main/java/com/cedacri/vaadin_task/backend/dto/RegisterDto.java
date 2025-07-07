package com.cedacri.vaadin_task.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotBlank(message = "Username can't be empty.")
    @Size(min = 4, max = 20, message = "Username should have min 4 and max 20 characters.")
    private String username;

    @NotBlank(message = "Email can't be empty.")
    @Pattern(
            regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Please enter a valid email address (e.g., user@example.com).")
    private String email;

    @NotBlank(message = "Password can't be empty.")
    @Size(min = 8, max = 30, message = "Password should have min 7 and max 30 characters.")
    @Pattern(
            regexp = "^(?=.+\\d)(?=.+[a-z])(?=.+[A-Z])(?=.+[@#$%^&*+=])(?=\\S+$).*$",
            message = "Password must contain at least one lowercase letter, " +
                    "one uppercase letter, one digit, one special character (@#$%^&*+=), and no whitespace.")
    private String password;
}
