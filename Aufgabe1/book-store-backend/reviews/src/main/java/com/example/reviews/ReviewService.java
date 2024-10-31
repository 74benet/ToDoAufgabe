package com.example.reviews;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Set<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(review -> new ReviewDTO(review.getId(), review.getReviewer(), review.getText()))
                .collect(Collectors.toSet());
    }

    public Optional<ReviewDTO> getReviewById(Long id) {
        return reviewRepository.findById(id)
                .map(review -> new ReviewDTO(review.getId(), review.getReviewer(), review.getText()));
    }
}
