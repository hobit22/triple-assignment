package com.triple.tripleassignment.service;

import com.triple.tripleassignment.dto.PointDto;
import com.triple.tripleassignment.dto.SignupRequestDto;
import com.triple.tripleassignment.exception.CustomException;
import com.triple.tripleassignment.exception.ErrorCode;
import com.triple.tripleassignment.model.User;
import com.triple.tripleassignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UUID registerUser(SignupRequestDto requestDto) {
        User user = new User(requestDto);
        userRepository.save(user);
        return user.getId();
    }

    public Long getPoints(PointDto requestDto) {
        User user = userRepository.findById(UUID.fromString(requestDto.getUserId()))
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER_ID));

        return user.getPoint();
    }
}
