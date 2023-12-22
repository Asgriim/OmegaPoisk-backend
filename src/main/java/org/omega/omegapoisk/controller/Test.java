package org.omega.omegapoisk.controller;

import lombok.RequiredArgsConstructor;
import org.omega.omegapoisk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("test")
public class Test {

    @Autowired
    UserService userService;

    @GetMapping("/anime")
    public ResponseEntity<?> anime() {
        userService.test();
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println("test");
//        System.out.println(userDetails.getUsername());
        return ResponseEntity.ok("");
    }
}
