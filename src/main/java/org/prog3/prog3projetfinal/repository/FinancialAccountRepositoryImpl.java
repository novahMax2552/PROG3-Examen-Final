package org.prog3.prog3projetfinal.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.prog3.prog3projetfinal.controller.dto.FinancialAccount;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FinancialAccountRepositoryImpl implements FinancialAccountRepository {
    private final Connection connection;

    @Override
    public List<FinancialAccount> findByCollectivityAndDate(String collectivityId, LocalDate date) {
        List<FinancialAccount> accounts = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT id, account_type, holder_name, balance " +
                        "FROM financial_account WHERE collectivity_id = ?"
        )) {
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                accounts.add(FinancialAccount.builder()
                        .id(rs.getString("id"))
                        .type(rs.getString("account_type"))
                        .holderName(rs.getString("holder_name"))
                        .amount(rs.getDouble("balance"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return accounts;
    }
}

