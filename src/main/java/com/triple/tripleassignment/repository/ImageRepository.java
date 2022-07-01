package com.triple.tripleassignment.repository;

import com.triple.tripleassignment.model.Image;
import com.triple.tripleassignment.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    void deleteAllByReview(Review review);
}
