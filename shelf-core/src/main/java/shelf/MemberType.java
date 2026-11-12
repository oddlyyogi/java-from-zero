package shelf;

public enum MemberType {
    CHILD(10), STUDENT(10), ADULT(25), SENIOR(0);

    private final int centsPerDay;

    MemberType(int centsPerDay) {
        this.centsPerDay = centsPerDay;
    }

    public int centsPerDay() {
        return centsPerDay;
    }

    public String label() {
        return switch (this) {
            case CHILD -> "child";
            case STUDENT -> "student";
            case ADULT -> "adult";
            case SENIOR -> "senior, no fines";
        };
    }
}
