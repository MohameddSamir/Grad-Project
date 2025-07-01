package com.favtour.travel.order.entity;

import com.favtour.travel.user.entity.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    private String orderName;
    @Enumerated(EnumType.STRING)
    private OrderType orderType;
    private LocalDate orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private String orderCoverPhoto;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private int referenceEntityId;

    private Integer hotelId;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String nationality;
    private String guideLanguage;
    @Column(columnDefinition = "TEXT")
    private String specialRequirements;
    private int numberOfAdults;
    private int numberOfChildren;
    private int totalPrice;

    private LocalDateTime createdAt;


}
