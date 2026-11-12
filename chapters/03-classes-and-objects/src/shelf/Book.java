package shelf;

public record Book(String isbn, String title, String author, int year) {
    public Book {
        if (title.isBlank()) {
            throw new IllegalArgumentException("title must not be blank");
        }
        if (year <= 0) {
            throw new IllegalArgumentException("year must be positive: " + year);
        }
    }

    public String label() {
        return title + " by " + author + " (" + year + ")";
    }
}
