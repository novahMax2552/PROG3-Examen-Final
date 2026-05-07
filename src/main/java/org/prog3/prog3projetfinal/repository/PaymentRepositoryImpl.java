package org.prog3.prog3projetfinal.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.prog3.prog3projetfinal.entity.MemberPayment;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {
    private final Connection connection;

    @Override
    public List<MemberPayment> findByMemberAndPeriod(String memberId, LocalDate from, LocalDate to) {
        List<MemberPayment> payments = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT id, amount, payment_mode, account_credited_id, creation_date " +
                        "FROM member_payment WHERE member_id = ? AND creation_date BETWEEN ? AND ?"
        )) {
            ps.setString(1, memberId);
            ps.setDate(2, java.sql.Date.valueOf(from));
            ps.setDate(3, java.sql.Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                payments.add(MemberPayment.builder()
                        .id(rs.getString("id"))
                        .amount(rs.getDouble("amount"))
                        .paymentMode(rs.getString("payment_mode"))
                        .accountCreditedId(rs.getString("account_credited_id"))
                        .creationDate(rs.getString("creation_date"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return payments;
    }

    @Override
    public boolean isMemberUpToDate(String memberId, LocalDate from, LocalDate to) {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT SUM(amount) AS total_paid " +
                        "FROM member_payment WHERE member_id = ? AND creation_date BETWEEN ? AND ?"
        )) {
            ps.setString(1, memberId);
            ps.setDate(2, java.sql.Date.valueOf(from));
            ps.setDate(3, java.sql.Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double totalPaid = rs.getDouble("total_paid");
                return totalPaid > 0; // simplifié : à adapter selon tes règles métier
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
