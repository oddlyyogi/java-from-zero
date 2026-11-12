package dev.shelf.api;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(long id) {
        super("no book with id " + id);
    }
}
