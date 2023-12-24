package org.omega.omegapoisk.controller;

import lombok.RequiredArgsConstructor;
import org.omega.omegapoisk.data.CardDTO;
import org.omega.omegapoisk.entity.*;
import org.omega.omegapoisk.service.ContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/read")
@RequiredArgsConstructor
public class ReadContentController {
    private final ContentService contentService;

    @GetMapping("/anime")
    public ResponseEntity<?> anime() {
        System.out.println("anime call");
        List<CardDTO<Anime>> cardDTOList = contentService.getAllCardsOfContent(Anime.class);
        return ResponseEntity.ok(cardDTOList);
    }

    @GetMapping("/anime/{id}")
    public ResponseEntity<?> animeById(@PathVariable int id){
        return ResponseEntity.ok("");
    }

//todo one elem by id

    @GetMapping("/comic")
    public ResponseEntity<?> comic() {
        List<CardDTO<Comic>> cardDTOList = contentService.getAllCardsOfContent(Comic.class);
        return ResponseEntity.ok(cardDTOList);
    }

    @GetMapping("/game")
    public ResponseEntity<?> game() {
        List<CardDTO<Game>> cardDTOList = contentService.getAllCardsOfContent(Game.class);
        return ResponseEntity.ok(cardDTOList);
    }

    @GetMapping("/movie")
    public ResponseEntity<?> movie() {
        List<CardDTO<Movie>> cardDTOList = contentService.getAllCardsOfContent(Movie.class);
        return ResponseEntity.ok(cardDTOList);
    }

    @GetMapping("/tv_show")
    public ResponseEntity<?> tvShow() {
        List<CardDTO<TvShow>> cardDTOList = contentService.getAllCardsOfContent(TvShow.class);
        return ResponseEntity.ok(cardDTOList);
    }

    @GetMapping("/all_tags")
    public ResponseEntity<?> getAllTags() {
        return ResponseEntity.ok(contentService.getAllTags());
    }
}
