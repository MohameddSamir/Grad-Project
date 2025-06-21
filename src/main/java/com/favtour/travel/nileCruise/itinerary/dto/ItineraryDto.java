package com.favtour.travel.nileCruise.itinerary.dto;

import com.favtour.travel.nileCruise.itinerary.entity.ItineraryDuration;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ItineraryDto {

    @NotBlank(message = "Label is required")
    private String label;

    @NotEmpty(message = "You must provide at least one duration")
    private List<ItineraryDuration> durations;
}
