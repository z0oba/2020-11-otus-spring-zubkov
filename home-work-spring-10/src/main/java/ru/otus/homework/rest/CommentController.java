package ru.otus.homework.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.rest.dto.BookDto;
import ru.otus.homework.rest.dto.CommentDto;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

//@RestController
//@RequiredArgsConstructor
public class CommentController {

//    private final CommentService commentService;

//    @GetMapping("/api/book/comments")
//    public List<CommentDto> getAllComments() {
//
//        return commentService.stream().map(CommentDto::toDto)
//                .collect(Collectors.toList());
//    }

}
