package com.triple.tripleassignment.controller;

import com.triple.tripleassignment.dto.PointDto;
import com.triple.tripleassignment.dto.SignupRequestDto;
import com.triple.tripleassignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<Object> registerUser(@RequestBody SignupRequestDto requestDto) {
        userService.registerUser(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/points")
    public ResponseEntity<Object> getPoints(@RequestBody PointDto requestDto) {
        return new ResponseEntity<>( userService.getPoints(requestDto), HttpStatus.OK);
    }
}
