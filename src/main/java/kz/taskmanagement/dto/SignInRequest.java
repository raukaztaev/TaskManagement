package kz.taskmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInRequest {
    @NotBlank(message = "Email is required.")
    @Email(message = "Incorrect email. Example: user@example.com")
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;
}
