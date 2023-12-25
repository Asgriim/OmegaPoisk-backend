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
        return ResponseEntity.ok(contentService.getCardById(Anime.class, id));
    }

    @GetMapping("/comic")
    public ResponseEntity<?> comic() {
        List<CardDTO<Comic>> cardDTOList = contentService.getAllCardsOfContent(Comic.class);
        return ResponseEntity.ok(cardDTOList);
    }

    @GetMapping("/comic/{id}")
    public ResponseEntity<?> comicById(@PathVariable int id){
        return ResponseEntity.ok(contentService.getCardById(Comic.class, id));
    }


    @GetMapping("/game")
    public ResponseEntity<?> game() {
        List<CardDTO<Game>> cardDTOList = contentService.getAllCardsOfContent(Game.class);
        return ResponseEntity.ok(cardDTOList);
    }

    @GetMapping("/game/{id}")
    public ResponseEntity<?> gameById(@PathVariable int id){
        return ResponseEntity.ok(contentService.getCardById(Game.class, id));
    }

    @GetMapping("/movie")
    public ResponseEntity<?> movie() {
        List<CardDTO<Movie>> cardDTOList = contentService.getAllCardsOfContent(Movie.class);
        return ResponseEntity.ok(cardDTOList);
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<?> movieById(@PathVariable int id){
        return ResponseEntity.ok(contentService.getCardById(Movie.class, id));
    }

    @GetMapping("/tv_show")
    public ResponseEntity<?> tvShow() {
        List<CardDTO<TvShow>> cardDTOList = contentService.getAllCardsOfContent(TvShow.class);
        return ResponseEntity.ok(cardDTOList);
    }

    @GetMapping("/tv_show/{id}")
    public ResponseEntity<?> tv_showById(@PathVariable int id){
        return ResponseEntity.ok(contentService.getCardById(TvShow.class, id));
    }



    @GetMapping("/all_tags")
    public ResponseEntity<?> getAllTags() {
        return ResponseEntity.ok(contentService.getAllTags());
    }


    @GetMapping("/content/studio/{id}")
    public ResponseEntity<?> getStudioByContentId (@PathVariable int id) {
        Studio studio = contentService.getStudioByContentId(id);
        if (studio == null) {
            return ResponseEntity.badRequest().body("No studio found");
        }
        return ResponseEntity.ok(studio);

    }
}
