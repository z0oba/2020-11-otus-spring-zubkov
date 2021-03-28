package ru.otus.homework.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.service.BookService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private static final String EMPTY_STRING = "";
    private final BookService bookService;

    @GetMapping("/books")
    public String getBooks(Model model) {
        List<Book> books = bookService.getAll();
        model.addAttribute("books", books);
        return "bookList";
    }

    @GetMapping("/book")
    public String getBook(@RequestParam("id") long id, Model model) {
        Book book = bookService.getById(id);
        model.addAttribute("books", List.of(book));
        return "bookList";
    }

    @GetMapping("/book/edit")
    public String getBookEdit(@RequestParam("id") long id, Model model) {
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        return "bookEdit";
    }

    @GetMapping("/book/add")
    public String addBook(Model model) {
        Book book = new Book(EMPTY_STRING, new Author(EMPTY_STRING), new Genre(EMPTY_STRING));
        model.addAttribute("book", book);
        return "bookEdit";
    }

    @PostMapping("/book/edit")
    public String editBook(@ModelAttribute Book book) {
        bookService.insert(book);
        return "redirect:/books";
    }

    @GetMapping("/book/delete")
    public String deleteBook(@RequestParam("id") long id, Model model) {
        bookService.deleteById(id);
        return "redirect:/books";
    }
}
