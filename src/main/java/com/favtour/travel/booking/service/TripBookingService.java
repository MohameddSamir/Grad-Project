package com.favtour.travel.booking.service;

import com.favtour.travel.booking.dto.BookingActionResponse;
import com.favtour.travel.booking.exception.CancelBookingException;
import com.favtour.travel.booking.dto.TripBookingCard;
import com.favtour.travel.booking.dto.TripBookingResponse;
import com.favtour.travel.booking.entity.BookingStatus;
import com.favtour.travel.booking.entity.TripBooking;
import com.favtour.travel.booking.mapper.TripBookingMapper;
import com.favtour.travel.booking.repository.TripBookingRepository;
import com.favtour.travel.shared.EntityNotFoundException;
import com.favtour.travel.trip.entity.Trip;
import com.favtour.travel.trip.repository.TripRepository;
import com.favtour.travel.user.entity.Users;
import com.favtour.travel.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripBookingService {

    private final TripBookingRepository tripBookingRepository;
    private final UsersRepository usersRepository;
    private final TripRepository tripRepository;
    private final TripBookingMapper tripBookingMapper;

    public TripBookingResponse saveBooking(int tripId, TripBooking tripBooking) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Users users= usersRepository.findByEmail(username).orElseThrow(()->
                new EntityNotFoundException("User not found"));

        Trip trip = tripRepository.findById(tripId).orElseThrow(()->
                new EntityNotFoundException("Trip not found"));

        tripBooking.setUser(users);
        tripBooking.setTrip(trip);

        tripBooking.setTotalPrice(trip.getPrice() * tripBooking.getNumberOfAdults() +
                trip.getPrice()/2 * tripBooking.getNumberOfChildren());

        return tripBookingMapper.toTripBookingResponse(tripBookingRepository.save(tripBooking));
    }

    public List<TripBookingCard> getUserBookings() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users users= usersRepository.findByEmail(username).orElseThrow(()->new EntityNotFoundException("User not found"));

        List<TripBooking> tripBookingList= users.getTripBookingList();
        List<TripBookingCard> tripBookingCardList = new ArrayList<>();

        for (TripBooking tripBooking : tripBookingList) {
            tripBookingCardList.add(tripBookingMapper.toTripBookingCard(tripBooking));
        }

        return tripBookingCardList;
    }

    public TripBookingResponse getTripBooking(int tripId) {

        TripBooking tripBooking= tripBookingRepository.findById(tripId).orElseThrow(()->
                new EntityNotFoundException("Trip not found"));

        return tripBookingMapper.toTripBookingResponse(tripBooking);
    }

    public BookingActionResponse confirmBookingAfterPayment(int bookingId) {

        TripBooking tripBooking= tripBookingRepository.findById(bookingId).orElseThrow(()->
                new EntityNotFoundException("Booking not found"));

        if(tripBooking.getBookingStatus() == BookingStatus.PENDING) {
            tripBooking.setBookingStatus(BookingStatus.APPROVED);
        }

        return tripBookingMapper.toBookingActionResponse(tripBookingRepository.save(tripBooking));
    }

    public BookingActionResponse cancelBooking(int bookingId) {

        TripBooking tripBooking= tripBookingRepository.findById(bookingId).orElseThrow(()->
                new EntityNotFoundException("Booking not found"));

        if(tripBooking.getCreatedAt().plusHours(24).isBefore(LocalDateTime.now())) {
            throw new CancelBookingException(
                    "You can't cancel this booking now. Cancellations are only allowed within 24 hours of booking.");
        }

        tripBooking.setBookingStatus(BookingStatus.CANCELLED);
        return tripBookingMapper.toBookingActionResponse(tripBookingRepository.save(tripBooking));
    }
}
