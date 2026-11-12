package shelf;

public class Member {
    public static final int MAX_LOANS = 3;
    private static int nextId = 1;

    private final int id;
    private final String name;
    private String email;
    private int loans;

    public Member(String name, String email) {
        this.id = nextId++;
        this.name = name;
        setEmail(email);
    }

    public Member(String name) {
        String clean = name.strip();
        if (clean.isEmpty()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        this(clean, clean.toLowerCase().replace(' ', '.') + "@shelf.example");
    }

    public final String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException("not an email address: " + email);
        }
        this.email = email;
    }

    public boolean borrow() {
        if (loans >= maxLoans()) {
            return false;
        }
        loans++;
        return true;
    }

    protected int maxLoans() {
        return MAX_LOANS;
    }

    @Override
    public String toString() {
        return "Member #" + id + " " + name + " <" + email + ">, loans: " + loans;
    }
}
