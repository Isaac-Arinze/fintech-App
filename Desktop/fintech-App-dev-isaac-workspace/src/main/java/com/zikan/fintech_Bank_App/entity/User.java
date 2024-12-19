package com.zikan.fintech_Bank_App.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "AppUser")
public class User implements UserDetails {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String otherName;
    private String gender;
    private String address;
//    @Column(columnDefinition = "boolean default false")
    @Column(name= "is_LoggedIn")
    private boolean isLoggedIn;
    private LocalDateTime lastLoginDate;
    private String stateOfOrigin;
    private String accountNumber;
    private BigDecimal accountBalance;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @OneToOne(mappedBy = "user")
    private ForgotPassword forgotPassword;
    @Column(name = "password", nullable = false)
    private String password;
    private String phoneNumber;
    private String alternativePhoneNumber;
    private String status;
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Column(name = "verification_token")
    private String verificationToken;

    @Column(name = "is_verified")
    private boolean isVerified;

    private Role role;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
