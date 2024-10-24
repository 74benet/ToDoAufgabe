package com.example.reviews;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ReviewService {

    private Set<Review> reviews = new HashSet<>();

    public ReviewService() {
        // Beispiel-Daten hinzufügen
        reviews.add(new Review(1L, "John Doe", "Best book ever!"));
        reviews.add(new Review(2L, "Jane Smith", "Great insights on data systems."));
    }

    public Set<Review> getAllReviews() {
        return reviews;
    }

    public Optional<Review> getReviewById(Long id) {
        return reviews.stream()
                .filter(review -> review.getId().equals(id))
                .findFirst();
    }
}
