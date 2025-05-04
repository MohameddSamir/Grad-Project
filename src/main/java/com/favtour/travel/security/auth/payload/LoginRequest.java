package com.favtour.travel.security.auth.payload;

import lombok.Getter;

@Getter
public class LoginRequest {

    private String email;
    private String password;
}
