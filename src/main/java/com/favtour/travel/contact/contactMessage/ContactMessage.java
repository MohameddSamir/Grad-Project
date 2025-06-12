package com.favtour.travel.contact.contactMessage;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "contact_message")
@Data
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Full Name is required")
    private String fullName;

    @NotBlank(message = "Email Address is required")
    private String emailAddress;

    @NotBlank(message = "Mobile Phone number is required")
    private String mobile;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "Please provide your message")
    private String message;
}
