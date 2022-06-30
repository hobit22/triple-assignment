package com.triple.tripleassignment.service;

import com.triple.tripleassignment.dto.SignupRequestDto;
import com.triple.tripleassignment.model.User;
import com.triple.tripleassignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void registerUser(SignupRequestDto requestDto) {
        User user = new User(requestDto);
        userRepository.save(user);
    }
}
