package com.favtour.travel.trip.dto;

import com.favtour.travel.trip.entity.TripType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TripRequest {

    private String label;
    private String location;
    private String run;
    private String duration;
    private TripType tripType;
    private int price;
    private String itinerary;
    private String description;
    private String meetingPoint;
    private String cancellationPolicy;
}
