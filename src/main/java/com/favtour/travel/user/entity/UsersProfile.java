package com.favtour.travel.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Table(name = "users_profile")
@Data
@NoArgsConstructor
public class UsersProfile {

    @Id
    private int userProfileId;
    @OneToOne
    @JoinColumn(name = "user_profile_id")
    @MapsId
    private Users user;
    @NotEmpty(message = "First Name is required")
    private String firstName;
    @NotEmpty(message = "Last Name is required")
    private String lastName;
    @Pattern(regexp = "^\\+?[0-9\\s-]+$")
    private String phone;
    private String nationality;

    public UsersProfile(Users users) {
        this.user=users;
    }
}
