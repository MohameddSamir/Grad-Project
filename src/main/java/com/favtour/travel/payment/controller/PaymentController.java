package com.favtour.travel.payment.controller;

import com.favtour.travel.core.payload.ApiResponse;
import com.favtour.travel.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/checkout/{orderId}")
    public ResponseEntity<ApiResponse<Map<String, String>>> checkout(@PathVariable int orderId) {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Payment Url is ready", Map.of("url",paymentService.createCheckoutSession(orderId))));
    }

}
