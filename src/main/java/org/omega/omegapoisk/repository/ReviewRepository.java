package org.omega.omegapoisk.repository;

import org.omega.omegapoisk.ORM.OmegaORM;
import org.omega.omegapoisk.data.ReviewDTO;
import org.omega.omegapoisk.entity.Rating;
import org.omega.omegapoisk.entity.Review;
import org.omega.omegapoisk.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewRepository {
    @Autowired
    OmegaORM omegaORM;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<ReviewDTO> getAllReviews(int contentId) {
        return omegaORM.getAllReview(contentId);
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


    public void addRating(Rating rating) {
        String del = "delete from rating where contentid = ? and userid = ?";
        jdbcTemplate.update(del, rating.getContentId(), rating.getUserId());
        String req = "insert into rating(value, userid, contentid) values (?,?,?)";
        jdbcTemplate.update(req, rating.getValue(), rating.getUserId(), rating.getContentId());
    }

    public Rating getRatingById(int id, User user) {
        String req = String.format("select * from rating where userid =%s and contentid =%s",user.getId(),id);
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(req);

        List<Rating> list = (List<Rating>) omegaORM.getListFromRowSet(Rating.class, sqlRowSet);
        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }



}
