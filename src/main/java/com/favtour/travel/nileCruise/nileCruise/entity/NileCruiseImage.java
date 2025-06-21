package com.favtour.travel.nileCruise.nileCruise.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "nile_cruise_images")
@Data
public class NileCruiseImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "nile_cruise_id")
    @JsonIgnore
    private NileCruise nileCruise;
}
