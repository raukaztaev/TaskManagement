package kz.taskmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import kz.taskmanagement.entity.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    @NotBlank
    private String username;
    @Email
    private String email;
    @NotBlank
    private String password;
    private UserRole userRole;
    private List<Long> createdTasks;
    private List<Long> assignedTasks;
    private List<Long> comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
