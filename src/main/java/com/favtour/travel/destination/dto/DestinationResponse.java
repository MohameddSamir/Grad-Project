package com.favtour.travel.destination.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DestinationResponse {

    private int destinationId;
    private String destinationName;
    private String coverPhoto;
    private String destinationGuide;
    private String mapPhoto;
}
