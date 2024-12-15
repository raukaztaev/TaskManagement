package kz.taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import kz.taskmanagement.entity.task.Priority;
import kz.taskmanagement.entity.task.TaskStatus;
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
public class TaskDto {
    private Long id;
    @NotBlank
    private String title;
    private Long authorId;
    private Long assigneeId;
    private String description;
    private Priority priority;
    private TaskStatus taskStatus;
    private List<Long> commentsId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
