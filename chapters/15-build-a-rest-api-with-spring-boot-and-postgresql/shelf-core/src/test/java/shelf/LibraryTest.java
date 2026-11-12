package shelf;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.*;

@DisplayName("A library")
class LibraryTest {
    private Library library;

    @BeforeAll
    static void startAll() {
        IO.println("LibraryTest starting");
    }

    @BeforeEach
    void stockShelves() {
        library = new Library("Test Library");
        library.add(new Book("9780441013593", "Dune", "Frank Herbert", 1965));
        library.add(new Book("9780141439587", "Emma", "Jane Austen", 1815));
        library.add(new Book("9781400033416", "Beloved", "Toni Morrison", 1987));
    }

    @AfterEach
    void report() {
        IO.println("books left: " + library.count());
    }

    @Test
    @DisplayName("finds a book by its ISBN")
    void findsByIsbn() {
        assertEquals("Emma", library.find("9780141439587").title());
        assertNull(library.find("0000000000000"));
    }

    @Test
    @DisplayName("sorts books by title")
    void sortsByTitle() {
        List<String> titles = library.sortedByTitle().stream().map(Book::title).toList();

        assertIterableEquals(List.of("Beloved", "Dune", "Emma"), titles);
    }

    @Test
    @Disabled("waits for the remove feature")
    void removesABook() {
        fail("not written yet");
    }

    @Nested
    @DisplayName("waitlist")
    class Waitlist {
        private final Member sam = new Member("Sam Okafor", "sam@uni.example");
        private final Member ada = new Member("Ada Lovelace", "ada@shelf.example");

        @Test
        @DisplayName("serves members in the order they joined")
        void firstInFirstOut() {
            library.reserve("9780441013593", sam);
            library.reserve("9780441013593", ada);

            assertSame(sam, library.nextInLine("9780441013593"));
            assertSame(ada, library.nextInLine("9780441013593"));
            assertNull(library.nextInLine("9780441013593"));
        }
    }
}
