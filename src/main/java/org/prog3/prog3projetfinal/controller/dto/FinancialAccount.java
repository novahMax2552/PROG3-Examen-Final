package org.prog3.prog3projetfinal.controller.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancialAccount {
    private String id;
    private String type;
    private String holderName;
    private double amount;
}


