package org.omega.omegapoisk.repository;

import org.omega.omegapoisk.ORM.OmegaORM;
import org.omega.omegapoisk.data.ReviewDTO;
import org.omega.omegapoisk.entity.Review;
import org.omega.omegapoisk.entity.User;
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

    public void deleteReviewById(int id) {
        omegaORM.deleteById(Review.class, id);
    }

    public void addReview(Review review) {
        String req = "insert into review(txt, userid, contentid) VALUES (?,?,?)";
        jdbcTemplate.update(req, review.getTxt(),review.getUserId(), review.getContentId());
    }

    public boolean isOwner(User user, int reviewId) {
        String req = String.format("select COUNT(*) from review where review.id = %s and review.userid = %s",
                reviewId,
                user.getId());
        Integer i = jdbcTemplate.queryForObject(req, Integer.class);
        return i > 0;
    }

}
