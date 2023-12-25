package org.omega.omegapoisk.controller;

import lombok.RequiredArgsConstructor;
import org.omega.omegapoisk.data.AddContentDTO;
import org.omega.omegapoisk.entity.*;
import org.omega.omegapoisk.exception.AccessDeniedException;
import org.omega.omegapoisk.service.ContentService;
import org.omega.omegapoisk.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/creator")
@RequiredArgsConstructor
public class CreatorController {
    private final UserService userService;
    private final ContentService contentService;

    private void checkUser(User user) {
        if (user.getRoles().get(0).equals(Role.USER))  {
            throw new AccessDeniedException();
        }
    }

    @PostMapping("/add/anime")
    public ResponseEntity<?> addAnime(@RequestPart("json") AddContentDTO<Anime> contentDTO, @RequestPart("image") MultipartFile file) {
        System.out.println("add anime");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.loadUserByUsername(userDetails.getUsername());
        checkUser(user);
        System.out.println(user.getUsername());
        contentService.addAnime(contentDTO, file, user);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/add/comic")
    public ResponseEntity<?> addComic(@RequestPart("json") AddContentDTO<Comic> contentDTO, @RequestPart("image") MultipartFile file) {
        System.out.println("add comic");
        System.out.println(contentDTO.getContent());
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.loadUserByUsername(userDetails.getUsername());
        checkUser(user);
        System.out.println(user.getUsername());
        contentService.addComic(contentDTO, file, user);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/add/game")
    public ResponseEntity<?> addGame(@RequestPart("json") AddContentDTO<Game> contentDTO, @RequestPart("image") MultipartFile file) {
        System.out.println("add game");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.loadUserByUsername(userDetails.getUsername());
        checkUser(user);
        System.out.println(user.getUsername());
        contentService.addGame(contentDTO, file, user);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/add/tv_show")
    public ResponseEntity<?> addTVShow(@RequestPart("json") AddContentDTO<TvShow> contentDTO, @RequestPart("image") MultipartFile file) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.loadUserByUsername(userDetails.getUsername());
        checkUser(user);
        System.out.println(user.getUsername());
        contentService.addTvShow(contentDTO, file, user);
        return ResponseEntity.ok("ok");
    }


    @PostMapping("/add/movie")
    public ResponseEntity<?> addMovie(@RequestPart("json") AddContentDTO<Movie> contentDTO, @RequestPart("image") MultipartFile file) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.loadUserByUsername(userDetails.getUsername());
        checkUser(user);
        System.out.println(user.getUsername());
        contentService.addMovie(contentDTO, file, user);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/read/anime")
    public ResponseEntity<?> readOwnerAnime() {
        System.out.println("read anime");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.loadUserByUsername(userDetails.getUsername());
        checkUser(user);
        Class cl = Anime.class;
        if (user.getRoles().get(0).equals(Role.ADMIN)) {
//            return ResponseEntity.ok("admin");
            return ResponseEntity.ok(contentService.getAllCardsOfContent(cl));
        }
        return ResponseEntity.ok(contentService.getOwnerCards(cl, user.getId()));
    }

    @GetMapping("/read/comic")
    public ResponseEntity<?> readOwnerComic() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.loadUserByUsername(userDetails.getUsername());
        checkUser(user);
        Class cl = Comic.class;
        if (user.getRoles().get(0).equals(Role.ADMIN)) {
//            return ResponseEntity.ok("admin");
            return ResponseEntity.ok(contentService.getAllCardsOfContent(cl));
        }
        return ResponseEntity.ok(contentService.getOwnerCards(cl, user.getId()));
    }


    @GetMapping("/read/game")
    public ResponseEntity<?> readOwnerGame() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.loadUserByUsername(userDetails.getUsername());
        checkUser(user);
        Class cl = Game.class;
        if (user.getRoles().get(0).equals(Role.ADMIN)) {
//            return ResponseEntity.ok("admin");
            return ResponseEntity.ok(contentService.getAllCardsOfContent(cl));
        }
        return ResponseEntity.ok(contentService.getOwnerCards(cl, user.getId()));
    }


    @GetMapping("/read/movie")
    public ResponseEntity<?> readOwnerMovie() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.loadUserByUsername(userDetails.getUsername());
        checkUser(user);
        Class cl = Movie.class;
        if (user.getRoles().get(0).equals(Role.ADMIN)) {
//            return ResponseEntity.ok("admin");
            return ResponseEntity.ok(contentService.getAllCardsOfContent(cl));
        }
        return ResponseEntity.ok(contentService.getOwnerCards(cl, user.getId()));
    }

    @GetMapping("/read/tv_show")
    public ResponseEntity<?> readOwnerTvShow() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.loadUserByUsername(userDetails.getUsername());
        checkUser(user);
        Class cl = TvShow.class;
        if (user.getRoles().get(0).equals(Role.ADMIN)) {
//            return ResponseEntity.ok("admin");
            return ResponseEntity.ok(contentService.getAllCardsOfContent(cl));
        }
        return ResponseEntity.ok(contentService.getOwnerCards(cl, user.getId()));
    }


    private <T extends OmegaEntity> void delCont(Class<T> cl, User user, int id) {
        checkUser(user);
        System.out.println(user.getRoles().get(0));
        if (user.getRoles().get(0).equals(Role.ADMIN)) {
            contentService.deleteContentById(cl,id);
            return;
        }
        else {
            if (contentService.checkOwner(user,id)) {
                contentService.deleteContentById(cl,id);
                return;
            }
        }
        throw new AccessDeniedException() ;
    }

    @PostMapping("/del/anime/{id}")
    public ResponseEntity<?> delAnime(@PathVariable int id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.loadUserByUsername(userDetails.getUsername());
        delCont(Anime.class, user, id);
        return ResponseEntity.ok("");
    }

    @PostMapping("/del/movie/{id}")
    public ResponseEntity<?> delMovie(@PathVariable int id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.loadUserByUsername(userDetails.getUsername());
        delCont(Movie.class, user, id);
        return ResponseEntity.ok("");
    }


    @PostMapping("/del/comic/{id}")
    public ResponseEntity<?> delComic(@PathVariable int id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.loadUserByUsername(userDetails.getUsername());
        delCont(Comic.class, user, id);
        return ResponseEntity.ok("");
    }

    @PostMapping("/del/tv_show/{id}")
    public ResponseEntity<?> delTvShow(@PathVariable int id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.loadUserByUsername(userDetails.getUsername());
        delCont(TvShow.class, user, id);
        return ResponseEntity.ok("");
    }

    @PostMapping("/del/game/{id}")
    public ResponseEntity<?> delGame(@PathVariable int id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.loadUserByUsername(userDetails.getUsername());
        delCont(Game.class, user, id);
        return ResponseEntity.ok("");
    }



}

