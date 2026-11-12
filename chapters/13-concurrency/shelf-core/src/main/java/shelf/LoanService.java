package shelf;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoanService {
    private final Repository<String, Book> books;
    private final List<Loan> loans = new ArrayList<>();

    public LoanService(Repository<String, Book> books) {
        this.books = Objects.requireNonNull(books, "books");
    }

    public Loan checkout(String isbn, Member member, LocalDate day) {
        Objects.requireNonNull(member, "member");
        Book book = books.findById(isbn);
        if (book == null) {
            throw new BookNotAvailableException(isbn, "not in the catalogue");
        }
        for (Loan loan : loans) {
            if (loan.item().equals(book)) {
                String who = loan.member().getName();
                throw new BookNotAvailableException(isbn, "already lent to " + who);
            }
        }
        if (!member.borrow()) {
            throw new LoanLimitException(member);
        }
        Loan loan = new Loan(book, member, day);
        loans.add(loan);
        return loan;
    }

    public void save(Path file) throws LoanFileException {
        try (BufferedWriter out = Files.newBufferedWriter(file)) {
            for (Loan loan : loans) {
                String line = loan.item().title() + "," + loan.member().getName();
                out.write(line + "," + loan.borrowed());
                out.newLine();
            }
        } catch (IOException e) {
            throw new LoanFileException("could not save loans to " + file, e);
        }
    }
}
