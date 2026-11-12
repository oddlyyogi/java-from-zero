package shelf;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

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
    }

    static void banner() {
        IO.println("--- Shelf ---");
    }
}
