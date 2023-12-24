package org.omega.omegapoisk.controller;

import org.omega.omegapoisk.data.CardDTO;
import org.omega.omegapoisk.data.ContentPageDTO;
import org.omega.omegapoisk.entity.Anime;
import org.omega.omegapoisk.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RestController
@RequestMapping("test")
public class Test {

    @Autowired
    ContentService contentService;


    @GetMapping("/all")
    public ResponseEntity<?> all() {
        List<CardDTO<Anime>> allCardsOfContent = contentService.getAllCardsOfContent(Anime.class);
        return ResponseEntity.ok(allCardsOfContent);
    }

    @GetMapping("/anime")
    public ResponseEntity<?> anime() {
//        System.out.println(contentService.getContentTags(2));
        ContentPageDTO<Anime> animeContentDTO = new ContentPageDTO<>();
        Anime anime = new Anime();
        anime.setId(1);
        anime.setSeriesNum(1);
        anime.setDescription("desc");
        anime.setTitle("title");
        animeContentDTO.setContent(anime);
        return ResponseEntity.ok(animeContentDTO);
    }

    @PostMapping("/file")
    public ResponseEntity<?> file(@RequestPart("json") ContentPageDTO<Anime> contentDTO, @RequestPart("image") MultipartFile file) {
        System.out.println(contentDTO.getContent());
        System.out.println(file.getSize());
        System.out.println(file.getOriginalFilename());
        try {
            InputStream inputStream = file.getInputStream();
            System.out.println(System.getProperty("user.home"));
            String path = System.getProperty("user.home") + "/omega/anime/" + contentDTO.getContent().getId() + "_" +  file.getOriginalFilename();
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            fileOutputStream.write(inputStream.readAllBytes());
            fileOutputStream.close();
            System.out.println(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok("");
    }
}
