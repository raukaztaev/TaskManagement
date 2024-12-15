package kz.taskmanagement.service;

import kz.taskmanagement.entity.Comment;
import kz.taskmanagement.entity.task.Task;
import kz.taskmanagement.entity.user.User;
import kz.taskmanagement.repository.CommentRepository;
import kz.taskmanagement.validator.AccessValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final AccessValidator accessValidator;
    private final TaskService taskService;

    public Comment createComment(Comment comment) {
        Task task = taskService.getTaskById(comment.getTask().getId());

        comment.setAuthor(userService.getCurrentUser());
        comment.setTask(task);

        return commentRepository.save(comment);
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment with id " + id + " not found"));
    }

    public Comment updateComment(Long id, Comment comment) {
        User currentUser = userService.getCurrentUser();

        if (!accessValidator.isAdmin(currentUser)) {
            accessValidator.checkCommentAuthor(currentUser, getCommentById(comment.getId()));
        }

        comment.setId(id);
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        User currentUser = userService.getCurrentUser();

        if (!accessValidator.isAdmin(currentUser)) {
            accessValidator.checkCommentAuthor(currentUser, getCommentById(id));
        }

        commentRepository.deleteById(id);
    }
}
