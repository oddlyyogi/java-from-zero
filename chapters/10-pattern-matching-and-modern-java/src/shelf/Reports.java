package shelf;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public final class Reports {
    private Reports() {
    }

    public static List<Loan> overdue(List<Loan> loans, LocalDate today) {
        return loans.stream()
                .filter(loan -> loan.daysLate(today) > 0)
                .sorted(Comparator.comparingLong((Loan l) -> l.daysLate(today)).reversed())
                .toList();
    }

    public static Map<String, Long> finesByMember(List<Loan> loans, LocalDate today) {
        return loans.stream().collect(Collectors.groupingBy(
                loan -> loan.member().getName(),
                TreeMap::new,
                Collectors.summingLong(loan -> loan.fineCents(today))));
    }

    public static long totalFines(List<Loan> loans, LocalDate today) {
        return loans.stream().mapToLong(loan -> loan.fineCents(today)).sum();
    }

    public static String titles(List<Loan> loans) {
        return loans.stream()
                .map(loan -> loan.item().title())
                .distinct()
                .sorted()
                .collect(Collectors.joining(", "));
    }
}
