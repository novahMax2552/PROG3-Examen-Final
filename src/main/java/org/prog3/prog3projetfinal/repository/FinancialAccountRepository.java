package org.prog3.prog3projetfinal.repository;

import org.prog3.prog3projetfinal.controller.dto.FinancialAccount;
import java.time.LocalDate;
import java.util.List;

public interface FinancialAccountRepository {
    List<FinancialAccount> findByCollectivityAndDate(String collectivityId, LocalDate date);
}
