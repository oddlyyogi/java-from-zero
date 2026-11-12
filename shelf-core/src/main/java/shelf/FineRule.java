package shelf;

@FunctionalInterface
public interface FineRule {
    long centsFor(long daysLate);

    static FineRule capped(long perDay, long max) {
        return days -> Math.min(days * perDay, max);
    }
}
