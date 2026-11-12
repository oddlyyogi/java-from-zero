package shelf;

public class StudentMember extends Member {
    private final String school;

    public StudentMember(String name, String email, String school) {
        super(name, email);
        this.school = school;
    }

    @Override
    protected int maxLoans() {
        return 5;
    }

    @Override
    public String toString() {
        return super.toString() + ", student at " + school;
    }
}
