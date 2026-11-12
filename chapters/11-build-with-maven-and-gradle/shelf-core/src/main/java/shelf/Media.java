package shelf;

public abstract sealed class Media implements Item permits Dvd, Magazine {
    private final String title;

    protected Media(String title) {
        this.title = title;
    }

    public String title() {
        return title;
    }

    public abstract String format();

    @Override
    public String toString() {
        return format() + " \"" + title + "\"";
    }
}
