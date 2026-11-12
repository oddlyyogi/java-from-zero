package shelf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.jackson.core.JacksonException;

class CatalogFilesTest {
    @TempDir
    Path dir;

    private final List<Book> books = List.of(
            new Book("9780441013593", "Dune", "Frank Herbert", 1965),
            new Book("9780141439587", "Emma", "Jane Austen", 1815));

    @Test
    void exportThenLoadGivesTheSameBooks() throws Exception {
        Path file = dir.resolve("exports/catalogue.json");

        CatalogFiles.export(books, file);

        assertEquals(books, CatalogFiles.load(file));
        assertTrue(Files.readString(file).contains("\"title\" : \"Dune\""));
    }

    @Test
    void parsesAFixtureAndIgnoresExtraFields() {
        String json = """
                [ { "isbn": "9781400033416", "title": "Beloved",
                    "author": "Toni Morrison", "year": 1987, "shelf": "M" } ]
                """;

        List<Book> parsed = CatalogFiles.parse(json);

        assertEquals("Beloved", parsed.getFirst().title());
    }

    @Test
    void brokenJsonThrowsJacksonException() {
        assertThrows(JacksonException.class, () -> CatalogFiles.parse("[ { \"isbn\": "));
    }
}
