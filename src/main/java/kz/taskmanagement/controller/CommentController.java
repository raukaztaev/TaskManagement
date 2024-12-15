package kz.taskmanagement.controller;

import jakarta.validation.Valid;
import kz.taskmanagement.dto.CommentDto;
import kz.taskmanagement.entity.Comment;
import kz.taskmanagement.mapper.CommentMapper;
import kz.taskmanagement.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody @Valid CommentDto commentDto) {
        Comment comment = commentMapper.toEntity(commentDto);
        CommentDto createdCommentDto = commentMapper.toDto(commentService.createComment(comment));
        return ResponseEntity.ok(createdCommentDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        CommentDto commentDto = commentMapper.toDto(commentService.getCommentById(id));
        return ResponseEntity.ok(commentDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody @Valid CommentDto commentDto) {
        Comment comment = commentMapper.toEntity(commentDto);
        CommentDto updatedCommentDto = commentMapper.toDto(commentService.updateComment(id, comment));
        return ResponseEntity.ok(updatedCommentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
