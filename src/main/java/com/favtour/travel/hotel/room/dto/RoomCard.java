package com.favtour.travel.hotel.room.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class RoomCard {

    private int id;

    private String name;
    private String type;

    private double price;
}
