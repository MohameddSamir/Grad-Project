package com.favtour.travel.destination.entity;

import com.favtour.travel.trip.entity.Trip;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "destination")
@Setter
@Getter
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int destinationId;

    @Column(unique = true, nullable = false)
    private String destinationName;
    private String coverPhoto;

    @Column(columnDefinition = "TEXT")
    private String destinationGuide;

    private String mapPhoto;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL)
    private List<DestinationActivity> destinationActivities=new ArrayList<>();

    @OneToMany(mappedBy = "destination", cascade = CascadeType.REMOVE)
    private List<Trip> trips=new ArrayList<>();
}
