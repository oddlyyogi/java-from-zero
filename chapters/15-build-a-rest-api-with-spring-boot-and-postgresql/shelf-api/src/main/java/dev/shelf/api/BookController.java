package dev.shelf.api;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<BookResponse> list(@RequestParam(required = false) String author) {
        return service.list(author).stream().map(BookResponse::of).toList();
    }

    @GetMapping("/{id}")
    public BookResponse get(@PathVariable long id) {
        return BookResponse.of(service.find(id));
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request) {
        Book book = service.create(request);
        URI location = URI.create("/books/" + book.getId());
        return ResponseEntity.created(location).body(BookResponse.of(book));
    }

    @PostMapping("/{id}/loan")
    public BookResponse lend(@PathVariable long id, @Valid @RequestBody LoanRequest request) {
        return BookResponse.of(service.lend(id, request.member()));
    }
}
