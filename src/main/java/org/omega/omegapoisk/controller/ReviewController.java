package org.omega.omegapoisk.controller;

import lombok.RequiredArgsConstructor;
import org.omega.omegapoisk.service.ReviewService;
import org.omega.omegapoisk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
