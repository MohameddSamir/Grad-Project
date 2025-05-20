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
public class TripBookingResponse {

    private int bookingId;
    private int userId;
    private int tripId;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String nationality;
    private String hotelName;
    private String guideLanguage;
    private String specialRequirements;

    private LocalDateTime createdAt;
    private LocalDate bookingDate;
    private int numberOfAdults;
    private int numberOfChildren;
    private int totalPrice;
    private BookingStatus bookingStatus;
}
