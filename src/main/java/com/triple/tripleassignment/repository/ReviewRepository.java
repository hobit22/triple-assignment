package com.triple.tripleassignment.repository;

import com.triple.tripleassignment.model.Place;
import com.triple.tripleassignment.model.Review;
import com.triple.tripleassignment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Optional<Review> findByPlaceAndUserAndDeleteTimeIsNull(Place place, User user);

    boolean existsByPlace(Place place);

    boolean existsByPlaceAndDeleteTimeNull(Place place);
}
