package com.favtour.travel.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
public class OrderRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String nationality;
    private String guideLanguage;
    private String specialRequirements;
    private int numberOfAdults;
    private int numberOfChildren;

    private LocalDate orderDate;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
