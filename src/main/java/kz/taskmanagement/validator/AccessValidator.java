package kz.taskmanagement.validator;

import kz.taskmanagement.entity.Comment;
import kz.taskmanagement.entity.task.Task;
import kz.taskmanagement.entity.user.User;
import kz.taskmanagement.entity.user.UserRole;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
public class AccessValidator {

    public boolean isAdmin(User user) {
        return user.getUserRole() == UserRole.ADMIN;
    }

    public void checkTaskAuthor(User user, Task task) {
        if (!user.getId().equals(task.getAuthor().getId())) {
            throw new AccessDeniedException("Not allowed to update this task");
        }
    }

    public void checkTaskAssignee(User user, Task task) {
        if (task.getAssignee() != null && !user.getId().equals(task.getAssignee().getId())) {
            throw new AccessDeniedException("Not allowed to update this task");
        }
    }

    public void checkCommentAuthor(User user, Comment comment) {
        if (!user.getId().equals(comment.getAuthor().getId())) {
            throw new AccessDeniedException("Not allowed to update this comment");
        }
    }
}
