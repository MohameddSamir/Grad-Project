package com.favtour.travel.hotel.hotel.dto;

import com.favtour.travel.hotel.room.dto.RoomCard;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class HotelCard {

    private int hotelId;

    private String hotelName;
    private String hotelAddress;
    private String hotelCoverPhoto;

    private double hotelAveragePrice;

    List<RoomCard> roomCards;
}
