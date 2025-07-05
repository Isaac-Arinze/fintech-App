    package com.zikan.fintech_Bank_App.entity;
    
    import io.swagger.v3.oas.annotations.tags.Tag;
    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import lombok.*;
    import org.hibernate.annotations.CreationTimestamp;
    import org.hibernate.annotations.UpdateTimestamp;
    
    import java.math.BigDecimal;
    import java.time.LocalDate;
    
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    @Getter
    @Setter
    @Builder
    @Tag(name = "transactions")
    
    public class Transaction {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
    
        private String transactionId;
        private String transactionType;
        private BigDecimal amount;
        private String accountNumber;
        private String status;
        @CreationTimestamp
        private LocalDate createdAt;
        @UpdateTimestamp
        private LocalDate modifiedAt;
    }
