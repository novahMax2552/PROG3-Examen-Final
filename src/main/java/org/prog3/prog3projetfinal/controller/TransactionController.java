package org.prog3.prog3projetfinal.controller;

import org.prog3.prog3projetfinal.model.CollectivityTransaction;
import org.prog3.prog3projetfinal.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/collectivities/{id}/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public List<CollectivityTransaction> getTransactions(@PathVariable("id") UUID collectivityId,
                                                         @RequestParam("from") String from,
                                                         @RequestParam("to") String to) throws SQLException {
        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);
        return service.getTransactions(collectivityId, fromDate, toDate);
    }
}

