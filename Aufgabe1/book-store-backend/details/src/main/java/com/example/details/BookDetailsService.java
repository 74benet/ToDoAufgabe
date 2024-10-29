package com.example.details;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class BookDetailsService {

    private final BookDetailsRepository bookDetailsRepository;

    public BookDetailsService(BookDetailsRepository bookDetailsRepository) {
        this.bookDetailsRepository = bookDetailsRepository;
    }

    public Set<BookDetails> getAllBooks() {
        return bookDetailsRepository.findAll();
    }

    public Optional<BookDetails> getBookById(Long id) {
        return bookDetailsRepository.findById(id);
    }
}
