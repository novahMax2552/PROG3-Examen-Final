package org.prog3.prog3projetfinal.controller.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class BankAccount extends FinancialAccount {
    private String bankName;
    private String accountNumber;
}

