package com.favtour.travel.booking.entity;

import com.favtour.travel.trip.entity.Trip;
import com.favtour.travel.user.entity.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "trip_booking")
@Setter
@Getter
public class TripBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @NotEmpty(message = "First Name is required")
    private String firstName;
    @NotEmpty(message = "Last Name is required")
    private String lastName;
    @NotEmpty(message = "Email is required")
    private String email;
    @NotEmpty(message = "Phone number is required")
    private String phone;
    @NotEmpty(message = "Your nationality is required")
    private String nationality;
    private String hotelName;
    private String guideLanguage;
    @Column(columnDefinition = "TEXT")
    private String specialRequirements;

    private LocalDateTime createdAt= LocalDateTime.now();

    @NotNull(message = "You must choose a date for booking")
    private LocalDate bookingDate;

    @Min(value = 1, message = "Number of adults should be one at least")
    private int numberOfAdults;
    private int numberOfChildren;
    private int totalPrice;
    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status")
    private BookingStatus bookingStatus= BookingStatus.PENDING;
}
