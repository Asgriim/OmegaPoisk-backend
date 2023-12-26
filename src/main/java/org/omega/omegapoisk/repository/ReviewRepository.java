package org.omega.omegapoisk.repository;

import org.omega.omegapoisk.ORM.OmegaORM;
import org.omega.omegapoisk.data.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewRepository {
    @Autowired
    OmegaORM omegaORM;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<ReviewDTO> getAllReviews() {
        return omegaORM.getAllReview();
    }

    public void deleteReviewById() {

    }




}
