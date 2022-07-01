package com.triple.tripleassignment.service;

import com.triple.tripleassignment.dto.EventRequestDto;
import com.triple.tripleassignment.exception.CustomException;
import com.triple.tripleassignment.exception.ErrorCode;
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
    public String publishEvent(EventRequestDto requestDto) {
        ActionType action = requestDto.getAction();
        StringBuilder sb = new StringBuilder();
        switch (action) {
            case ADD:
                sb.append("추가한 리뷰 아이디 : ");
                sb.append(addReview(requestDto));
                break;
            case MOD:
                sb.append("수정한 리뷰 아이디 : ");
                sb.append(modReview(requestDto));
                break;
            case DELETE:
                sb.append("삭제한 리뷰 아이디 : ");
                sb.append(deleteReview(requestDto));
                break;
        }
        return String.valueOf(sb);
    }


    private String addReview(EventRequestDto requestDto) {
        String reviewId = requestDto.getReviewId();
        String placeId = requestDto.getPlaceId();
        String userId = requestDto.getUserId();

        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER_ID));
        Place place = placeRepository.findById(UUID.fromString(placeId))
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_PLACE_ID));

        Optional<Review> foundReview = reviewRepository.findByPlaceAndUserAndDeleteTimeIsNull(place, user);
        if (foundReview.isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_REVIEW);
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

        reviewRepository.save(review);


        PointLog pointLog = PointLog.builder()
                .id(UUID.randomUUID())
                .review(review)
                .user(user)
                .action(requestDto.getAction())
                .variation(change)
                .build();

        pointLogRepository.save(pointLog);
        user.addPointLog(pointLog);

        return reviewId;
    }

    private String modReview(EventRequestDto requestDto) {
        Review review = reviewRepository.findById(UUID.fromString(requestDto.getReviewId()))
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_REVIEW_ID));

        User user = userRepository.findById(UUID.fromString(requestDto.getUserId()))
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER_ID));
        if(!UUID.fromString(requestDto.getUserId()).equals(review.getUser().getId())){
            throw new CustomException(ErrorCode.NOT_AUTHOR);
        }

        if(!UUID.fromString(requestDto.getPlaceId()).equals(review.getPlace().getId())){
            throw new CustomException(ErrorCode.NOT_SAME_REVIEW_AND_PLACE);
        }

        long change = 0L;

        imageRepository.deleteAll(review.getImageList());
        List<Image> imageList = new ArrayList<>();
        if(review.getImageList().size() > 0 && requestDto.getAttachedPhotoIds().size() == 0){
            change = change - 1;
        } else if(requestDto.getAttachedPhotoIds().size() > 0){
            for(String photoId : requestDto.getAttachedPhotoIds()){
                imageService.uploadImage(photoId, review);
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

        return requestDto.getReviewId();
    }

    private String deleteReview(EventRequestDto requestDto) {
        Review review = reviewRepository.findById(UUID.fromString(requestDto.getReviewId()))
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_REVIEW_ID));

        User user = userRepository.findById(UUID.fromString(requestDto.getUserId()))
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER_ID));

        if(!UUID.fromString(requestDto.getUserId()).equals(review.getUser().getId())){
            throw  new CustomException(ErrorCode.NOT_AUTHOR);
        }

        if(!UUID.fromString(requestDto.getPlaceId()).equals(review.getPlace().getId())){
            throw new CustomException(ErrorCode.NOT_SAME_REVIEW_AND_PLACE);
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
        return requestDto.getReviewId();
    }

}
