package com.favtour.travel.destination.dto;

import com.favtour.travel.trip.entity.Trip;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DestinationWithTrips {

    private int destinationId;
    private String destinationName;
    private String coverPhoto;
    private List<Trip> trips;
}
