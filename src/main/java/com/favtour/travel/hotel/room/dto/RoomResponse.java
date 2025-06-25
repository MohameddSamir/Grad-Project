package com.favtour.travel.hotel.room.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Builder
public class RoomResponse {

    private int id;

    private String name;
    private String type;

    private double price;

    private LocalDate availableFrom;
    private LocalDate availableTo;

    private String hotelName;
    private String hotelDescription;

    private List<String> imageUrls;
}
