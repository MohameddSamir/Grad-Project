package com.favtour.travel.booking.controller;

import com.favtour.travel.booking.dto.BookingActionResponse;
import com.favtour.travel.booking.dto.TripBookingCard;
import com.favtour.travel.booking.dto.TripBookingResponse;
import com.favtour.travel.booking.entity.TripBooking;
import com.favtour.travel.booking.service.TripBookingService;
import com.favtour.travel.core.payload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TripBookingController {

    private final TripBookingService tripBookingService;

    @PostMapping("/trips/{tripId}/book")
    public ResponseEntity<ApiResponse<TripBookingResponse>> createBooking(@PathVariable int tripId,
                                                                          @Valid @RequestBody TripBooking tripBooking){
        return ResponseEntity.status(HttpStatus.CREATED).body
                (new ApiResponse<>(true, "Booking request is ready", tripBookingService.saveBooking(tripId, tripBooking)));
    }

    @GetMapping("/{userId}/bookings")
    public ResponseEntity<ApiResponse<List<TripBookingCard>>> getBookings(@PathVariable int userId){
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Your bookings are ready", tripBookingService.getUserBookings(userId)));
    }

    @GetMapping("/bookings/{bookingId}/booking-info")
    public ResponseEntity<ApiResponse<TripBookingResponse>> getBookingInfo(@PathVariable int bookingId){
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Booking info are ready", tripBookingService.getTripBooking(bookingId)));
    }

    // we will enable true paying later
    @PutMapping("/bookings/{bookingId}/pay")
    public ResponseEntity<ApiResponse<BookingActionResponse>> pay(@PathVariable int bookingId){
        return ResponseEntity.ok
                (new ApiResponse<>(true, "You have paid successfully", tripBookingService.pay(bookingId)));
    }

    @PutMapping("/bookings/{bookingId}/cancel")
    public ResponseEntity<ApiResponse<BookingActionResponse>> cancel(@PathVariable int bookingId){
        return ResponseEntity.ok
                (new ApiResponse<>(true, "You have cancelled successfully", tripBookingService.cancelBooking(bookingId)));
    }
}
