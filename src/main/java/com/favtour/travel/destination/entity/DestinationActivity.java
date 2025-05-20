package com.favtour.travel.destination.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "destination_activity")
@Setter
@Getter
public class DestinationActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String activityName;

    @Column(columnDefinition = "TEXT")
    private String activityDescription;

    private String coverPhoto;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    @JsonIgnore
    private Destination destination;

}
