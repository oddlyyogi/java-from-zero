package shelf;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ReportsTest {
    static final Member ADA = new Member("Ada Lovelace", "ada@shelf.example");
    static final Book DUNE = new Book("9780441013593", "Dune", "Frank Herbert", 1965);
    static final Book EMMA = new Book("9780141439587", "Emma", "Jane Austen", 1815);

    static Stream<Arguments> loanSets() {
        LocalDate oct1 = LocalDate.of(2026, 10, 1);
        return Stream.of(
                Arguments.of(List.of(), 0),
                Arguments.of(List.of(new Loan(DUNE, ADA, oct1)), 500),
                Arguments.of(
                        List.of(new Loan(DUNE, ADA, oct1), new Loan(EMMA, ADA, oct1)), 1000));
    }

    @ParameterizedTest
    @MethodSource("loanSets")
    void totalFinesAddsEveryLoan(List<Loan> loans, long expected) {
        assertEquals(expected, Reports.totalFines(loans, LocalDate.of(2026, 12, 1)));
    }
}
