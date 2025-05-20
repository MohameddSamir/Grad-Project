package com.favtour.travel.booking.mapper;


import com.favtour.travel.booking.dto.BookingActionResponse;
import com.favtour.travel.booking.dto.TripBookingCard;
import com.favtour.travel.booking.dto.TripBookingResponse;
import com.favtour.travel.booking.entity.TripBooking;
import org.springframework.stereotype.Component;

@Component
public class TripBookingMapper {

    public TripBookingResponse toTripBookingResponse(TripBooking tripBooking) {

        return TripBookingResponse.builder()
                .bookingId(tripBooking.getBookingId())
                .userId(tripBooking.getUser().getUserId())
                .tripId(tripBooking.getTrip().getTripId())

                .firstName(tripBooking.getFirstName())
                .lastName(tripBooking.getLastName())
                .email(tripBooking.getEmail())
                .phone(tripBooking.getPhone())
                .nationality(tripBooking.getNationality())
                .hotelName(tripBooking.getHotelName())
                .guideLanguage(tripBooking.getGuideLanguage())
                .specialRequirements(tripBooking.getSpecialRequirements())

                .createdAt(tripBooking.getCreatedAt())
                .bookingDate(tripBooking.getBookingDate())
                .numberOfAdults(tripBooking.getNumberOfAdults())
                .numberOfChildren(tripBooking.getNumberOfChildren())
                .totalPrice(tripBooking.getTotalPrice())
                .bookingStatus(tripBooking.getBookingStatus())
                .build();
    }

    public TripBookingCard toTripBookingCard(TripBooking tripBooking) {

        return TripBookingCard.builder()
                .bookingId(tripBooking.getBookingId())
                .tripId(tripBooking.getTrip().getTripId())
                .tripLabel(tripBooking.getTrip().getLabel())
                .tripCoverPhoto(tripBooking.getTrip().getCoverImage())
                .tripDate(tripBooking.getBookingDate())
                .createdAt(tripBooking.getCreatedAt())
                .totalPrice(tripBooking.getTotalPrice())
                .bookingStatus(tripBooking.getBookingStatus())
                .build();

    }

    public BookingActionResponse toBookingActionResponse(TripBooking tripBooking) {

        return BookingActionResponse.builder()
                .bookingId(tripBooking.getBookingId())
                .bookingStatus(tripBooking.getBookingStatus())
                .build();
    }
}
