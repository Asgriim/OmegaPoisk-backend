package org.omega.omegapoisk.service;

import org.omega.omegapoisk.data.ReviewDTO;
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
}
