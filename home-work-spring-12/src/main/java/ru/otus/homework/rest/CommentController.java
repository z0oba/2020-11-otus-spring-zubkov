package ru.otus.homework.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.rest.dto.CommentDto;
import ru.otus.homework.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/books/{bookId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getAllComments(@PathVariable Long bookId) {
        return commentService.getByBookId(bookId).stream().map(CommentDto::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateComment(@RequestBody CommentDto commentDto, @PathVariable Long commentId) {
        commentService.updateById(commentId, commentDto.getText());
    }

    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteById(commentId);
    }

    @PostMapping("/books/{bookId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(@RequestBody CommentDto commentDto, @PathVariable Long bookId) {
        return CommentDto.toDto(commentService.add(bookId, commentDto.getText()));
    }
}
