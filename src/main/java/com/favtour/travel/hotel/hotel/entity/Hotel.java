package com.favtour.travel.hotel.hotel.entity;

import com.favtour.travel.destination.entity.Destination;
import com.favtour.travel.hotel.room.entity.Room;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "hotel")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String address;

    @Column(columnDefinition = "TEXT")
    private String description;

    private double averagePrice;

    private String coverPhoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id")
    private Destination destination;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> rooms;
}
