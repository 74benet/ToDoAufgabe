package com.example.books;

import java.util.Optional;
import java.util.Set;

public interface BookRepository {
    Set<Book> findAll();
    Optional<Book> findById(Long id);
}
