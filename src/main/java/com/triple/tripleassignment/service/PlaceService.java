package com.triple.tripleassignment.service;

import com.triple.tripleassignment.dto.PlaceRequestDto;
import com.triple.tripleassignment.model.Place;
import com.triple.tripleassignment.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public void createPlace(PlaceRequestDto requestDto) {
        Place place = new Place(requestDto);
        placeRepository.save(place);
    }
}
