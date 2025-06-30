package com.favtour.travel.order.controller;

import com.favtour.travel.core.payload.ApiResponse;
import com.favtour.travel.order.dto.OrderRequest;
import com.favtour.travel.order.dto.OrderResponse;
import com.favtour.travel.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/user/orders")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Your orders are ready", orderService.getUserOrders()));
    }

    @GetMapping("/user/orders/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrder(@PathVariable int orderId) {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Your order is ready", orderService.getOrderById(orderId)));
    }

    @PostMapping("/trips/{tripId}/order")
    public ResponseEntity<ApiResponse<OrderResponse>> createTripOrder(@PathVariable int tripId,
                                                                  @RequestBody OrderRequest orderRequest) {

        return ResponseEntity.ok
                (new ApiResponse<>(true, "Order has been created successfully", orderService.createTripOrder(tripId, orderRequest)));
    }

    @PostMapping("rooms/{roomId}/order")
    public ResponseEntity<ApiResponse<OrderResponse>> createHotelOrder(@PathVariable int roomId,
                                                                       @RequestBody OrderRequest orderRequest) {

        return ResponseEntity.ok
                (new ApiResponse<>(true, "Hotel order has been created successfully", orderService.createHotelOrder(roomId, orderRequest)));
    }

    @PostMapping("nile-cruises/{cruiseId}/order")
    public ResponseEntity<ApiResponse<OrderResponse>> createNileCruiseOrder(@PathVariable int cruiseId,
                                                                            @RequestBody OrderRequest orderRequest) {

        return ResponseEntity.ok
                (new ApiResponse<>(true, "Cruise order has been created successfully", orderService.createNileCruiseOrder(cruiseId, orderRequest)));
    }

    @PutMapping("/orders/{orderId}/cancel")
    public ResponseEntity<ApiResponse<OrderResponse>> cancelOrder(@PathVariable int orderId) {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "You have canceled the order", orderService.cancelOrder(orderId)));
    }
}
