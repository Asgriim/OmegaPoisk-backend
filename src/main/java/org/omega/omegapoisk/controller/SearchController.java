package org.omega.omegapoisk.controller;

import org.omega.omegapoisk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/search")
public class SearchController {

    @Autowired
    UserService userService;


}
