package com.favtour.travel.hotel.hotel.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HotelRequest {

    @NotBlank(message = "Hotel name is required")
    private String name;
    @NotBlank(message = "Hotel address is required")
    private String address;
    @NotBlank(message = "Hotel description is required")
    private String description;

    @Min(value = 0, message = "Price must be more than zero")
    private double averagePrice;

    private int destinationId;
}
