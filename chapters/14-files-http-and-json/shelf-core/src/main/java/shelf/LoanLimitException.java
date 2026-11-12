package shelf;

public class LoanLimitException extends RuntimeException {
    public LoanLimitException(Member member) {
        super(member.getName() + " already has the maximum number of loans");
    }
}
