package com.zikan.fintech_Bank_App.repository;

import com.zikan.fintech_Bank_App.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
