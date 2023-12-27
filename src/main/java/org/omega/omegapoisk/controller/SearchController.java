package org.omega.omegapoisk.controller;

import org.omega.omegapoisk.entity.*;
import org.omega.omegapoisk.service.SearchService;
import org.omega.omegapoisk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/search")
public class SearchController {

    @Autowired
    UserService userService;

    @Autowired
    SearchService searchService;

    @GetMapping("/anime/{title}")
    public ResponseEntity<?> anime(@PathVariable String title) {
        return ResponseEntity.ok(searchService.searchByTitle(Anime.class,title));
    }

    @GetMapping("/game/{title}")
    public ResponseEntity<?> game(@PathVariable String title) {
        return ResponseEntity.ok(searchService.searchByTitle(Game.class,title));
    }

    @GetMapping("/movie/{title}")
    public ResponseEntity<?> movie(@PathVariable String title) {
        return ResponseEntity.ok(searchService.searchByTitle(Movie.class,title));
    }

    @GetMapping("/tv_show/{title}")
    public ResponseEntity<?> TvShow(@PathVariable String title) {
        return ResponseEntity.ok(searchService.searchByTitle(TvShow.class,title));
    }

    @GetMapping("/comic/{title}")
    public ResponseEntity<?> comic(@PathVariable String title) {
        return ResponseEntity.ok(searchService.searchByTitle(Comic.class,title));
    }


}
