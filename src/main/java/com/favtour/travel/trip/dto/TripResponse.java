package com.favtour.travel.trip.dto;

import com.favtour.travel.trip.entity.Inclusion;
import com.favtour.travel.trip.entity.TripType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class TripResponse {

    private int tripId;
    private String label;
    private String coverImage;
    private String destination;
    private String run;
    private String duration;
    private TripType tripType;
    private int price;
    private String itinerary;
    private String description;
    private List<Inclusion> inclusions;
    private String meetingPoint;
    private String cancellationPolicy;
    private List<String> tripImages;
}
