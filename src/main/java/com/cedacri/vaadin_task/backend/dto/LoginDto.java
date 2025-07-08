package com.cedacri.vaadin_task.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotBlank(message = "Empty username field.")
    private String username;

    @NotBlank(message = "Empty password field.")
    private String password;

}
