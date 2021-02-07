package ru.otus.homework.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.service.CommentService;

@AllArgsConstructor
@ShellComponent
public class CommentsCommands {

    private final CommentService commentService;

    @ShellMethod(value = "Get all comments from DB", key = {"get-all-comments", "gac"})
    public void getAllComments() {
        commentService.getAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get comment by comment id", key = {"get-comment-by-comment-id", "gcbci"})
    public void getCommentById(@ShellOption long id) {
        System.out.println(commentService.getById(id));
    }

    @ShellMethod(value = "Get comment by book id", key = {"get-comment-by-book-id", "gcbbi"})
    public void getCommentByBoookId(@ShellOption long id) {
        commentService.getByBookId(id).forEach(System.out::println);
    }

    @ShellMethod(value = "Delete comment by id", key = {"delete-comment-by-id", "dcbi"})
    public void deleteCommentById(@ShellOption long id) {
        commentService.deleteById(id);
    }

    @ShellMethod(value = "Get comments count form DB", key = {"comments-count", "cc"})
    public void commentsCount() {
        System.out.println("Comments count " + commentService.count());
    }

    @ShellMethod(value = "Insert comment to DB", key = {"insert-comment", "ic"})
    public void insertComment(
            @ShellOption(value = {"--bookId", "-bi"}, help = "id of the book") Long bookId,
            @ShellOption(value = {"--text", "-t"}, help = "text of the comment") String text) {
        System.out.println("Comment id " + commentService.insert(bookId, text));
    }
}
