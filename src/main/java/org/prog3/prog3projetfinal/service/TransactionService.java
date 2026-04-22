package org.prog3.prog3projetfinal.service;

import org.prog3.prog3projetfinal.Dao.TransactionDao;
import org.prog3.prog3projetfinal.model.CollectivityTransaction;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionDao dao;

    public TransactionService(TransactionDao dao) {
        this.dao = dao;
    }

    public List<CollectivityTransaction> getTransactions(UUID collectivityId, LocalDate from, LocalDate to) throws SQLException {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("From date must be before To date");
        }
        return dao.findByCollectivityIdAndPeriod(collectivityId.toString(), from, to);
    }
}

