package shelf;

import java.util.List;

public final class Shelves {
    private Shelves() {
    }

    public static String describe(Item item) {
        return switch (item) {
            case Book b when b.year() < 1900 -> "a classic: " + b.title();
            case Book b -> "a book: " + b.title();
            case Dvd d -> "a film: " + d.title();
            case Magazine m -> "a magazine: " + m.title();
        };
    }

    public static String loanSummary(Loan loan) {
        return switch (loan) {
            case Loan(Book(_, var title, var author, var year), var member, _)
                    when year < 1900 ->
                    member.getName() + " has the classic " + title + " by " + author;
            case Loan(var item, var member, _) -> member.getName() + " has " + item.title();
        };
    }

    public static <T extends Comparable<? super T>> T max(List<? extends T> items) {
        T best = items.getFirst();
        for (T item : items) {
            if (item.compareTo(best) > 0) best = item;
        }
        return best;
    }

    public static <T extends Item & Comparable<? super T>> String firstTitle(List<T> items) {
        List<T> copy = new java.util.ArrayList<>(items);
        java.util.Collections.sort(copy);
        return copy.getFirst().title();
    }

    public static int totalLoanDays(List<? extends Item> items) {
        int sum = 0;
        for (Item item : items) {
            sum += item.loanDays();
        }
        return sum;
    }

    public static void addClassics(List<? super Book> target) {
        target.add(new Book("9780141439518", "Pride and Prejudice", "Jane Austen", 1813));
    }
}
