package org.omega.omegapoisk.controller;


import lombok.RequiredArgsConstructor;
import org.omega.omegapoisk.entity.Rating;
import org.omega.omegapoisk.entity.User;
import org.omega.omegapoisk.exception.AccessDeniedException;
import org.omega.omegapoisk.service.ReviewService;
import org.omega.omegapoisk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/rating")
@RequiredArgsConstructor
public class RatingController {
    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        User user = userService.getUserFromContext();
        System.out.println(user.getId());
        Rating rating = reviewService.getRatingById(id, user);
        if (rating == null) {
            return ResponseEntity.badRequest().body("no rating");
        }
        return ResponseEntity.ok(rating);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Rating rating) {
        User user = userService.getUserFromContext();
        if (user.getId() != rating.getUserId()) {
            throw new AccessDeniedException();
        }
        reviewService.addRating(rating);
        return ResponseEntity.ok("");
    }

}
