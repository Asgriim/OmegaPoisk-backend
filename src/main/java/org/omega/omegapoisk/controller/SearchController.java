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

    @GetMapping("/anime")
    public ResponseEntity<?> anime(@RequestBody String title) {
        return ResponseEntity.ok(searchService.searchByTitle(Anime.class,title));
    }

    @GetMapping("/game")
    public ResponseEntity<?> game(@RequestBody String title) {
        return ResponseEntity.ok(searchService.searchByTitle(Game.class,title));
    }

    @GetMapping("/movie")
    public ResponseEntity<?> movie(@RequestBody String title) {
        return ResponseEntity.ok(searchService.searchByTitle(Movie.class,title));
    }

    @GetMapping("/tv_show")
    public ResponseEntity<?> TvShow(@RequestBody String title) {
        return ResponseEntity.ok(searchService.searchByTitle(TvShow.class,title));
    }

    @GetMapping("/comic/")
    public ResponseEntity<?> comic(@RequestBody String title) {
        return ResponseEntity.ok(searchService.searchByTitle(Comic.class,title));
    }

    @GetMapping("/anime/creator")
    public ResponseEntity<?> animeCr(@RequestBody String title) {
        User user = userService.getUserFromContext();
        userService.checkUser(user);
        return ResponseEntity.ok(searchService.searchByTitleCreator(Anime.class, title, user));
    }


    @GetMapping("/game/creator")
    public ResponseEntity<?> gameCr(@RequestBody String title) {
        User user = userService.getUserFromContext();
        userService.checkUser(user);
        return ResponseEntity.ok(searchService.searchByTitleCreator(Game.class, title, user));
    }

    @GetMapping("/movie/creator")
    public ResponseEntity<?> movieCr(@RequestBody String title) {
        User user = userService.getUserFromContext();
        userService.checkUser(user);
        return ResponseEntity.ok(searchService.searchByTitleCreator(Movie.class, title, user));
    }

    @GetMapping("/tv_show/creator")
    public ResponseEntity<?> tvShowCr(@RequestBody String title) {
        User user = userService.getUserFromContext();
        userService.checkUser(user);
        return ResponseEntity.ok(searchService.searchByTitleCreator(TvShow.class, title, user));
    }
    @GetMapping("/comic/creator")
    public ResponseEntity<?> comicCr(@RequestBody String title) {
        User user = userService.getUserFromContext();
        userService.checkUser(user);
        return ResponseEntity.ok(searchService.searchByTitleCreator(Comic.class, title, user));
    }
}
