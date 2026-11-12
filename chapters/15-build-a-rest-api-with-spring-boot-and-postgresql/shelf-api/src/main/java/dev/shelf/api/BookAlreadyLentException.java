package dev.shelf.api;

public class BookAlreadyLentException extends RuntimeException {
    public BookAlreadyLentException(String title, String borrower) {
        super(title + " is already lent to " + borrower);
    }
}
