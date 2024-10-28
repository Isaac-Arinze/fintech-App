package com.zikan.fintech_Bank_App.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfo {
    @Schema(
            name = "user account name"
    )

    private String accountName;
    @Schema(
            name = "user account Balance"
    )
    private BigDecimal accountBalance;
    @Schema(
            name = "user account number"
    )
    private String accountNumber;

}
