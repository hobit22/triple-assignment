package com.triple.tripleassignment.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Image extends TimeStamped {
    @Id
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn
    private Review review;

    public Image(UUID id, Review review) {
        this.id = id;
        this.review = review;
    }

    public void setReview(Review review) {
        this.review = review;
        this.review.addImage(this);
    }
}
