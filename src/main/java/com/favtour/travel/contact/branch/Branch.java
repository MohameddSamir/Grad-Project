package com.favtour.travel.contact.branch;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "branch")
@Data
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String location;
    private String email;
    private String phone;

    private String coverPhoto;
}
