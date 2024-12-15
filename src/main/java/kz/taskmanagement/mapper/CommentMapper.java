package kz.taskmanagement.mapper;

import kz.taskmanagement.dto.CommentDto;
import kz.taskmanagement.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mapping(target = "author.id", source = "authorId")
    @Mapping(target = "task.id", source = "taskId")
    Comment toEntity(CommentDto commentDto);

    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "taskId", source = "task.id")
    CommentDto toDto(Comment comment);
}
