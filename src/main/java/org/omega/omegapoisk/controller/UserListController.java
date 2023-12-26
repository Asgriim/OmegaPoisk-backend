package org.omega.omegapoisk.controller;

import org.omega.omegapoisk.data.UserListsDTO;
import org.omega.omegapoisk.entity.ListType;
import org.omega.omegapoisk.entity.Rating;
import org.omega.omegapoisk.entity.User;
import org.omega.omegapoisk.entity.UserList;
import org.omega.omegapoisk.exception.AccessDeniedException;
import org.omega.omegapoisk.service.ListService;
import org.omega.omegapoisk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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


    @GetMapping("/all")
    public ResponseEntity<?> getAll() throws InterruptedException {
        User user = userService.getUserFromContext();
        ExecutorService watched = Executors.newFixedThreadPool(1);
        ExecutorService watching = Executors.newFixedThreadPool(1);
        ExecutorService will = Executors.newFixedThreadPool(1);
        CountDownLatch latch = new CountDownLatch(3);
        UserListsDTO userListsDTO = new UserListsDTO(new ArrayList<>(),new ArrayList<>(),new ArrayList<>());

        watched.execute(() -> {
            userListsDTO.setWatched(listService.getAllListByType(ListType.WATCHED, user.getId()));
            latch.countDown();
        });

        watching.execute(() -> {
            userListsDTO.setWatching(listService.getAllListByType(ListType.WATCHING, user.getId()));
            latch.countDown();
        });

        will.execute(() -> {
            userListsDTO.setWillWatch(listService.getAllListByType(ListType.WILL_WATCH, user.getId()));
            latch.countDown();
        });

        latch.await();

        return ResponseEntity.ok(userListsDTO);
    }


    @PostMapping("/del")
    public ResponseEntity<?> del(@RequestBody UserList userList) {
        User user = userService.getUserFromContext();
        if (user.getId() != userList.getUserId()) {
            throw new AccessDeniedException();
        }
        listService.delListById(userList);
        return ResponseEntity.ok("");
    }


}
