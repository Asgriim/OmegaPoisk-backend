package org.omega.omegapoisk.controller;

import lombok.RequiredArgsConstructor;
import org.omega.omegapoisk.data.AddContentDTO;
import org.omega.omegapoisk.entity.*;
import org.omega.omegapoisk.service.ContentService;
import org.omega.omegapoisk.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/creator")
@RequiredArgsConstructor
public class CreatorController {
    private final UserService userService;
    private final ContentService contentService;

    @PostMapping("/add/anime")
    public ResponseEntity<?> addAnime(@RequestPart("json") AddContentDTO<Anime> contentDTO, @RequestPart("image") MultipartFile file) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.loadUserByUsername(userDetails.getUsername());
        System.out.println(user.getUsername());
        contentService.addAnime(contentDTO, file, user);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/add/comic")
    public ResponseEntity<?> addComic(@RequestPart("json") AddContentDTO<Comic> contentDTO, @RequestPart("image") MultipartFile file) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.loadUserByUsername(userDetails.getUsername());
        System.out.println(user.getUsername());
        contentService.addComic(contentDTO, file, user);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/add/game")
    public ResponseEntity<?> addGame(@RequestPart("json") AddContentDTO<Game> contentDTO, @RequestPart("image") MultipartFile file) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.loadUserByUsername(userDetails.getUsername());
        System.out.println(user.getUsername());
        contentService.addGame(contentDTO, file, user);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/add/tv_show")
    public ResponseEntity<?> addTVShow(@RequestPart("json") AddContentDTO<TvShow> contentDTO, @RequestPart("image") MultipartFile file) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.loadUserByUsername(userDetails.getUsername());
        System.out.println(user.getUsername());
        contentService.addTvShow(contentDTO, file, user);
        return ResponseEntity.ok("ok");
    }


    @PostMapping("/add/movie")
    public ResponseEntity<?> addMovie(@RequestPart("json") AddContentDTO<Movie> contentDTO, @RequestPart("image") MultipartFile file) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.loadUserByUsername(userDetails.getUsername());
        System.out.println(user.getUsername());
        contentService.addMovie(contentDTO, file, user);
        return ResponseEntity.ok("ok");
    }

}
