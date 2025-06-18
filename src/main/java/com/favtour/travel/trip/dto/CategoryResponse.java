package com.favtour.travel.trip.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryResponse {

    private int categoryId;

    private String categoryName;
}
