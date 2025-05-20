package com.favtour.travel.booking.dto;

import com.favtour.travel.booking.entity.BookingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class TripBookingCard {

    private int bookingId;

    private int tripId;
    private String tripLabel;
    private String tripCoverPhoto;
    private LocalDate tripDate;

    private LocalDateTime createdAt;
    private int totalPrice;
    private BookingStatus bookingStatus;
}
