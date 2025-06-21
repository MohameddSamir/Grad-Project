package com.favtour.travel.nileCruise.nileCruise.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NileCruiseCard {

    private int id;
    private String label;
    private String location;
    private int price;
    private String coverPhoto;
}
