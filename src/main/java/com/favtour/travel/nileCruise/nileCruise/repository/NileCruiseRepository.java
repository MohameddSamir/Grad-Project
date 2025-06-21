package com.favtour.travel.nileCruise.nileCruise.repository;

import com.favtour.travel.nileCruise.nileCruise.entity.NileCruise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NileCruiseRepository extends JpaRepository<NileCruise, Integer> {

    List<NileCruise> findByLabelContainingIgnoreCase(String label);

    @Query(value = "SELECT n.* FROM nile_cruise n " +
            "JOIN nile_cruise_itinerary ni ON n.id = ni.nile_cruise_id " +
            "JOIN itinerary i ON ni.itinerary_id = i.id " +
            "WHERE LOWER(i.label) LIKE LOWER(CONCAT('%', :itineraryName, '%')) ", nativeQuery = true)
    List<NileCruise> findNileCruiseByItineraryName(@Param("itineraryName") String itineraryName);
}
