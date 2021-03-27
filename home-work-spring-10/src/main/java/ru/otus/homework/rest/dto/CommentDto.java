package ru.otus.homework.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.otus.homework.domain.Comment;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class CommentDto {
    private long id = -1;
    private long bookId = -1;
    private String text;

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getBook().getId(), comment.getText());
    }
}
