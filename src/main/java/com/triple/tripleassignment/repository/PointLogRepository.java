package com.triple.tripleassignment.repository;

import com.triple.tripleassignment.model.PointLog;
import com.triple.tripleassignment.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointLogRepository extends JpaRepository<PointLog, Long> {
    List<PointLog> findAllByReview(Review review);
}
