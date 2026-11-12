package dev.shelf.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

record BookRequest(@NotBlank @Size(min = 10, max = 13) String isbn,
                   @NotBlank String title,
                   @NotBlank String author,
                   @Positive int year) {
}

record BookResponse(Long id, String isbn, String title, String author, int year,
                    String borrower) {
    static BookResponse of(Book book) {
        return new BookResponse(book.getId(), book.getIsbn(), book.getTitle(),
                book.getAuthor(), book.getYear(), book.getBorrower());
    }
}

record LoanRequest(@NotBlank String member) {
}
