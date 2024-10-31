package com.example.details;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class BookDetailsRepositoryImpl implements BookDetailsRepository {

    private final Set<BookDetails> books = new HashSet<>();

    public BookDetailsRepositoryImpl() {
        books.add(new BookDetails(1L, "Martin Kleppmann", "Designing Data-Intensive Applications", "hardcover", "O'Reilly Media", 2017, "English", "978-1449373320", 590));
        books.add(new BookDetails(2L, "Robert C. Martin", "Clean Code", "paperback", "Prentice Hall", 2008, "English", "978-0132350884", 464));
    }

    @Override
    public Set<BookDetails> findAll() {
        return books;
    }

    @Override
    public Optional<BookDetails> findById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }
}
