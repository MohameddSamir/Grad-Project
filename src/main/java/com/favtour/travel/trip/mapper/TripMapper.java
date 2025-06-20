package com.favtour.travel.trip.mapper;

import com.favtour.travel.destination.service.DestinationService;
import com.favtour.travel.trip.dto.TripRequest;
import com.favtour.travel.trip.dto.TripResponse;
import com.favtour.travel.trip.entity.Trip;
import com.favtour.travel.trip.entity.TripImages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TripMapper {

    private final DestinationService destinationService;

    public Trip mapTripRequestToTrip(TripRequest tripRequest) {

        return Trip.builder()
                .label(tripRequest.getLabel())
                .destination(destinationService.getDestinationByName(tripRequest.getDestination()))
                .run(tripRequest.getRun())
                .duration(tripRequest.getDuration())
                .tripType(tripRequest.getTripType())
                .price(tripRequest.getPrice())
                .itinerary(tripRequest.getItinerary())
                .description(tripRequest.getDescription())
                .inclusions(tripRequest.getInclusions())
                .meetingPoint(tripRequest.getMeetingPoint())
                .cancellationPolicy(tripRequest.getCancellationPolicy())
                .build();
    }

    public TripResponse mapTripToTripResponse(Trip trip) {

        return TripResponse.builder()
                .tripId(trip.getTripId())
                .label(trip.getLabel())
                .coverImage(trip.getCoverImage())
                .destination(trip.getDestination().getDestinationName())
                .run(trip.getRun())
                .duration(trip.getDuration())
                .tripType(trip.getTripType())
                .price(trip.getPrice())
                .itinerary(trip.getItinerary())
                .description(trip.getDescription())
                .inclusions(trip.getInclusions())
                .meetingPoint(trip.getMeetingPoint())
                .cancellationPolicy(trip.getCancellationPolicy())
                .tripImages(trip.getTripImages().stream().map(TripImages::getImageUrl).toList())
                .build();
    }
}
