package shelf;

import module java.base;
import shelf.Command.*;

public class Desk {
    private final Library library;
    private final LoanService service;
    private final Repository<Integer, Member> members;
    private final List<Loan> history;
    private final LocalDate today;

    public Desk(Library library, LoanService service, Repository<Integer, Member> members,
                List<Loan> history, LocalDate today) {
        this.library = library;
        this.service = service;
        this.members = members;
        this.history = history;
        this.today = today;
    }


    public String handle(Command command) {
        return switch (command) {
            case Checkout(var isbn, var id) -> checkout(isbn, id);
            case Return(var isbn) -> "Thank you for returning " + isbn;
            case Renew(var isbn) -> "Renewed " + isbn + " for another loan period";
            case Search(var text) when text.isBlank() -> "Search needs some text";
            case Search(var text) -> library.findByTitle(text)
                    .map(Book::label)
                    .orElse("No book called " + text);
            case Report(var kind) when kind.equals("fines") ->
                    Reports.finesByMember(history, today).toString();
            case Report(var kind) -> "Unknown report: " + kind;
            case Unknown(var line) -> "Sorry, I do not understand: " + line;
        };
    }

    private String checkout(String isbn, int id) {
        Member member = members.findById(id);
        if (member == null) {
            return "No member with id " + id;
        }
        try {
            Loan loan = service.checkout(isbn, member, today);
            return "Lent " + loan.item().title() + " to " + member.getName();
        } catch (BookNotAvailableException | LoanLimitException e) {
            return "Refused: " + e.getMessage();
        }
    }
}