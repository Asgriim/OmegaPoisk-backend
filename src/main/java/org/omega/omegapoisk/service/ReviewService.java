package org.omega.omegapoisk.service;

import org.omega.omegapoisk.data.ReviewDTO;
import org.omega.omegapoisk.entity.Rating;
import org.omega.omegapoisk.entity.Review;
import org.omega.omegapoisk.entity.User;
import org.omega.omegapoisk.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    public List<ReviewDTO> getAllReview() {
        return reviewRepository.getAllReviews();
    }

    public boolean isOwner(User user, int reviewId) {
        return reviewRepository.isOwner(user, reviewId);
    }

    public void addReview(Review review) {
        if (review.getTxt().isEmpty()) {
            return;
        }
        reviewRepository.addReview(review);
    }

    public void deleteReviewById(int id) {
        reviewRepository.deleteReviewById(id);
    }

    public void addRating(Rating rating) {
        reviewRepository.addRating(rating);
    }

    public Rating getRatingById(int id, User user) {
        return reviewRepository.getRatingById(id, user);
    }

}
