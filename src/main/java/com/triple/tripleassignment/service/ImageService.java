package com.triple.tripleassignment.service;

import com.triple.tripleassignment.model.Image;
import com.triple.tripleassignment.model.Review;
import com.triple.tripleassignment.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public void uploadImage(String file, Review review) {
        Image image = new Image(UUID.fromString(file), review);
        imageRepository.save(image);

    }
}
