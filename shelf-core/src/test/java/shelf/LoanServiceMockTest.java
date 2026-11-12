package shelf;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoanServiceMockTest {
    @Mock
    Repository<String, Book> books;

    @InjectMocks
    LoanService service;

    @Test
    void unknownIsbnIsNotAvailable() {
        when(books.findById("0000000000000")).thenReturn(null);
        assertThrows(BookNotAvailableException.class,
                () -> service.checkout("0000000000000", new Member("Ada Lovelace"),
                        LocalDate.of(2026, 11, 12)));
    }
}
