package dev.shelf.api;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {
    private final BookRepository books;

    public BookService(BookRepository books) {
        this.books = books;
    }

    @Transactional(readOnly = true)
    public List<Book> list(String author) {
        if (author == null) {
            return books.findAll();
        }
        return books.findByAuthorIgnoreCaseOrderByTitle(author);
    }

    @Transactional(readOnly = true)
    public Book find(long id) {
        return books.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    @Transactional
    public Book create(BookRequest request) {
        Book book = new Book(request.isbn(), request.title(), request.author(), request.year());
        return books.save(book);
    }

    @Transactional
    public Book lend(long id, String member) {
        Book book = find(id);
        book.lendTo(member);
        return book;
    }
}
