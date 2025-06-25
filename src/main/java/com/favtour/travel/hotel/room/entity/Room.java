package com.favtour.travel.hotel.room.entity;

import com.favtour.travel.hotel.hotel.entity.Hotel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "room")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String type;

    private double price;

    private LocalDate availableFrom;
    private LocalDate availableTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<RoomImage> images;

    public void addRoomImage(RoomImage image) {
        image.setRoom(this);
        images.add(image);
    }
}
