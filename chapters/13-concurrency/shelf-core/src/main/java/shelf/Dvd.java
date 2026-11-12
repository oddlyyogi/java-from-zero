package shelf;

public final class Dvd extends Media {
    private final int minutes;

    public Dvd(String title, int minutes) {
        super(title);
        this.minutes = minutes;
    }

    @Override
    public int loanDays() {
        return 7;
    }

    @Override
    public String format() {
        return "DVD, " + minutes + " min";
    }
}
