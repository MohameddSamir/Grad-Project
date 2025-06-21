package com.favtour.travel.nileCruise.nileCruise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NileCruiseRequest {

    @NotBlank(message = "Label is required")
    private String label;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Price is required")
    private int price;

    @NotBlank(message = "Highlight is required")
    private String highlights;

    @NotNull(message = "Cabin Numbers are required")
    private int cabinNumbers;

    @NotNull(message = "Decks Number is required")
    private int decksNumber;
}
