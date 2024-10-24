package com.example.details ;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
public class BookDetailsController {

    private final BookDetailsService bookDetailsService;

    public BookDetailsController(BookDetailsService bookDetailsService) {
        this.bookDetailsService = bookDetailsService;
    }

    @GetMapping("/")
    public Set<BookDetails> getAllBooks() {
        return bookDetailsService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDetails getBookById(@PathVariable Long id) {
        Optional<BookDetails> book = bookDetailsService.getBookById(id);
        return book.orElse(null);
    }
}
