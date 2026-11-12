package shelf;

import java.nio.file.Path;
import java.time.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        banner();
        Library library = new Library("Riverside Library");
        library.add(new Book("9780441013593", "Dune", "Frank Herbert", 1965));
        library.add(new Book("9780141439587", "Emma", "Jane Austen", 1815));
        library.add(new Book("9781400033416", "Beloved", "Toni Morrison", 1987));
        library.printCatalogue();
        IO.println(library.count() + " books in stock");
        Member ada = new Member("Ada Lovelace", "ada@shelf.example");
        Member sam = new StudentMember("Sam Okafor", "sam@uni.example", "Riverside College");
        for (Member m : List.of(ada, sam)) {
            int ok = 0;
            for (int i = 0; i < 6; i++) {
                if (m.borrow()) ok++;
            }
            IO.println(m + " -> borrowed " + ok);
        }
        List<Item> items = List.of(
            new Book("9780441013593", "Dune", "Frank Herbert", 1965),
            new Dvd("Spirited Away", 125),
            new Magazine("National Geographic", 211));
        for (Item item : items) {
            IO.println(item.dueLabel());
            if (item instanceof Renewable) {
                Renewable r = (Renewable) item;
                IO.println("  renewable " + r.maxRenewals() + " times");
            }
        }
        IO.println("Total loan days: " + Item.totalDays(items));
        Clock clock = Clock.fixed(Instant.parse("2026-11-12T10:00:00Z"), ZoneOffset.UTC);
        LocalDate today = LocalDate.now(clock);
        Loan late = new Loan(items.get(0), ada, LocalDate.of(2026, 10, 1));
        Loan fresh = new Loan(items.get(1), sam, LocalDate.of(2026, 11, 8));
        IO.print(late.receipt(today));
        IO.print(fresh.receipt(today));
        library.add(new Book("9780441013593", "Dune", "Frank Herbert", 1965));
        IO.println(library.count() + " books");
        IO.println(library.find("9781400033416").label());
        IO.println(library.find("0000000000000"));
        library.reserve("9780441013593", sam);
        library.reserve("9780441013593", ada);
        for (int i = 0; i < 3; i++) {
            IO.println("Next for Dune: " + library.nextInLine("9780441013593"));
        }
        for (Book book : library.sortedByTitle()) {
            IO.println("  " + book.title());
        }
        Repository<String, Book> bookRepo = new InMemoryRepository<>();
        for (Book book : library.sortedByTitle()) {
            bookRepo.save(book);
        }
        Repository<Integer, Member> memberRepo = new InMemoryRepository<>();
        memberRepo.save(ada);
        memberRepo.save(sam);
        IO.println(bookRepo.count() + " books, " + memberRepo.count() + " members");
        IO.println(bookRepo.findById("9780141439587").title());
        IO.println(memberRepo.findById(2).getName());
        IO.println(Shelves.max(bookRepo.findAll()).title());
        int bookDays = Shelves.totalLoanDays(bookRepo.findAll());
        IO.println(bookDays + " and " + Shelves.totalLoanDays(items));
        List<Item> shelf = new ArrayList<>(items);
        Shelves.addClassics(shelf);
        IO.println(shelf.size() + " items, last: " + shelf.getLast().title());
        IO.println("First: " + Shelves.firstTitle(bookRepo.findAll()));
        bookRepo.save(new Book("9780141439518", "Pride and Prejudice", "Jane Austen", 1813));
        LoanService service = new LoanService(bookRepo);
        Member grace = new Member("Grace Hopper");
        String[] wanted = {"9781400033416", "9781400033416", "0000000000000",
                "9780141439587", "9780441013593", "9780141439518"};
        for (String isbn : wanted) {
            try {
                Loan loan = service.checkout(isbn, grace, today);
                IO.println("Lent " + loan.item().title() + " to " + grace.getName());
            } catch (BookNotAvailableException e) {
                IO.println("Sorry: " + e.getMessage());
                if (library.find(e.isbn()) != null) {
                    library.reserve(e.isbn(), grace);
                }
            } catch (LoanLimitException e) {
                IO.println("Limit: " + e.getMessage());
            }
        }
        IO.println("Next for Beloved: " + library.nextInLine("9781400033416").getName());
        List<Loan> history = List.of(late, fresh,
                new Loan(items.get(2), grace, LocalDate.of(2026, 11, 2)),
                new Loan(bookRepo.findById("9780141439587"), sam, LocalDate.of(2026, 10, 10)),
                new Loan(bookRepo.findById("9781400033416"), ada, LocalDate.of(2026, 10, 25)));
        for (Loan loan : Reports.overdue(history, today)) {
            String title = loan.item().title();
            String who = loan.member().getName();
            IO.println("Overdue: " + title + ", " + who + ", " + loan.daysLate(today));
        }
        IO.println(Reports.finesByMember(history, today));
        IO.println("Total fines: " + Reports.totalFines(history, today) + " cents");
        IO.println("Titles: " + Reports.titles(history));
        IO.println(library.findByTitle("emma").map(Book::author).orElse("unknown"));
        IO.println(library.findByTitle("Hamlet").map(Book::author).orElse("unknown"));
        FineRule standard = FineRule.capped(25, 500);
        IO.println("Rule for 30 days: " + standard.centsFor(30));
        List<Book> byYear = library.sortedByTitle().stream()
                .sorted(Comparator.comparing(Book::year).thenComparing(Book::title))
                .toList();
        String years = byYear.stream()
                .map(b -> b.year() + " " + b.title())
                .collect(Collectors.joining(", "));
        IO.println(years);
        Map<String, Long> perMember = history.stream()
                .collect(Collectors.groupingBy(l -> l.member().getName(),
                        TreeMap::new, Collectors.counting()));
        IO.println(perMember);
        try {
            service.save(Path.of("loans.csv"));
            service.save(Path.of("no-such-folder", "loans.csv"));
        } catch (LoanFileException e) {
            IO.println("Error: " + e.getMessage());
            IO.println("Cause: " + e.getCause());
            System.exit(2);
        }
    }

    static void banner() {
        IO.println("--- Shelf ---");
    }
}
