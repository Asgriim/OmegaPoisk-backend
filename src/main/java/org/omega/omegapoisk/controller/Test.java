package org.omega.omegapoisk.controller;

import org.omega.omegapoisk.data.*;
import org.omega.omegapoisk.entity.*;
import org.omega.omegapoisk.repository.ContentRepository;
import org.omega.omegapoisk.repository.ListRepository;
import org.omega.omegapoisk.repository.ReviewRepository;
import org.omega.omegapoisk.repository.SearchRepository;
import org.omega.omegapoisk.service.ContentService;
import org.omega.omegapoisk.service.SearchService;
import org.omega.omegapoisk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("test")
public class Test {

    @Autowired
    UserService userService;


    @Autowired
    ContentService contentService;

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ListRepository listRepository;

    @Autowired
    SearchRepository searchRepository;

    @Autowired
    SearchService searchService;

    @GetMapping("/all")
    public ResponseEntity<?> all() {
        List<CardDTO<Anime>> allCardsOfContent = contentService.getAllCardsOfContent(Anime.class);
        return ResponseEntity.ok(allCardsOfContent);
    }

    @GetMapping("/anime")
    public ResponseEntity<?> anime(@RequestBody AddContentDTO<Comic> comic) {
//        System.out.println(contentService.getContentTags(2));
//        ContentPageDTO<Anime> animeContentDTO = new ContentPageDTO<>();
//        Anime anime = new Anime();
//        anime.setId(1);
//        anime.setSeriesNum(1);
//        anime.setDescription("desc");
//        anime.setTitle("title");
//        animeContentDTO.setContent(anime);
        return ResponseEntity.ok(comic);
    }

    @PostMapping("/file")
    public ResponseEntity<?> file(@RequestPart("json") AddContentDTO<Comic> contentDTO, @RequestPart("image") MultipartFile file) {
        System.out.println("file test");
        User user = new User();
        user.setId(1);
        contentRepository.addComic(contentDTO, file, user );

        return ResponseEntity.ok("");
    }


    @GetMapping("/add")
    public ResponseEntity<?> add() {
        AddContentDTO<Anime> animeAddContentDTO = new AddContentDTO<>();
        animeAddContentDTO.setStudio("studio name");
        animeAddContentDTO.setContent(new Anime(123));
        animeAddContentDTO.setTags(contentService.getAllTags());
        return ResponseEntity.ok(animeAddContentDTO);
    }


    @GetMapping("/comic")
    public ResponseEntity<?> com() {
        AddContentDTO<Comic> animeAddContentDTO = new AddContentDTO<>();
        animeAddContentDTO.setStudio("studio name");
        animeAddContentDTO.setContent(new Comic(true));
        animeAddContentDTO.setTags(contentService.getAllTags());
        return ResponseEntity.ok(animeAddContentDTO);
    }



    @PostMapping("/upd")
    public ResponseEntity<?> upd() {
//        UserListsDTO userListsDTO = new UserListsDTO();
//        userListsDTO.setWatched(Collections.singletonList(new UserList(123,12312,13,"123","anime")));
//        List<UserList> allListByType = listRepository.getAllListByType(ListType.WATCHED);
//        String templ = "select * from %s left join content_tags on content_tags.contentid = %s\n" +
//                "                    left join tags ON tags.id = content_tags.tagid\n" +
//                "                    left join avg_rating ON avg_rating.contentid = %s where  lower(%s) ";

//        List<CardDTO<Anime>> f = searchRepository.searchByTitle(Anime.class, "f", templ);

        User user = new User();
        user.setId(1);
        searchService.searchByTitleCreator(Anime.class,"f", user);
        return ResponseEntity.ok("");
    }
}
