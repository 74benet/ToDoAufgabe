package com.example.books;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final Set<Book> books = new HashSet<>();

    public BookRepositoryImpl() {
        books.add(new Book(1L, "Martin Kleppmann", "Designing Data-Intensive Applications"));
        books.add(new Book(2L, "Robert C. Martin", "Clean Code"));
    }

    @Override
    public Set<Book> findAll() {
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }
}
