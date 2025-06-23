package com.favtour.travel.payment.service;

import com.favtour.travel.booking.entity.BookingStatus;
import com.favtour.travel.booking.entity.TripBooking;
import com.favtour.travel.booking.repository.TripBookingRepository;
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

    private final TripBookingRepository tripBookingRepository;

    @Value("${payment.secret_key}")
    private String paymentSecretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = paymentSecretKey;
    }

    public String createCheckoutSession(int bookingId){

        TripBooking tripBooking= tripBookingRepository.findById(bookingId)
                .orElseThrow(() ->new EntityNotFoundException("Trip Booking not found"));

        if(tripBooking.getBookingStatus() == BookingStatus.APPROVED){
            throw new IllegalArgumentException("Booking is already approved");
        }

        long amount= (long) tripBooking.getTotalPrice() *100;

        SessionCreateParams.LineItem.PriceData.ProductData productData=
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(tripBooking.getTrip().getLabel())
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
