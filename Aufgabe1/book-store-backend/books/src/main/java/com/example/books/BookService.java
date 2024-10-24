package com.example.books;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class BookService {

    private Set<Book> books = new HashSet<>();

    public BookService() {
        // Beispiel-Daten für Bücher hinzufügen
        books.add(new Book(1L, "Martin Kleppmann", "Designing Data-Intensive Applications"));
        books.add(new Book(2L, "Robert C. Martin", "Clean Code"));
    }

    public Set<Book> getAllBooks() {
        return books;
    }

    public Optional<Book> getBookById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }
}
