package com.favtour.travel.nileCruise.itinerary.repository;

import com.favtour.travel.nileCruise.itinerary.entity.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItineraryRepository extends JpaRepository<Itinerary, Integer> {
}
