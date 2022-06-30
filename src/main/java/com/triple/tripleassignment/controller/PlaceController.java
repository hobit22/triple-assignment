package com.triple.tripleassignment.controller;

import com.triple.tripleassignment.dto.PlaceRequestDto;
import com.triple.tripleassignment.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @PostMapping("/place")
    public ResponseEntity<Object> createPlace(@RequestBody PlaceRequestDto requestDto) {
        placeService.createPlace(requestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
