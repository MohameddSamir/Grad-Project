package com.favtour.travel.security.controller;

import com.favtour.travel.security.jwt.JwtAuthenticationService;
import com.favtour.travel.security.payload.JwtResponse;
import com.favtour.travel.security.payload.LoginRequest;
import com.favtour.travel.user.dto.UserCreateDto;
import com.favtour.travel.user.dto.UserDto;
import com.favtour.travel.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favtour")
public class AuthenticationController {

    private final JwtAuthenticationService jwtAuthenticationService;
    private final UsersService usersService;

    @PostMapping("/register")
    public UserDto register(@RequestBody UserCreateDto user){
        return usersService.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest){
       return ResponseEntity.ok(jwtAuthenticationService.authenticate(loginRequest));
    }
}
