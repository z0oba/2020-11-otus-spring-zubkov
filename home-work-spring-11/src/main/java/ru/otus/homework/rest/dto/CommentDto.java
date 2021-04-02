package ru.otus.homework.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.otus.homework.domain.Comment;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class CommentDto {
    private String id = "";
    private String bookId = "";
    private String text;
    private String bookName;

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getBook().getId(), comment.getText(), comment.getBook().getName());
    }
}
