package kz.taskmanagement.mapper;

import io.jsonwebtoken.lang.Collections;
import kz.taskmanagement.dto.UserDto;
import kz.taskmanagement.entity.Comment;
import kz.taskmanagement.entity.task.Task;
import kz.taskmanagement.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "createdTasks", target = "createdTasks", qualifiedByName = "taskIdsToEntity")
    @Mapping(source = "assignedTasks", target = "assignedTasks", qualifiedByName = "taskIdsToEntity")
    @Mapping(source = "comments", target = "comments", qualifiedByName = "commentsIdsToEntity")
    User toEntity(UserDto userDto);

    @Mapping(source = "createdTasks", target = "createdTasks", qualifiedByName = "taskEntityToTaskIds")
    @Mapping(source = "assignedTasks", target = "assignedTasks", qualifiedByName = "taskEntityToTaskIds")
    @Mapping(source = "comments", target = "comments", qualifiedByName = "commentEntityToCommentIds")
    UserDto toDto(User user);


    @Named("taskIdsToEntity")
    default List<Task> taskIdsToEntity(List<Long> taskIds) {
        if (taskIds == null || taskIds.isEmpty()) {
            return Collections.emptyList();
        }
        return taskIds.stream().map(taskId -> Task.builder().id(taskId).build()).toList();
    }

    @Named("taskEntityToTaskIds")
    default List<Long> taskEntityToTaskIds(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return Collections.emptyList();
        }
        return tasks.stream().map(Task::getId).toList();
    }

    @Named("commentsIdsToEntity")
    default List<Comment> commentIdsToEntity(List<Long> commentIds) {
        if (commentIds == null || commentIds.isEmpty()) {
            return Collections.emptyList();
        }
        return commentIds.stream().map(commentId -> Comment.builder().id(commentId).build()).toList();
    }

    @Named("commentEntityToCommentIds")
    default List<Long> commentEntityToCommentIds(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return Collections.emptyList();
        }
        return comments.stream().map(Comment::getId).toList();
    }
}
