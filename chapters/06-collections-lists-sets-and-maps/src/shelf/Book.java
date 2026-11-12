package shelf;

public record Book(String isbn, String title, String author, int year)
        implements Item, Renewable, Comparable<Book> {
    public Book {
        if (title.isBlank()) {
            throw new IllegalArgumentException("title must not be blank");
        }
        if (year <= 0) {
            throw new IllegalArgumentException("year must be positive: " + year);
        }
    }

    @Override
    public int loanDays() {
        return 21;
    }

    @Override
    public int maxRenewals() {
        return 2;
    }

    @Override
    public String dueLabel() {
        return Item.super.dueLabel() + " (renewable)";
    }

    @Override
    public int compareTo(Book other) {
        return title.compareTo(other.title);
    }

    public String label() {
        return title + " by " + author + " (" + year + ")";
    }
}
