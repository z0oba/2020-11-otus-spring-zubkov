package ru.otus.homework.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.rest.dto.CommentDto;
import ru.otus.homework.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/books/{bookId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getAllComments(@PathVariable Long bookId) {
        return commentService.getByBookId(bookId).stream().map(CommentDto::toDto)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PutMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateComment(@RequestBody CommentDto commentDto, @PathVariable Long commentId) {
        commentService.updateById(commentId, commentDto.getText());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteById(commentId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/books/{bookId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(@RequestBody CommentDto commentDto, @PathVariable Long bookId) {
        return CommentDto.toDto(commentService.add(bookId, commentDto.getText()));
    }
}
