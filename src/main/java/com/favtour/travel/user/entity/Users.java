package com.favtour.travel.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String email;
    private String password;
    private String role;
    private boolean isActive;
    @OneToOne(targetEntity = UsersProfile.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile")
    private UsersProfile profile;
}
