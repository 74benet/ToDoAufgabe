package com.example.reviews;

import java.util.Optional;
import java.util.Set;

public interface ReviewRepository {
    Set<Review> findAll();
    Optional<Review> findById(Long id);
}
