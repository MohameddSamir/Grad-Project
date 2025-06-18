package com.favtour.travel.trip.repository;

import com.favtour.travel.trip.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Integer> {

    List<Trip> findByLabelContainingIgnoreCase(String keyword);

    List<Trip> findByDurationLessThan(Integer duration);

    @Query(value = "SELECT t.* FROM trip t " +
            "LEFT JOIN trip_category tc ON t.trip_id = tc.trip_id " +
            "LEFT JOIN category c ON tc.category_id = c.category_id " +
            "WHERE t.destination_id= :destinationId " +
            "AND (c.category_name IN :categories) " +
            "ORDER BY t.trip_id", nativeQuery = true)
    List<Trip> findByCategories(@Param("categories") List<String> categories,
                                 @Param("destinationId") int destinationId);

    @Query(value = "SELECT t.* FROM trip t " +
            "LEFT JOIN trip_category tc ON t.trip_id = tc.trip_id " +
            "LEFT JOIN category c ON tc.category_id = c.category_id " +
            "WHERE t.destination_id= :destinationId " +
            "AND (LOWER(t.label) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (c.category_name IN :categories) " +
            "AND t.duration <= :duration " +
            "ORDER BY t.trip_id", nativeQuery = true)
    List<Trip> findByCategoriesDurationAndSearch(@Param("categories") List<String> categories,
                                 @Param("duration") Integer duration,
                                 @Param("search") String search,
                                 @Param("destinationId") int destinationId);

    @Query(value = "SELECT t.* FROM trip t " +
            "JOIN trip_category tc ON t.trip_id = tc.trip_id " +
            "JOIN category c ON tc.category_id = c.category_id " +
            "WHERE t.destination_id= :destinationId " +
            "AND (LOWER(t.label) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND c.category_name IN :categories" , nativeQuery = true)
    List<Trip> findByCategoriesAndSearch(@Param("categories") List<String> categories,
                                         @Param("search") String search,
                                         @Param("destinationId") int destinationId);

    @Query(value = "SELECT t.* FROM trip t " +
            "WHERE t.destination_id= :destinationId " +
            "AND t.duration <= :duration " +
            "AND LOWER(t.label) LIKE LOWER(CONCAT('%', :search, '%'))", nativeQuery = true)
    List<Trip> findBySearchAndDuration(@Param("search") String search,
                                       @Param("duration") Integer duration,
                                       @Param("destinationId") int destinationId);

    @Query(value = "SELECT t.* FROM trip t " +
            "JOIN trip_category tc ON t.trip_id = tc.trip_id " +
            "JOIN category c ON tc.category_id = c.category_id " +
            "WHERE t.destination_id= :destinationId " +
            "AND c.category_name IN :categories " +
            "AND t.duration <= :duration", nativeQuery = true)
    List<Trip> findByCategoriesAndDuration(@Param("categories") List<String> categories,
                                           @Param("duration") Integer duration,
                                           @Param("destinationId") int destinationId);
}
