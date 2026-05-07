package org.prog3.prog3projetfinal.repository;

import lombok.RequiredArgsConstructor;
import org.prog3.prog3projetfinal.controller.dto.Member;
import org.prog3.prog3projetfinal.controller.dto.PaymentMode;
import org.springframework.stereotype.Repository;
import org.prog3.prog3projetfinal.controller.dto.CollectivityTransaction;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {
    private final Connection connection;

    @Override
    public List<CollectivityTransaction> findByCollectivityAndPeriod(String collectivityId, LocalDate from, LocalDate to) {
        List<CollectivityTransaction> transactions = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT id, creation_date, amount, payment_mode, account_credited_id, member_debited_id " +
                        "FROM collectivity_transaction " +
                        "WHERE collectivity_id = ? AND creation_date BETWEEN ? AND ?"
        )) {
            ps.setString(1, collectivityId);
            ps.setDate(2, java.sql.Date.valueOf(from));
            ps.setDate(3, java.sql.Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                transactions.add(CollectivityTransaction.builder()
                        .id(rs.getString("id"))
                        .creationDate(rs.getDate("creation_date").toLocalDate())
                        .amount(rs.getDouble("amount"))
                        .paymentMode(PaymentMode.valueOf(rs.getString("payment_mode")))
                        .accountCredited(null) // à mapper si besoin
                        .memberDebitedId(Member.builder()
                                .id(rs.getString("member_debited_id"))
                                .build()) // ✅ ajout du build()
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }
}
