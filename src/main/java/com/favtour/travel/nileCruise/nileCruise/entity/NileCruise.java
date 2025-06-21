package com.favtour.travel.nileCruise.nileCruise.entity;

import com.favtour.travel.nileCruise.itinerary.entity.Itinerary;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "nile_cruise")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NileCruise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String label;
    @Column(columnDefinition = "TEXT")

    private String location;
    private int price;
    private String highlights;

    private int cabinNumbers;
    private int decksNumber;

    private String coverPhoto;

    @OneToMany(mappedBy = "nileCruise", cascade = CascadeType.ALL)
    private List<NileCruiseImage> images= new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "nile_cruise_itinerary",
            joinColumns = @JoinColumn(name = "nile_cruise_id"),
            inverseJoinColumns = @JoinColumn(name = "itinerary_id")
    )
    private List<Itinerary> itineraries = new ArrayList<>();

    public void addNileCruiseImage(NileCruiseImage nileCruiseImage) {
        nileCruiseImage.setNileCruise(this);
        images.add(nileCruiseImage);
    }
}
