package org.omega.omegapoisk.controller;

import org.omega.omegapoisk.entity.Rating;
import org.omega.omegapoisk.entity.User;
import org.omega.omegapoisk.entity.UserList;
import org.omega.omegapoisk.exception.AccessDeniedException;
import org.omega.omegapoisk.service.ListService;
import org.omega.omegapoisk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/list")
public class UserListController {

    @Autowired
    private UserService userService;

    @Autowired
    private ListService listService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        User user = userService.getUserFromContext();
        UserList userList = listService.getListByOwner(user.getId(), id);
        System.out.println(user.getId());
        if (userList == null) {
            return ResponseEntity.badRequest().body("no user list");
        }
        return ResponseEntity.ok(userList);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody UserList userList) {
        User user = userService.getUserFromContext();
        if (user.getId() != userList.getUserId()) {
            throw new AccessDeniedException();
        }
        listService.addList(userList);
        return ResponseEntity.ok("");
    }






}
