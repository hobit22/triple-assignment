package com.triple.tripleassignment.service;

import com.triple.tripleassignment.dto.PlaceRequestDto;
import com.triple.tripleassignment.model.Place;
import com.triple.tripleassignment.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public UUID createPlace(PlaceRequestDto requestDto) {
        Place place = new Place(requestDto);
        placeRepository.save(place);

        return place.getId();
    }
}
