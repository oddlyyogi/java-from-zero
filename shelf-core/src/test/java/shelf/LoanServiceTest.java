package shelf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoanServiceTest {
    private final LocalDate day = LocalDate.of(2026, 11, 12);
    private LoanService service;

    @BeforeEach
    void setUp() {
        Repository<String, Book> books = new InMemoryRepository<>();
        books.save(new Book("9780441013593", "Dune", "Frank Herbert", 1965));
        books.save(new Book("9780141439587", "Emma", "Jane Austen", 1815));
        service = new LoanService(books);
    }

    @Test
    void aBookCannotBeLentTwice() {
        service.checkout("9780441013593", new Member("Ada Lovelace"), day);

        var e = assertThrows(BookNotAvailableException.class,
                () -> service.checkout("9780441013593", new Member("Sam Okafor"), day));

        assertEquals("Book 9780441013593 is not available: already lent to Ada Lovelace",
                e.getMessage());
    }

    @Test
    void memberIsRequired() {
        var e = assertThrows(NullPointerException.class,
                () -> service.checkout("9780441013593", null, day));

        assertEquals("member", e.getMessage());
    }
}
