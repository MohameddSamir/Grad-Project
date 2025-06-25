package com.favtour.travel.hotel.room.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class RoomRequest {

    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Room type is required")
    private String type;

    @Min(value = 0, message = "Price must be more than zero")
    private double price;

    @NotNull(message = "You must provide a date the room will be available from")
    private LocalDate availableFrom;

    @NotNull(message = "You must provide a date the room will be available to")
    private LocalDate availableTo;

    private int hotelId;
}
