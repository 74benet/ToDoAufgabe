package com.example.reviews;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    private final Set<Review> reviews = new HashSet<>();

    public ReviewRepositoryImpl() {

        reviews.add(new Review(1L, "John Doe", "Best book ever!"));
        reviews.add(new Review(2L, "Jane Smith", "Great insights on data systems."));
    }

    @Override
    public Set<Review> findAll() {
        return reviews;
    }

    @Override
    public Optional<Review> findById(Long id) {
        return reviews.stream()
                .filter(review -> review.getId().equals(id))
                .findFirst();
    }
}
