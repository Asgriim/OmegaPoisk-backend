package org.omega.omegapoisk.controller;

import org.omega.omegapoisk.ORM.OmegaORM;
import org.omega.omegapoisk.entity.Content;
import org.omega.omegapoisk.entity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("test")
public class Test {

    @Autowired
    OmegaORM omegaORM;

    @GetMapping("/anime")
    public ResponseEntity<?> anime() {
        List<Content> games = (List<Content>) omegaORM.getListOf(Game.class);
        Content content = games.get(1);
        System.out.println(games);
        System.out.println(content.getId());
        System.out.println(content.getDescription());
        System.out.println(content.getTitle());
        return ResponseEntity.ok("");
    }
}
