package com.example.books;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class BookRepository {

    private Set<Book> books = new HashSet<>();

    public BookRepository() {

        books.add(new Book(1L, "Martin Kleppmann", "Designing Data-Intensive Applications"));
        books.add(new Book(2L, "Robert C. Martin", "Clean Code"));
    }

    public Set<Book> findAll() {
        return books;
    }

    public Optional<Book> findById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }
}
