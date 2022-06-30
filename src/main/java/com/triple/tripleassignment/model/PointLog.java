package com.triple.tripleassignment.model;

import com.triple.tripleassignment.model.enumType.ActionType;

import javax.persistence.*;

@Entity
public class PointLog extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne
    private User user;

    @Column
    private ActionType action;

}
