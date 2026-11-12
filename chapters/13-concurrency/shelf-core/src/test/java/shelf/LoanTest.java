package shelf;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LoanTest {
    private final Member ada = new Member("Ada Lovelace", "ada@shelf.example");
    private final Book dune = new Book("9780441013593", "Dune", "Frank Herbert", 1965);

    @Test
    void dueDateIsThreeWeeksAfterBorrowing() {
        Loan loan = new Loan(dune, ada, LocalDate.of(2026, 10, 1));

        LocalDate due = loan.due();

        assertEquals(LocalDate.of(2026, 10, 22), due);
    }

    @Test
    void lateLoanHasDaysAndFine() {
        Loan loan = new Loan(dune, ada, LocalDate.of(2026, 10, 1));
        LocalDate today = LocalDate.of(2026, 10, 25);

        assertAll(
                () -> assertEquals(3, loan.daysLate(today)),
                () -> assertEquals(75, loan.fineCents(today)),
                () -> assertTrue(loan.receipt(today).contains("Late:     3 days")));
    }

    @Test
    void blankTitleIsRejected() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Book("1", " ", "Nobody", 2000));

        assertEquals("title must not be blank", e.getMessage());
    }

    @ParameterizedTest(name = "returned {0} -> {1} cents")
    @CsvSource({
            "2026-10-22, 0",
            "2026-10-23, 25",
            "2026-11-10, 475",
            "2026-11-11, 500",
            "2026-12-31, 500"
    })
    void fineGrowsThenStopsAtTheCap(LocalDate today, long cents) {
        Loan loan = new Loan(dune, ada, LocalDate.of(2026, 10, 1));

        assertEquals(cents, loan.fineCents(today));
    }
}
