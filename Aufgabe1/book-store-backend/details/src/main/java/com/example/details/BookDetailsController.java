package com.example.details;

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
    public Set<BookDetailsDTO> getAllBooks() {
        return bookDetailsService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Optional<BookDetailsDTO> getBookById(@PathVariable Long id) {
        return bookDetailsService.getBookById(id);
    }
}
