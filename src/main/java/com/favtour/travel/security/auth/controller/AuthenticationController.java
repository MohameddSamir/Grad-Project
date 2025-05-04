package com.favtour.travel.security.auth.controller;

import com.favtour.travel.common.ApiResponse;
import com.favtour.travel.security.auth.jwt.JwtAuthenticationService;
import com.favtour.travel.security.auth.payload.JwtResponse;
import com.favtour.travel.security.auth.payload.LoginRequest;
import com.favtour.travel.user.dto.UserCreateDto;
import com.favtour.travel.user.dto.UserDto;
import com.favtour.travel.user.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtAuthenticationService jwtAuthenticationService;
    private final UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(@Valid @RequestBody UserCreateDto user){
        return ResponseEntity.ok()
                .body(new ApiResponse<>(true, "You have been registered successfully", usersService.save(user)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(@RequestBody LoginRequest loginRequest){
       return ResponseEntity.ok().
               body(new ApiResponse<>(true, "Successful Login", jwtAuthenticationService.authenticate(loginRequest)));
    }
}
