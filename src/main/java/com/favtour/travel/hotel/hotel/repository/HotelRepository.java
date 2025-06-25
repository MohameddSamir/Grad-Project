package com.favtour.travel.hotel.hotel.repository;

import com.favtour.travel.hotel.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    List<Hotel> findByNameContainingIgnoreCase(String name);

    @Query(value = "SELECT h.* FROM hotel h " +
            "JOIN room r ON h.id = r.hotel_id " +
            "JOIN destination d ON h.destination_id = d.destination_id " +
            "WHERE LOWER(h.name) LIKE LOWER(CONCAT('%', :hotelName ,'%')) " +
            "AND d.destination_name = :destinationName " +
            "AND r.available_from <= :from " +
            "AND r.available_to >= :to", nativeQuery = true)
    List<Hotel> findByNameAndDestinationNameAndAvailableFromTo(@Param("hotelName") String hotelName,
                                                               @Param("destinationName") String destinationName,
                                                               @Param("from") LocalDate from,
                                                               @Param("to") LocalDate to);

    @Query(value = "SELECT h.* FROM hotel h " +
            "JOIN destination d ON h.destination_id = d.destination_id " +
            "WHERE LOWER(h.name) LIKE LOWER(CONCAT('%', :hotelName , '%')) " +
            "AND d.destination_name = :destinationName", nativeQuery = true)
    List<Hotel> findByNameAndDestination(@Param("hotelName") String hotelName ,
                                         @Param("destinationName") String destinationName);

    @Query(value = "SELECT h.* FROM hotel h " +
            "JOIN destination d ON h.destination_id = d.destination_id " +
            "JOIN room r ON h.id = r.hotel_id " +
            "WHERE d.destination_name = :destinationName " +
            "AND r.available_from <= :from " +
            "AND r.available_to >= :to"
            , nativeQuery = true)
    List<Hotel> findByDestinationAndAvailableFromTo(@Param("destinationName") String destinationName,
                                                    @Param("from") LocalDate from,
                                                    @Param("to") LocalDate to);

    @Query(value = "SELECT h.* FROM hotel h " +
            "JOIN room r ON h.id = r.hotel_id " +
            "WHERE LOWER(h.name) LIKE LOWER(CONCAT('%', :hotelName, '%')) " +
            "AND r.available_from <= :from " +
            "AND r.available_to >= :to", nativeQuery = true)
    List<Hotel> findByNameAndAvailableFromTo(@Param("hotelName") String hotelName,
                                             @Param("from") LocalDate from,
                                             @Param("to") LocalDate to);

    @Query(value = " SELECT h.* FROM hotel h " +
            "JOIN destination d ON h.destination_id = d.destination_id " +
            "WHERE d.destination_name = :destinationName", nativeQuery = true)
    List<Hotel> findByDestinationName(@Param("destinationName") String destinationName);

    @Query(value = "SELECT h.* FROM hotel h " +
            "JOIN room r ON h.id = r.hotel_id " +
            "WHERE r.available_from <= :from " +
            "AND r.available_to >= :to", nativeQuery = true)
    List<Hotel> findByAvailableFromTo(LocalDate from, LocalDate to);

}
