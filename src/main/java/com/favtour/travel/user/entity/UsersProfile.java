package com.favtour.travel.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_profile")
@Data
@NoArgsConstructor
public class UsersProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userProfileId;
    private String firstName;
    private String lastName;
    private String phone;
    private String nationality;

}
