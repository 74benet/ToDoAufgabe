package com.example.books;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Set<BookDTO> getAllBooks() {

        return bookRepository.findAll().stream()
                .map(book -> new BookDTO(book.getId(), book.getAuthor(), book.getTitle()))
                .collect(Collectors.toSet());
    }

    public Optional<BookDTO> getBookById(Long id) {

        return bookRepository.findById(id)
                .map(book -> new BookDTO(book.getId(), book.getAuthor(), book.getTitle()));
    }
}
