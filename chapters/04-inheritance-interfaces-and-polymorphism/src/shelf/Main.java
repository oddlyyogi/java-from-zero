package shelf;

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
    }

    static void banner() {
        IO.println("--- Shelf ---");
    }
}
