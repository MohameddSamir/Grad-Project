package com.favtour.travel.destination.repository;

import com.favtour.travel.destination.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DestinationRepository extends JpaRepository<Destination, Integer> {

    Optional<Destination> findByDestinationName(String destinationName);
}
