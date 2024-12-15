package kz.taskmanagement.service;

import kz.taskmanagement.entity.task.Priority;
import kz.taskmanagement.entity.task.Task;
import kz.taskmanagement.entity.task.TaskStatus;
import kz.taskmanagement.entity.user.User;
import kz.taskmanagement.repository.TaskRepository;
import kz.taskmanagement.validator.AccessValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final AccessValidator accessValidator;

    public Task createTask(Task task) {
        if (task.getPriority() == null) {
            task.setPriority(Priority.MEDIUM);
        }
        task.setTaskStatus(TaskStatus.PENDING);
        task.setAuthor(userService.getCurrentUser());

        if (task.getAssignee().getId() != null) {
            task.setAssignee(userService.getUserById(task.getAssignee().getId()));
        } else {
            task.setAssignee(null);
        }

        return taskRepository.save(task);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + id + " not found"));
    }

    public Page<Task> getTasksByAuthorId(Long id, Pageable pageable) {
        return taskRepository.findTasksByAuthorId(id, pageable);
    }

    public Page<Task> getTasksByAssigneeId(Long id, Pageable pageable) {
        return taskRepository.findTasksByAssigneeId(id, pageable);
    }

    public Task updateTask(Long id, Task task) {
        User currentUser = userService.getCurrentUser();
        if (!accessValidator.isAdmin(currentUser)) {
            accessValidator.checkTaskAuthor(currentUser, task);
            accessValidator.checkTaskAssignee(currentUser, task);
        }

        Task taskToUpdate = getTaskById(id);
        task.setAssignee(task.getAssignee().getId() == null ? taskToUpdate.getAssignee() :
                userService.getUserById(task.getAssignee().getId()));
        task.setTitle(task.getTitle().isBlank() ? taskToUpdate.getTitle() : task.getTitle());
        task.setDescription(task.getDescription().isBlank() ? taskToUpdate.getDescription(): task.getDescription());
        task.setPriority(task.getPriority() == null ? taskToUpdate.getPriority(): task.getPriority());
        task.setTaskStatus(task.getTaskStatus() == null ? taskToUpdate.getTaskStatus(): task.getTaskStatus());

        return taskRepository.save(task);
    }

    public void deleteAssigneeFromTaskByTaskId(Long id) {
        Task task = getTaskById(id);
        task.setAssignee(null);
        taskRepository.save(task);
    }


    public void deleteTask(Long id) {
        User currentUser = userService.getCurrentUser();
        Task task = getTaskById(id);

        if (!accessValidator.isAdmin(currentUser)) {
            accessValidator.checkTaskAuthor(currentUser, task);
            accessValidator.checkTaskAssignee(currentUser, task);
        }

        taskRepository.deleteById(id);
    }
}
