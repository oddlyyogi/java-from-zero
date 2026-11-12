package shelf;

public non-sealed class Magazine extends Media {
    private final int issue;

    public Magazine(String title, int issue) {
        super(title);
        this.issue = issue;
    }

    @Override
    public int loanDays() {
        return 3;
    }

    @Override
    public String format() {
        return "Magazine #" + issue;
    }
}
