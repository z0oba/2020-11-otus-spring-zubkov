package ru.otus.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repo.AuthorRepository;
import ru.otus.homework.repo.BookRepository;
import ru.otus.homework.repo.CommentRepository;
import ru.otus.homework.repo.GenreRepository;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    private final SequenceGeneratorService sequenceGeneratorService;

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getById(long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    @Override
    public long count() {
        return bookRepository.count();
    }

    @Override
    public long insert(String name, String authorName, String genreName) {
        Author author = authorRepository.findByName(authorName);
        if (author == null)
            author = authorRepository.save(new Author(sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME),
                    authorName));

        Genre genre = genreRepository.findByName(genreName);
        if (genre == null)
            genre = genreRepository.save(new Genre(sequenceGeneratorService.generateSequence(Genre.SEQUENCE_NAME),
                    genreName));

        Book book = bookRepository.save(new Book(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME),
                name, author, genre, null));
        return book.getId();
    }

    @Override
    public void deleteById(long id) {
        Book book = bookRepository.findById(id).get();
        //remove authors if they are not present in other books
        if(bookRepository.findBooksByAuthor(book.getAuthor()).size() == 1)
            authorRepository.deleteById(book.getAuthor().getId());

        //remove genres if they are not present in other books
        if(bookRepository.findBooksByGenre(book.getGenre()).size() == 1)
            genreRepository.deleteById(book.getGenre().getId());

        //remove comments of books, comments text is not equals
        for(Comment comment: book.getComments()) {
//            if(bookRepository.findBooksByComment(comment).size() == 1)
                commentRepository.deleteById(comment.getId());
        }
        bookRepository.deleteById(id);
    }
}