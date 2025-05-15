package com.favtour.travel.trip.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.favtour.travel.destination.entity.Destination;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trip")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tripId;

    private String label;
    private String coverImage;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    @JsonIgnore
    private Destination destination;

    private String run;
    private String duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "trip_type")
    private TripType tripType = TripType.Group_Trip;

    private int price;
    private String itinerary;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String meetingPoint;
    private String cancellationPolicy;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<TripImages> tripImages;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<Inclusion> inclusions;

    public void addTripImage(TripImages tripImage){
        tripImage.setTrip(this);
        tripImages.add(tripImage);
    }
    
}
