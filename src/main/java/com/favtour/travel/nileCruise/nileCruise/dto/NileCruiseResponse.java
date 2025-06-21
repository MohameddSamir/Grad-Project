package com.favtour.travel.nileCruise.nileCruise.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NileCruiseResponse {

    private int id;
    private String label;
    private String location;
    private int price;
    private String highlights;
    private int cabinNumbers;
    private int decksNumber;
}
