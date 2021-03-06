package ru.otus.homework.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.service.BookService;

@AllArgsConstructor
@ShellComponent
public class BooksCommands {

    private final BookService bookService;

    @ShellMethod(value = "Get all books from DB", key = {"get-all-books", "gab"})
    public void getAllBooks() {
        bookService.getAll().forEach(System.out::println);
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
