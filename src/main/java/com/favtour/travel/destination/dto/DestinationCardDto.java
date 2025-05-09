package com.favtour.travel.destination.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DestinationCardDto {

    private int destinationId;
    private String destinationName;
    private String coverPhoto;
    private int numberOfTrips;
}
