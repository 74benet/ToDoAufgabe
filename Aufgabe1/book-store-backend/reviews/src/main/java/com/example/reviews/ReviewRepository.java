package com.example.reviews;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class ReviewRepository {

    private Set<Review> reviews = new HashSet<>();

    public ReviewRepository() {

        reviews.add(new Review(1L, "John Doe", "Best book ever!"));
        reviews.add(new Review(2L, "Jane Smith", "Great insights on data systems."));
    }

    public Set<Review> findAll() {
        return reviews;
    }

    public Optional<Review> findById(Long id) {
        return reviews.stream()
                .filter(review -> review.getId().equals(id))
                .findFirst();
    }
}
