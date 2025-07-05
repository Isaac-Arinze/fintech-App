package com.zikan.fintech_Bank_App.repository;

import com.zikan.fintech_Bank_App.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("SELECT t FROM Token t WHERE t.user.id = :userId AND t.loggedOut = false")
    List<Token> findAllTokenByUser(Integer userId);

    Optional<Token> findByToken(String token);
}
