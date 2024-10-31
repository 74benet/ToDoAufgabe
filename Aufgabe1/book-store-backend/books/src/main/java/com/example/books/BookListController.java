package com.example.books;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
public class BookListController {

    private final BookService bookService;

    public BookListController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public Set<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Optional<BookDTO> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }
}
