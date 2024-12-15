package kz.taskmanagement.controller;

import jakarta.validation.Valid;
import kz.taskmanagement.dto.TaskDto;
import kz.taskmanagement.entity.task.Task;
import kz.taskmanagement.mapper.TaskMapper;
import kz.taskmanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody @Valid TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        TaskDto createdTaskDto = taskMapper.toDto(taskService.createTask(task));
        return ResponseEntity.ok(createdTaskDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        TaskDto taskDto = taskMapper.toDto(taskService.getTaskById(id));
        return ResponseEntity.ok(taskDto);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<Page<TaskDto>> getTasksByAuthorId(@PathVariable Long id,
                                                            @RequestParam(required = false, defaultValue = "0") int page,
                                                            @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TaskDto> tasks = taskService.getTasksByAuthorId(id, pageable).map(taskMapper::toDto);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/assignee/{id}")
    public ResponseEntity<Page<TaskDto>> getTasksByAssigneeId(@PathVariable Long id,
                                                              @RequestParam(required = false, defaultValue = "0") int page,
                                                              @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TaskDto> tasks = taskService.getTasksByAssigneeId(id, pageable).map(taskMapper::toDto);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        TaskDto updatedTaskDto = taskMapper.toDto(taskService.updateTask(id, task));
        return ResponseEntity.ok(updatedTaskDto);
    }

    @PutMapping("/assignee/{id}")
    public ResponseEntity<Void> deleteAssigneeFromTaskByTaskId(@PathVariable Long id) {
        taskService.deleteAssigneeFromTaskByTaskId(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
