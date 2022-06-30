package com.triple.tripleassignment.controller;

import com.triple.tripleassignment.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping(value = "/image", headers = ("content-type=multipart/*"))
    public ResponseEntity<Object> uploadImages(
            @RequestPart(value = "files") List<MultipartFile> files) {

        imageService.uploadImages(files);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
