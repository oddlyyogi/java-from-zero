package shelf;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

public class OverdueReminder {
    private final Notifier notifier;
    private final Clock clock;

    public OverdueReminder(Notifier notifier, Clock clock) {
        this.notifier = notifier;
        this.clock = clock;
    }

    public int remind(List<Loan> loans) {
        LocalDate today = LocalDate.now(clock);
        int sent = 0;
        for (Loan loan : loans) {
            long late = loan.daysLate(today);
            if (late == 0) {
                continue;
            }
            String text = loan.item().title() + " is " + late + " days late";
            try {
                if (notifier.send(loan.member().getEmail(), text)) {
                    sent++;
                }
            } catch (RuntimeException e) {
                String who = loan.member().getName();
                IO.println("Could not remind " + who + ": " + e.getMessage());
            }
        }
        return sent;
    }
}
