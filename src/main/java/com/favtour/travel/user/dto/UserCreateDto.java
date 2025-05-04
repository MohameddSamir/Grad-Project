package com.favtour.travel.user.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserCreateDto {
    @Column(unique = true)
    @NotEmpty(message = "Email is required")
    private String email;
    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password should be 8 characters at least")
    private String password;
    private String role;
    private String isActive;
    @NotEmpty(message = "First Name is required")
    private String firstName;
    @NotEmpty(message = "Last Name is required")
    private String lastName;
    @Pattern(regexp = "^\\+?[0-9\\s-]+$")
    private String phone;
    private String nationality;
}
