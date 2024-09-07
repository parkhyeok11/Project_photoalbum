package com.squarecross.photoalbum.controller;

import com.squarecross.photoalbum.dto.UserDto;
import com.squarecross.photoalbum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto loginRequest) {
        UserDto authenticatedUser = userService.login(loginRequest.getUserId(), loginRequest.getPassword());
        if (authenticatedUser != null) {
            // 비밀번호 정보는 응답에서 제거
            authenticatedUser.setPassword(null);
            return ResponseEntity.ok(authenticatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDto signupRequest) {
        try {
            UserDto createdUser = userService.signup(signupRequest.getUserId(), signupRequest.getPassword());
            // 비밀번호 정보는 응답에서 제거
            createdUser.setPassword(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            // 아이디 중복
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during signup");
        }
    }
}