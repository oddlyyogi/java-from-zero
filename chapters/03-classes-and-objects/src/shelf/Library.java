package shelf;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private final String name;
    private final List<Book> books = new ArrayList<>();

    public Library(String name) {
        this.name = name;
    }

    public void add(Book book) {
        books.add(book);
    }

    int count() {
        return books.size();
    }

    public void printCatalogue() {
        IO.println(name + " has " + books.size() + " books:");
        for (Book book : books) {
            IO.println("  " + book.label());
        }
    }
}
