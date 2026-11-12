package dev.shelf.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShelfApiApplicationTests {
    @Autowired
    BookRepository books;

    @Test
    void derivedQueryIgnoresCase() {
        assertEquals(1, books.findByAuthorIgnoreCaseOrderByTitle("JANE AUSTEN").size());
    }
}