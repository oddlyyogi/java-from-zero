package shelf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class OverdueReminderTest {
    private final Clock clock =
            Clock.fixed(Instant.parse("2026-11-12T10:00:00Z"), ZoneOffset.UTC);
    private final Member ada = new Member("Ada Lovelace", "ada@shelf.example");
    private final Member sam = new Member("Sam Okafor", "sam@uni.example");
    private final Book dune = new Book("9780441013593", "Dune", "Frank Herbert", 1965);
    private final Book emma = new Book("9780141439587", "Emma", "Jane Austen", 1815);

    @Test
    void remindsOnlyLateMembers() {
        Notifier notifier = mock(Notifier.class);
        when(notifier.send(anyString(), anyString())).thenReturn(true);
        Loan late = new Loan(dune, ada, LocalDate.of(2026, 10, 1));
        Loan fresh = new Loan(emma, sam, LocalDate.of(2026, 11, 8));

        int sent = new OverdueReminder(notifier, clock).remind(List.of(late, fresh));

        assertEquals(1, sent);
        verify(notifier).send("ada@shelf.example", "Dune is 21 days late");
        verify(notifier, never()).send(eq("sam@uni.example"), anyString());
    }

    @Test
    void keepsGoingWhenOneSendFails() {
        Notifier notifier = mock(Notifier.class);
        when(notifier.send(eq("ada@shelf.example"), anyString()))
                .thenThrow(new IllegalStateException("mail server down"));
        when(notifier.send(eq("sam@uni.example"), anyString())).thenReturn(true);
        Loan adaLoan = new Loan(dune, ada, LocalDate.of(2026, 10, 1));
        Loan samLoan = new Loan(emma, sam, LocalDate.of(2026, 10, 10));

        int sent = new OverdueReminder(notifier, clock).remind(List.of(adaLoan, samLoan));

        assertEquals(1, sent);
        ArgumentCaptor<String> text = ArgumentCaptor.forClass(String.class);
        verify(notifier).send(eq("sam@uni.example"), text.capture());
        assertEquals("Emma is 12 days late", text.getValue());
    }
}
