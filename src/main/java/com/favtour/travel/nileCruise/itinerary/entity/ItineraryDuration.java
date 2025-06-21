package com.favtour.travel.nileCruise.itinerary.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "itinerary_durations")
@Data
public class ItineraryDuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String label;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "itinerary_id")
    @JsonIgnore
    private Itinerary itinerary;
}
