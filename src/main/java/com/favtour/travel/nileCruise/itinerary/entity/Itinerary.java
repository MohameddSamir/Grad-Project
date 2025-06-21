package com.favtour.travel.nileCruise.itinerary.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.favtour.travel.nileCruise.nileCruise.entity.NileCruise;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "itinerary")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String label;

    @OneToMany(mappedBy = "itinerary", cascade = CascadeType.ALL)
    private List<ItineraryDuration> durations;

    @ManyToMany(mappedBy = "itineraries")
    @JsonIgnore
    private List<NileCruise> nileCruises;

}
