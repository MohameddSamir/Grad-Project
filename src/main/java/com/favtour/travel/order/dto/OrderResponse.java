package com.favtour.travel.order.dto;

import com.favtour.travel.order.entity.OrderStatus;
import com.favtour.travel.order.entity.OrderType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
@Builder
public class OrderResponse {

    private int orderId;
    private int userId;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String nationality;
    private String guideLanguage;
    private String specialRequirements;
    private int numberOfAdults;
    private int numberOfChildren;
    private int totalPrice;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private String orderName;
    private OrderType orderType;
    private OrderStatus orderStatus;
    private String orderCoverPhoto;
    private int referenceEntityId;

}
