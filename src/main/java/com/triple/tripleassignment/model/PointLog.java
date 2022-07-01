package com.triple.tripleassignment.model;

import com.triple.tripleassignment.model.enumType.ActionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = @Index(name = "logIndex" ,columnList = "user_id"))
public class PointLog extends TimeStamped{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @JoinColumn
    @ManyToOne
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActionType action;

    @Column
    private Long variation;

    @JoinColumn
    @ManyToOne
    private Review review;

}
