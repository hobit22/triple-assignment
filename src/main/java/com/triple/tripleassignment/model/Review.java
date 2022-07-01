package com.triple.tripleassignment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Review extends TimeStamped {
    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Place place;

    @OneToMany(mappedBy = "review")
    private List<Image> imageList;

    @Column
    private LocalDateTime deleteTime;

    public void addImage(Image image) {
        if (imageList == null) {
            imageList = new ArrayList<>();
        }
        imageList.add(image);
    }

    public void updateReview(String content, List<Image> imageList) {
        this.content = content;
        this.imageList = imageList;
    }

    public void deleteReview() {
        this.deleteTime = LocalDateTime.now();
    }
}
