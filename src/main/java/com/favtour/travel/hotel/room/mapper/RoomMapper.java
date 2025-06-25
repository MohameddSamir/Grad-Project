package com.favtour.travel.hotel.room.mapper;

import com.favtour.travel.hotel.room.dto.RoomCard;
import com.favtour.travel.hotel.room.dto.RoomRequest;
import com.favtour.travel.hotel.room.dto.RoomResponse;
import com.favtour.travel.hotel.room.entity.Room;
import com.favtour.travel.hotel.room.entity.RoomImage;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoomMapper {

    public Room mapToRoom(RoomRequest roomRequest) {
        return Room.builder()
                .name(roomRequest.getName())
                .type(roomRequest.getType())
                .price(roomRequest.getPrice())
                .availableFrom(roomRequest.getAvailableFrom())
                .availableTo(roomRequest.getAvailableTo())
                .build();
    }

    public RoomResponse mapToRoomResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .type(room.getType())
                .price(room.getPrice())
                .availableFrom(room.getAvailableFrom())
                .availableTo(room.getAvailableTo())
                .hotelName(room.getHotel().getName())
                .hotelDescription(room.getHotel().getDescription())
                .imageUrls(room.getImages().stream().map(RoomImage::getImageUrl).collect(Collectors.toList()))
                .build();
    }

    public RoomCard mapToRoomCard(Room room) {
        return RoomCard.builder()
                .id(room.getId())
                .name(room.getName())
                .type(room.getType())
                .price(room.getPrice())
                .build();
    }
}
