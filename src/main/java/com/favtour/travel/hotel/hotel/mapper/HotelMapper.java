package com.favtour.travel.hotel.hotel.mapper;

import com.favtour.travel.hotel.hotel.dto.HotelCard;
import com.favtour.travel.hotel.hotel.dto.HotelRequest;
import com.favtour.travel.hotel.hotel.dto.HotelResponse;
import com.favtour.travel.hotel.hotel.entity.Hotel;
import com.favtour.travel.hotel.room.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HotelMapper {

    private final RoomMapper roomMapper;

    public Hotel mapToHotel(HotelRequest hotelRequest) {
        return Hotel.builder()
                .name(hotelRequest.getName())
                .address(hotelRequest.getAddress())
                .description(hotelRequest.getDescription())
                .averagePrice(hotelRequest.getAveragePrice())
                .build();
    }

    public HotelResponse mapToHotelResponse(Hotel hotel) {
        return HotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .address(hotel.getAddress())
                .description(hotel.getDescription())
                .averagePrice(hotel.getAveragePrice())
                .coverPhotoUrl(hotel.getCoverPhoto())
                .destinationId(hotel.getDestination().getDestinationId())
                .build();
    }

    public HotelCard mapToHotelCard(Hotel hotel) {
        return HotelCard.builder()
                .hotelId(hotel.getId())
                .hotelName(hotel.getName())
                .hotelAddress(hotel.getAddress())
                .hotelAveragePrice(hotel.getAveragePrice())
                .hotelCoverPhoto(hotel.getCoverPhoto())
                .roomCards(hotel.getRooms().stream().map(roomMapper::mapToRoomCard).collect(Collectors.toList()))
                .build();
    }
}
