package com.favtour.travel.user.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserCreateDto {

    private String email;
    private String password;
    private String role;
    private String isActive;
    private String firstName;
    private String lastName;
    private String phone;
    private String nationality;
}
