package com.favtour.travel.hotel.hotel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class HotelResponse {

    private int id;

    private String name;
    private String address;
    private String description;

    private double averagePrice;

    private String coverPhotoUrl;

    private int destinationId;
}
