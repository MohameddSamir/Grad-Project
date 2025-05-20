package com.favtour.travel.booking.repository;

import com.favtour.travel.booking.entity.TripBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripBookingRepository extends JpaRepository<TripBooking, Integer> {
}
