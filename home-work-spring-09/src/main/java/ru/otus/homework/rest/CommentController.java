package ru.otus.homework.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.CommentService;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private static final String EMPTY_STRING = "";

    private final CommentService commentService;
    private final BookService bookService;

    @GetMapping("/book/comments")
    public String getBookComments(@RequestParam("id") long id, Model model) {
        model.addAttribute("comments", commentService.getByBookId(id));
        model.addAttribute("book", bookService.getById(id));
        return "commentList";
    }

    @GetMapping("/book/comment/edit")
    public String getBookCommentEdit(@RequestParam("id") long id, Model model) {
        model.addAttribute("comment", commentService.getById(id));
        return "commentEdit";
    }

    @PostMapping("/comment/edit")
    public String editBookComment(@ModelAttribute Comment comment) {
        commentService.insert(comment);
        return "redirect:/book/comments?id=" + comment.getBook().getId();
    }

    @GetMapping("/book/comment/add")
    public String addBookComment(@RequestParam("id") long bookId, Model model) {
        model.addAttribute("comment", new Comment(EMPTY_STRING, bookService.getById(bookId)));
        return "commentEdit";
    }

    @GetMapping("/book/comment/delete")
    public String deleteBookComment(@RequestParam("id") long id, Model model) {
        Comment comment = commentService.getById(id);
        commentService.deleteById(id);
        return "redirect:/book/comments?id=" + comment.getBook().getId();
    }
}
