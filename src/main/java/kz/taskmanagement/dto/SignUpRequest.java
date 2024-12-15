package kz.taskmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {

    @NotBlank(message = "Username is required.")
    @Size(min = 5, max = 50, message = "Username length should be 5 to 50 symbols.")
    private String username;

    @NotBlank(message = "Email is required.")
    @Email(message = "Incorrect email. Example: user@example.com")
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;
}
