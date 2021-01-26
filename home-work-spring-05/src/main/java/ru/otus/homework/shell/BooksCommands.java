package ru.otus.homework.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.service.BookServiceImpl;

@ShellComponent
public class BooksCommands {

    @Autowired
    private final BookServiceImpl bookService;

    public BooksCommands(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @ShellMethod(value = "Get all books from DB", key = {"get-all-books", "gab"})
    public void getAllBooks() {
        System.out.println(bookService.getAll());
    }

    @ShellMethod(value = "Get book by id", key = {"get-book-by-id", "gbbi"})
    public void getBookById(@ShellOption long id) {
        System.out.println(bookService.getById(id));
    }

    @ShellMethod(value = "Delete book by id", key = {"delete-book-by-id", "dbbi"})
    public void deleteBookById(@ShellOption long id) {
        bookService.deleteById(id);
    }

    @ShellMethod(value = "Get books count form DB", key = {"books-count", "bc"})
    public void booksCount() {
        System.out.println("Books count " + bookService.count());
    }

    @ShellMethod(value = "Insert book to DB", key = {"insert-book", "ib"})
    public void insertBook(
            @ShellOption(value = {"--name", "-n"}, help = "name of the book") String name,
            @ShellOption(value = {"--author", "-a"}, help = "author of the book") String author,
            @ShellOption(value = {"--genre", "-g"}, help = "genre of the book") String genre) {
        System.out.println("Book id " + bookService.insert(name, author, genre));
    }
}
