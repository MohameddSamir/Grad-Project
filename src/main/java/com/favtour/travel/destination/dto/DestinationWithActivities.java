package com.favtour.travel.destination.dto;

import com.favtour.travel.destination.entity.DestinationActivity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class DestinationWithActivities {

    private int destinationId;
    private String destinationName;
    private String coverPhoto;
    private String destinationGuide;
    private String mapPhoto;
    private List<DestinationActivity> destinationActivities;
}
