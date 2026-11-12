package shelf;

public class BookNotAvailableException extends RuntimeException {
    private final String isbn;

    public BookNotAvailableException(String isbn, String reason) {
        super("Book " + isbn + " is not available: " + reason);
        this.isbn = isbn;
    }

    public String isbn() {
        return isbn;
    }
}
