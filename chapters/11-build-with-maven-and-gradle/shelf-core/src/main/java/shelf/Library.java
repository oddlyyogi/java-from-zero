package shelf;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Library {
    private final String name;
    private final Map<String, Book> books = new LinkedHashMap<>();
    private final Map<String, Deque<Member>> waitlists = new HashMap<>();

    public Library(String name) {
        this.name = name;
    }

    public void add(Book book) {
        Book old = books.put(book.isbn(), book);
        if (old != null) {
            IO.println("Replaced: " + old.title());
        }
    }

    int count() {
        return books.size();
    }

    public Book find(String isbn) {
        return books.get(isbn);
    }

    public Optional<Book> findByTitle(String title) {
        return books.values().stream()
                .filter(book -> book.title().equalsIgnoreCase(title))
                .findFirst();
    }

    public List<Book> sortedByTitle() {
        List<Book> list = new ArrayList<>(books.values());
        Collections.sort(list);
        return list;
    }

    public void reserve(String isbn, Member member) {
        Deque<Member> queue = waitlists.get(isbn);
        if (queue == null) {
            queue = new ArrayDeque<>();
            waitlists.put(isbn, queue);
        }
        queue.addLast(member);
    }

    public Member nextInLine(String isbn) {
        Deque<Member> queue = waitlists.get(isbn);
        return queue == null ? null : queue.pollFirst();
    }

    public void printCatalogue() {
        IO.println(name + " has " + books.size() + " books:");
        for (Book book : books.values()) {
            IO.println("  " + book.label());
        }
    }
}
