import java.time.*;
import java.util.ArrayList;
import java.util.List;
import shelf.*;

void main() {
    Notifier slow = (email, text) -> {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    };
    Clock clock = Clock.fixed(Instant.parse("2026-11-12T10:00:00Z"), ZoneOffset.UTC);
    Book dune = new Book("9780441013593", "Dune", "Frank Herbert", 1965);
    List<Loan> loans = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
        loans.add(new Loan(dune, new Member("Member " + i), LocalDate.of(2026, 10, 1)));
    }
    OverdueReminder reminder = new OverdueReminder(slow, clock);
    long start = System.nanoTime();
    int sent = reminder.remind(loans);
    long ms = (System.nanoTime() - start) / 1_000_000;
    IO.println("one by one: " + sent + " sent, " + ms + " ms");
    start = System.nanoTime();
    sent = reminder.remindConcurrently(loans, 10);
    ms = (System.nanoTime() - start) / 1_000_000;
    IO.println("concurrent: " + sent + " sent, " + ms + " ms");
}
