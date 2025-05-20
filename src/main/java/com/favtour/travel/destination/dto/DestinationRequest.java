package com.favtour.travel.destination.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DestinationRequest {

    private String destinationName;
    private String destinationGuide;
}
