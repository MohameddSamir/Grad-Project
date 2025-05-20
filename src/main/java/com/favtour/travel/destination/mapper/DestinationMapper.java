package com.favtour.travel.destination.mapper;

import com.favtour.travel.destination.dto.DestinationRequest;
import com.favtour.travel.destination.dto.DestinationResponse;
import com.favtour.travel.destination.entity.Destination;
import org.springframework.stereotype.Component;

@Component
public class DestinationMapper {

    public Destination toDestination(DestinationRequest destinationRequest) {

        return Destination.builder()
                .destinationName(destinationRequest.getDestinationName())
                .destinationGuide(destinationRequest.getDestinationGuide())
                .build();
    }

    public DestinationResponse toDestinationResponse(Destination destination) {
        return DestinationResponse.builder()
                .destinationId(destination.getDestinationId())
                .destinationName(destination.getDestinationName())
                .coverPhoto(destination.getCoverPhoto())
                .destinationGuide(destination.getDestinationGuide())
                .mapPhoto(destination.getMapPhoto())
                .build();
    }
}
