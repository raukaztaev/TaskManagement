package kz.taskmanagement.mapper;

import kz.taskmanagement.dto.TaskDto;
import kz.taskmanagement.entity.Comment;
import kz.taskmanagement.entity.task.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    @Mapping(target = "author.id", source = "authorId")
    @Mapping(target = "assignee.id", source = "assigneeId")
    @Mapping(target = "comments", source = "commentsId", qualifiedByName = "commentDtoToEntity")
    Task toEntity(TaskDto taskDto);

    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "assigneeId", source = "assignee.id")
    @Mapping(target = "commentsId", source = "comments", qualifiedByName = "commentEntityToDto")
    TaskDto toDto(Task task);

    @Named("commentDtoToEntity")
    default List<Comment> mapCommentsToEntity(List<Long> comments) {
        if (comments == null || comments.isEmpty()) {
            return Collections.emptyList();
        }
        return comments.stream().map(comment -> Comment.builder().id(comment).build()).toList();
    }

    @Named("commentEntityToDto")
    default List<Long> mapCommentsToDto(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return Collections.emptyList();
        }
        return comments.stream().map(Comment::getId).toList();
    }
}
