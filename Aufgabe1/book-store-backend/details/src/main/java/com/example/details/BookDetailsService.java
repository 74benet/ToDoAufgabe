package com.example.details;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookDetailsService {

    private final BookDetailsRepository bookDetailsRepository;

    public BookDetailsService(BookDetailsRepository bookDetailsRepository) {
        this.bookDetailsRepository = bookDetailsRepository;
    }

    public Set<BookDetailsDTO> getAllBooks() {
        return bookDetailsRepository.findAll().stream()
                .map(book -> new BookDetailsDTO(book.getId(), book.getAuthor(), book.getTitle(), book.getType(), book.getPublisher(), book.getYear(), book.getLanguage(), book.getIsbn(), book.getPages()))
                .collect(Collectors.toSet());
    }

    public Optional<BookDetailsDTO> getBookById(Long id) {
        return bookDetailsRepository.findById(id)
                .map(book -> new BookDetailsDTO(book.getId(), book.getAuthor(), book.getTitle(), book.getType(), book.getPublisher(), book.getYear(), book.getLanguage(), book.getIsbn(), book.getPages()));
    }
}
