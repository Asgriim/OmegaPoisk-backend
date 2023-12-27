package org.omega.omegapoisk.controller;

import org.omega.omegapoisk.entity.*;
import org.omega.omegapoisk.service.SearchService;
import org.omega.omegapoisk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/anime/creator/{title}")
    public ResponseEntity<?> animeCr(@PathVariable String title) {
        User user = userService.getUserFromContext();
        userService.checkUser(user);
        return ResponseEntity.ok(searchService.searchByTitleCreator(Anime.class, title, user));
    }


    @GetMapping("/game/creator/{title}")
    public ResponseEntity<?> gameCr(@PathVariable String title) {
        User user = userService.getUserFromContext();
        userService.checkUser(user);
        return ResponseEntity.ok(searchService.searchByTitleCreator(Game.class, title, user));
    }

    @GetMapping("/movie/creator/{title}")
    public ResponseEntity<?> movieCr(@PathVariable String title) {
        User user = userService.getUserFromContext();
        userService.checkUser(user);
        return ResponseEntity.ok(searchService.searchByTitleCreator(Movie.class, title, user));
    }

    @GetMapping("/tv_show/creator/{title}")
    public ResponseEntity<?> tvShowCr(@PathVariable String title) {
        User user = userService.getUserFromContext();
        userService.checkUser(user);
        return ResponseEntity.ok(searchService.searchByTitleCreator(TvShow.class, title, user));
    }
    @GetMapping("/comic/creator/{title}")
    public ResponseEntity<?> comicCr(@PathVariable String title) {
        User user = userService.getUserFromContext();
        userService.checkUser(user);
        return ResponseEntity.ok(searchService.searchByTitleCreator(Comic.class, title, user));
    }
}
