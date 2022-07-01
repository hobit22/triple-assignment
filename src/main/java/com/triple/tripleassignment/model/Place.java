package com.triple.tripleassignment.model;

import com.triple.tripleassignment.dto.PlaceRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Place extends TimeStamped{

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "place")
    private List<Review> reviewList;

    public Place(PlaceRequestDto requestDto) {
        this.name = requestDto.getName();
    }
}
