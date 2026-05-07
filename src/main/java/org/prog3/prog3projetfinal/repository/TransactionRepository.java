package org.prog3.prog3projetfinal.repository;

import org.prog3.prog3projetfinal.controller.dto.CollectivityTransaction;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository {
    List<CollectivityTransaction> findByCollectivityAndPeriod(String collectivityId, LocalDate from, LocalDate to);
}

