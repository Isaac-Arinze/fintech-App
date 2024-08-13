package com.zikan.fintech_Bank_App.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.NaturalIdCache;

@Data
@AllArgsConstructor
@NaturalIdCache
public class LoginDto {
    private String email;
    private  String password;
}
