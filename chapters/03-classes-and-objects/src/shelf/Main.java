package shelf;

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
        IO.println(ada.getName() + " " + ada.getEmail());
        IO.println(ada);
        ada.setEmail("ada@newmail.example");
        IO.println(ada);
        Member grace = new Member("  Grace Hopper ");
        IO.println(grace);
        for (int i = 0; i < 4; i++) {
            IO.println("Ada borrows: " + ada.borrow());
        }
        IO.println("Limit: " + Member.MAX_LOANS);
        Member alias = ada;
        alias.setEmail("ada@lovelace.example");
        IO.println(ada.getEmail());
        updateContact(ada);
        IO.println("outside: " + ada);
        Book a = new Book("9780441013593", "Dune", "Frank Herbert", 1965);
        Book b = new Book("9780441013593", "Dune", "Frank Herbert", 1965);
        IO.println(a);
        IO.println(a.equals(b) + " " + (a == b));
        IO.println(a.title() + " / " + a.label());
    }

    static void updateContact(Member m) {
        m.setEmail("ada@library.example");
        m = new Member("Someone Else");
        IO.println("inside: " + m.getName());
    }

    static void banner() {
        IO.println("--- Shelf ---");
    }
}
