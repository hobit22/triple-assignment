package com.triple.tripleassignment.controller;

import com.triple.tripleassignment.dto.EventRequestDto;
import com.triple.tripleassignment.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/events")
    public ResponseEntity<Object> publishEvent(@RequestBody EventRequestDto requestDto){

        reviewService.publishEvent(requestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
