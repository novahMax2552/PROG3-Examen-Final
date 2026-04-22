package org.prog3.prog3projetfinal.Dao;

import org.prog3.prog3projetfinal.model.CollectivityTransaction;
import org.prog3.prog3projetfinal.model.PaymentMode;
import org.prog3.prog3projetfinal.model.Member;
import org.prog3.prog3projetfinal.model.FinancialAccount;
import org.prog3.prog3projetfinal.util.DBConnection;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionDao {

    public List<CollectivityTransaction> findByCollectivityIdAndPeriod(
            String collectivityId, LocalDate from, LocalDate to) throws SQLException {

        List<CollectivityTransaction> transactions = new ArrayList<>();

        String sql = "SELECT id, creation_date, amount, payment_mode, account_id, member_id " +
                "FROM collectivity_transaction " +
                "WHERE collectivity_id = ? AND creation_date BETWEEN ? AND ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, java.util.UUID.fromString(collectivityId)); // UUID
            stmt.setDate(2, Date.valueOf(from));
            stmt.setDate(3, Date.valueOf(to));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CollectivityTransaction tx = new CollectivityTransaction();
                    tx.setId(rs.getString("id"));
                    tx.setCreationDate(rs.getDate("creation_date").toLocalDate());
                    tx.setAmount(rs.getDouble("amount"));

                    // PostgreSQL ENUM → Java Enum
                    tx.setPaymentMode(PaymentMode.valueOf(rs.getString("payment_mode")));

                    // TODO: charger FinancialAccount et Member via leurs DAO
                    FinancialAccount account = null;
                    Member member = null;

                    tx.setAccountCredited(account);
                    tx.setMemberDebited(member);

                    transactions.add(tx);
                }
            }
        }
        return transactions;
    }
}
