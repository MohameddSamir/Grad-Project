package com.favtour.travel.payment.service;

import com.favtour.travel.order.entity.Order;
import com.favtour.travel.order.entity.OrderStatus;
import com.favtour.travel.order.repository.OrderRepository;
import com.favtour.travel.shared.EntityNotFoundException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;

    @Value("${payment.secret_key}")
    private String paymentSecretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = paymentSecretKey;
    }

    public String createCheckoutSession(int orderId){

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if(order.getOrderStatus() == OrderStatus.APPROVED){
            throw new IllegalArgumentException("Booking is already approved");
        }

        long amount= (long) order.getTotalPrice() *100;

        SessionCreateParams.LineItem.PriceData.ProductData productData=
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(order.getOrderName())
                        .build();

        SessionCreateParams.LineItem.PriceData priceData=
                SessionCreateParams.LineItem.PriceData.builder()
                        .setUnitAmount(amount)
                        .setCurrency("USD")
                        .setProductData(productData)
                        .build();

        SessionCreateParams.LineItem lineItem=
                SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(priceData)
                        .build();

        SessionCreateParams params=
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:8080/success")
                        .setCancelUrl("http://localhost:8080/cancel")
                        .addLineItem(lineItem)
                        .build();
        try {
            Session session=Session.create(params);
            return session.getUrl();
        } catch (StripeException e) {
            throw new RuntimeException("Error creating session", e);
        }
    }
}
