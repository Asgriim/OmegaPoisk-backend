package org.omega.omegapoisk.controller;

import lombok.RequiredArgsConstructor;
import org.omega.omegapoisk.data.UserDTO;
import org.omega.omegapoisk.entity.Role;
import org.omega.omegapoisk.entity.User;
import org.omega.omegapoisk.exception.InvaliUserOrPasswordException;
import org.omega.omegapoisk.exception.UserAlreadyExistsException;
import org.omega.omegapoisk.security.JWT;
import org.omega.omegapoisk.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    PasswordEncoder encoder = new BCryptPasswordEncoder();

    private final UserService userService;
    private final JWT jwt;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO){
        System.out.println("login");
        try {
            User user = userService.loadUserByUsername(userDTO.getLogin());
            System.out.println(encoder.encode(userDTO.getPass()));
            if (!encoder.matches(userDTO.getPass(),user.getPassword())) {
                System.out.println("wrong pass or login");
                throw new InvaliUserOrPasswordException();
            }
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setRole(String.valueOf(user.getRoles().get(0)));
        } catch (UsernameNotFoundException e) {
            throw new InvaliUserOrPasswordException();
        }

        String jwtS = jwt.generateToken(userDTO.getLogin());
        System.out.println("new token: " + jwtS);
        userDTO.setToken(jwtS);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO){
        System.out.println("who registred :");
        System.out.println(userDTO);
        User user = new User(userDTO.getId(),
                userDTO.getEmail(),
                userDTO.getLogin(),
                encoder.encode(userDTO.getPass()),
                Collections.singletonList(Role.valueOf(userDTO.getRole())));
        userService.addUser(user);
        System.out.println("added user");
        return login(userDTO);
    }
}