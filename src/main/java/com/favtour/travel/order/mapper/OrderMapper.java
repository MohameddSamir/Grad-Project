package com.favtour.travel.order.mapper;

import com.favtour.travel.order.dto.OrderRequest;
import com.favtour.travel.order.dto.OrderResponse;
import com.favtour.travel.order.entity.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderMapper {

    public Order mapToOrder(OrderRequest orderRequest){
        return Order.builder()
                .firstName(orderRequest.getFirstName())
                .lastName(orderRequest.getLastName())
                .email(orderRequest.getEmail())
                .phone(orderRequest.getPhone())
                .nationality(orderRequest.getNationality())
                .guideLanguage(orderRequest.getGuideLanguage())
                .specialRequirements(orderRequest.getSpecialRequirements())
                .numberOfAdults(orderRequest.getNumberOfAdults())
                .numberOfChildren(orderRequest.getNumberOfChildren())
                .orderDate(orderRequest.getOrderDate())
                .checkInDate(orderRequest.getCheckInDate())
                .checkOutDate(orderRequest.getCheckOutDate())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public OrderResponse mapToOrderResponse(Order order){
        return OrderResponse.builder()
                .orderId(order.getId())
                .userId(order.getUser().getUserId())
                .firstName(order.getFirstName())
                .lastName(order.getLastName())
                .email(order.getEmail())
                .phone(order.getPhone())
                .nationality(order.getNationality())
                .guideLanguage(order.getGuideLanguage())
                .specialRequirements(order.getSpecialRequirements())
                .numberOfAdults(order.getNumberOfAdults())
                .numberOfChildren(order.getNumberOfChildren())
                .totalPrice(order.getTotalPrice())
                .checkInDate(order.getCheckInDate())
                .checkOutDate(order.getCheckOutDate())
                .orderName(order.getOrderName())
                .orderType(order.getOrderType())
                .orderCoverPhoto(order.getOrderCoverPhoto())
                .orderStatus(order.getOrderStatus())
                .referenceEntityId(order.getReferenceEntityId())
                .hotelId(order.getHotelId())
                .build();
    }
}
