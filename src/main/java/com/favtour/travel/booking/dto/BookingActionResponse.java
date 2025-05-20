package com.favtour.travel.booking.dto;

import com.favtour.travel.booking.entity.BookingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class BookingActionResponse {

    private int bookingId;
    private BookingStatus bookingStatus;
}
