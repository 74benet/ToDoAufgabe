package com.example.details;

import java.util.Optional;
import java.util.Set;

public interface BookDetailsRepository {
    Set<BookDetails> findAll();
    Optional<BookDetails> findById(Long id);
}
