package org.omega.omegapoisk.controller;

import org.omega.omegapoisk.exception.AccessDeniedException;
import org.omega.omegapoisk.exception.InvaliUserOrPasswordException;
import org.omega.omegapoisk.exception.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAEE() {
        return ResponseEntity.badRequest().body("This user already exists");
    }

    @ExceptionHandler(InvaliUserOrPasswordException.class)
    public ResponseEntity<?> handleIUOPE() {
        return ResponseEntity.badRequest().body("Wrong login or password");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleADE() {
        return ResponseEntity.badRequest().body("You can't access this resource");
    }

}
