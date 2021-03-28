package ru.otus.homework.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.rest.dto.BookDto;
import ru.otus.homework.rest.dto.CommentDto;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final BookService bookService;

    @PostMapping("/api/book/comments")
    public List<CommentDto> getAllComments(@RequestBody CommentDto commentDto) {
        return commentService.getByBookId(commentDto.getBookId()).stream().map(CommentDto::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/api/book/comment/update")
    void updateComment(@RequestBody CommentDto commentDto) {
        commentService.updateById(commentDto.getId(), commentDto.getText());
    }

    @PostMapping("/api/book/comment/delete")
    void deleteComment(@RequestBody CommentDto commentDto) {
        commentService.deleteById(commentDto.getId());
    }

    @PostMapping("/api/book/comment/add")
    long addComment(@RequestBody CommentDto commentDto) {
        return commentService.add(commentDto.getBookId(), commentDto.getText());
    }
}
