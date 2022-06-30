package com.triple.tripleassignment.model;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Image extends TimeStamped {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private String imageUrl;

    @ManyToOne
    @JoinColumn
    private Review review;

    public Image(String imgUrl) {
        this.imageUrl = imgUrl;
    }
}
