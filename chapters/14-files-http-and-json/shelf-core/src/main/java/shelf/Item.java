package shelf;

public sealed interface Item permits Book, Media {
    String title();

    int loanDays();

    default String dueLabel() {
        return title() + ": " + days(loanDays());
    }

    static int totalDays(java.util.List<Item> items) {
        int sum = 0;
        for (Item item : items) {
            sum += item.loanDays();
        }
        return sum;
    }

    private static String days(int n) {
        return n == 1 ? "1 day" : n + " days";
    }
}
