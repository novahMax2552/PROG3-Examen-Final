package org.prog3.prog3projetfinal.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MemberPayment {
    private String id;
    private double amount;
    private String paymentMode;
    private String accountCreditedId;
    private String creationDate;
}
