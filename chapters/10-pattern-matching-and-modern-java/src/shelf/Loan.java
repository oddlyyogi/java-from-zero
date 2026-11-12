package shelf;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public record Loan(Item item, Member member, LocalDate borrowed) {
    private static final DateTimeFormatter DAY =
            DateTimeFormatter.ofPattern("EEE d MMM uuuu", Locale.UK);
    public static final int CENTS_PER_DAY = 25;
    public static final int MAX_FINE = 500;

    public LocalDate due() {
        return borrowed.plusDays(item.loanDays());
    }

    public long daysLate(LocalDate today) {
        return Math.max(0, ChronoUnit.DAYS.between(due(), today));
    }

    public long fineCents(LocalDate today) {
        return Math.min(daysLate(today) * CENTS_PER_DAY, MAX_FINE);
    }

    public String receipt(LocalDate today) {
        long fine = fineCents(today);
        return """
            Riverside Library - loan receipt
            --------------------------------
            Item:     %s
            Member:   %s
            Borrowed: %s
            Due:      %s
            Late:     %d days
            Fine:     %d.%02d
            """.formatted(item.title(), member.getName(), borrowed.format(DAY),
                due().format(DAY), daysLate(today), fine / 100, fine % 100);
    }
}
