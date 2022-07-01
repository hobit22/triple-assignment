package com.triple.tripleassignment.service;

import com.triple.tripleassignment.dto.EventRequestDto;
import com.triple.tripleassignment.model.*;
import com.triple.tripleassignment.model.enumType.ActionType;
import com.triple.tripleassignment.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final PointLogRepository pointLogRepository;
    private final ImageService imageService;

    @Transactional
    public void publishEvent(EventRequestDto requestDto) {
        ActionType action = requestDto.getAction();
        switch (action) {
            case ADD:
                addReview(requestDto);
                break;
            case MOD:
                modReview(requestDto);
                break;
            case DELETE:
                deleteReview(requestDto);
                break;
        }
    }


    private void addReview(EventRequestDto requestDto) {
        String reviewId = requestDto.getReviewId();
        String placeId = requestDto.getPlaceId();
        String userId = requestDto.getUserId();

        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("유저없다."));
        Place place = placeRepository.findById(UUID.fromString(placeId))
                .orElseThrow(() -> new IllegalArgumentException("장소없다."));

        Optional<Review> foundReview = reviewRepository.findByPlaceAndUserAndDeleteTimeIsNull(place, user);
        if (foundReview.isPresent()) {
            throw new IllegalArgumentException("이 유저는 이 장소에 이미 리뷰를 남겼습니다.");
        }

        Review review = Review.builder()
                .id(UUID.fromString(reviewId))
                .content(requestDto.getContent())
                .user(user)
                .place(place)
                .build();

        long change = 0L;
        // 1자 이상 텍스트 작성 +1점
        if (requestDto.getContent().trim().length() > 0) {
            change = change + 1;
        }
        if (requestDto.getAttachedPhotoIds().size() > 0) {
            for(String photoId : requestDto.getAttachedPhotoIds()){
                imageService.uploadImage(photoId, review);
//                Image image = imageRepository.findById(UUID.fromString(photoId))
//                        .orElseThrow(() -> new IllegalArgumentException("이미지 없다."));
//                image.setReview(review);
            }
            change = change + 1;
        }

        if (!reviewRepository.existsByPlaceAndDeleteTimeNull(place)){
            change = change + 1;
        }

//        reviewRepository.save(review);
//        reviewRepository.


        PointLog pointLog = PointLog.builder()
                .id(UUID.randomUUID())
                .review(review)
                .user(user)
                .action(requestDto.getAction())
                .variation(change)
                .build();

        pointLogRepository.save(pointLog);
        user.addPointLog(pointLog);

    }

    private void modReview(EventRequestDto requestDto) {
        Review review = reviewRepository.findById(UUID.fromString(requestDto.getReviewId()))
                .orElseThrow(()-> new IllegalArgumentException("리뷰 없다."));

        User user = userRepository.findById(UUID.fromString(requestDto.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("유저없다."));
        if(!UUID.fromString(requestDto.getUserId()).equals(review.getUser().getId())){
            throw new IllegalArgumentException("작성자가 아닙니다.");
        }

        if(!UUID.fromString(requestDto.getPlaceId()).equals(review.getPlace().getId())){
            throw new IllegalArgumentException("수정하려는 리뷰와 장소가 일치하지 않습니다.");
        }

        long change = 0L;

        imageRepository.deleteAll(review.getImageList());
        List<Image> imageList = new ArrayList<>();
        if(review.getImageList().size() > 0 && requestDto.getAttachedPhotoIds().size() == 0){
            change = change - 1;
        } else if(requestDto.getAttachedPhotoIds().size() > 0){
            for(String photoId : requestDto.getAttachedPhotoIds()){
                Image image = imageRepository.findById(UUID.fromString(photoId))
                        .orElseThrow(() -> new IllegalArgumentException("이미지 없다."));
                image.setReview(review);
                imageList.add(image);
            }
            if(review.getImageList().isEmpty()){
                change = change + 1;
            }
        }

        String content = requestDto.getContent();
        review.updateReview(content, imageList);

        PointLog pointLog = PointLog.builder()
                .id(UUID.randomUUID())
                .review(review)
                .user(user)
                .action(requestDto.getAction())
                .variation(change)
                .build();

        pointLogRepository.save(pointLog);
        user.addPointLog(pointLog);
    }

    private void deleteReview(EventRequestDto requestDto) {
        Review review = reviewRepository.findById(UUID.fromString(requestDto.getReviewId()))
                .orElseThrow(()-> new IllegalArgumentException("리뷰 없다."));

        User user = userRepository.findById(UUID.fromString(requestDto.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("유저없다."));

        if(!UUID.fromString(requestDto.getUserId()).equals(review.getUser().getId())){
            throw new IllegalArgumentException("작성자가 아닙니다.");
        }

        if(!UUID.fromString(requestDto.getPlaceId()).equals(review.getPlace().getId())){
            throw new IllegalArgumentException("삭제하려는 리뷰와 장소가 일치하지 않습니다.");
        }

        List<PointLog> pointLogs = pointLogRepository.findAllByReview(review);

        long change = 0L;

        for(PointLog pointLog: pointLogs){
            change += pointLog.getVariation();
        }

        change = change * -1;

        PointLog pointLog = PointLog.builder()
                .id(UUID.randomUUID())
                .review(review)
                .user(user)
                .action(requestDto.getAction())
                .variation(change)
                .build();

        review.deleteReview();
//        reviewRepository.save(review);

        pointLogRepository.save(pointLog);
        user.addPointLog(pointLog);
    }

}
