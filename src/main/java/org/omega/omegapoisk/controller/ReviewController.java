package org.omega.omegapoisk.controller;

import lombok.RequiredArgsConstructor;
import org.omega.omegapoisk.data.ReviewDTO;
import org.omega.omegapoisk.entity.Review;
import org.omega.omegapoisk.entity.Role;
import org.omega.omegapoisk.entity.User;
import org.omega.omegapoisk.exception.AccessDeniedException;
import org.omega.omegapoisk.service.ReviewService;
import org.omega.omegapoisk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

//todo url not agreed
@RestController
@RequestMapping("api/review")
@RequiredArgsConstructor
public class ReviewController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;


    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(reviewService.getAllReview());
    }


    @PostMapping("/del/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id){
        User user = userService.getUserFromContext();

        if (!reviewService.isOwner(user, id) && !user.getRoles().get(0).equals(Role.ADMIN)) {
            throw new AccessDeniedException();
        }

        reviewService.deleteReviewById(id);

        return ResponseEntity.ok("");
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody ReviewDTO reviewDTO) {
        User user = userService.getUserFromContext();
        Review review = reviewDTO.getReview();

        if (review.getUserId() != user.getId()) {
            throw new AccessDeniedException();
        }
        reviewService.addReview(review);

        return ResponseEntity.ok("");
    }
}
