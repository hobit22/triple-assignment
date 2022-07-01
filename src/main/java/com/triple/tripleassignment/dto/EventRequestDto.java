package com.triple.tripleassignment.dto;

import com.triple.tripleassignment.model.enumType.ActionType;
import lombok.Getter;

import java.util.List;

@Getter
public class EventRequestDto {
    private String type;
    private ActionType action;
    private String reviewId;
    private String content;
    private List<String> attachedPhotoIds;
    private String userId;
    private String placeId;
}
