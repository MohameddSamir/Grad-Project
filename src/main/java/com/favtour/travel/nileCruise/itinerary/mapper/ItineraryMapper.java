package com.favtour.travel.nileCruise.itinerary.mapper;

import com.favtour.travel.nileCruise.itinerary.dto.ItineraryDto;
import com.favtour.travel.nileCruise.itinerary.entity.Itinerary;
import org.springframework.stereotype.Component;

@Component
public class ItineraryMapper {

    public Itinerary mapToItinerary(ItineraryDto itineraryDto) {

        return Itinerary.builder()
                .label(itineraryDto.getLabel())
                .durations(itineraryDto.getDurations())
                .build();
    }
}
