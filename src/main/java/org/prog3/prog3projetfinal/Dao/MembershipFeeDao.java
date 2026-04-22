package org.prog3.prog3projetfinal.Dao;

import org.prog3.prog3projetfinal.model.MembershipFee;
import org.prog3.prog3projetfinal.model.enums.Frequency;
import org.prog3.prog3projetfinal.model.enums.ActivityStatus;
import org.prog3.prog3projetfinal.util.DBConnection;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class MembershipFeeDao {

    public List<MembershipFee> findByCollectivityId(UUID collectivityId) throws SQLException {
        List<MembershipFee> fees = new ArrayList<>();

        String sql = "SELECT id, eligible_from, frequency, amount, label, status " +
                "FROM membership_fee WHERE collectivity_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, collectivityId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MembershipFee fee = new MembershipFee();
                    fee.setId(rs.getString("id"));
                    fee.setEligibleFrom(rs.getDate("eligible_from").toLocalDate());
                    fee.setFrequency(Frequency.valueOf(rs.getString("frequency")));
                    fee.setAmount(rs.getDouble("amount"));
                    fee.setLabel(rs.getString("label"));
                    fee.setStatus(ActivityStatus.valueOf(rs.getString("status")));

                    fees.add(fee);
                }
            }
        }
        return fees;
    }

    public List<MembershipFee> createFees(UUID collectivityId, List<MembershipFee> fees) throws SQLException {
        String sql = "INSERT INTO membership_fee " +
                "(id, collectivity_id, eligible_from, frequency, amount, label, status) " +
                "VALUES (?, ?, ?, ?::frequency, ?, ?, ?::activity_status)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (MembershipFee fee : fees) {
                stmt.setObject(1, UUID.fromString(fee.getId()));
                stmt.setObject(2, collectivityId);
                stmt.setDate(3, Date.valueOf(fee.getEligibleFrom()));
                stmt.setString(4, fee.getFrequency().name());
                stmt.setDouble(5, fee.getAmount());
                stmt.setString(6, fee.getLabel());
                stmt.setString(7, fee.getStatus().name());

                stmt.addBatch();
            }
            stmt.executeBatch();
        }
        return fees;
    }
}
