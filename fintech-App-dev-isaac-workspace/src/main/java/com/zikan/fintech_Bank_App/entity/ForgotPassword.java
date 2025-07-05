package com.zikan.fintech_Bank_App.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class ForgotPassword {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    private Integer fpid;
    @Column(nullable = false)
    private Integer otp;
    @Column(nullable = false)
    private Date expirationTime;
    @OneToOne
    private User user;
}
