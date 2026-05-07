package org.prog3.prog3projetfinal.service;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class StatisticsDao {

    public List<CollectivityLocalStatistics> getLocalStatistics(String collectivityId, LocalDate from, LocalDate to) throws SQLException {
        List<CollectivityLocalStatistics> stats = new ArrayList<>();

        String sql = """
            SELECT m.id, m.first_name, m.last_name,
                   COALESCE(SUM(p.amount),0) AS earnedAmount,
                   COALESCE(SUM(f.amount),0) - COALESCE(SUM(p.amount),0) AS unpaidAmount
            FROM member m
            JOIN membership_fee f ON f.collectivity_id = m.collectivity_id AND f.status = 'ACTIVE'
            LEFT JOIN payment p ON p.member_id = m.id
                                AND p.creation_date BETWEEN ? AND ?
            WHERE m.collectivity_id = ?
            GROUP BY m.id, m.first_name, m.last_name
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, from);
            ps.setObject(2, to);
            ps.setString(3, collectivityId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CollectivityLocalStatistics stat = new CollectivityLocalStatistics();
                stat.setMemberDescription(new MemberDescription(
                        rs.getString("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        null,
                        null
                ));
                stat.setEarnedAmount(rs.getDouble("earnedAmount"));
                stat.setUnpaidAmount(rs.getDouble("unpaidAmount"));
                stats.add(stat);
            }
        }
        return stats;
    }

    public List<CollectivityOverallStatistics> getOverallStatistics(LocalDate from, LocalDate to) throws SQLException {
        List<CollectivityOverallStatistics> stats = new ArrayList<>();

        String sql = """
            SELECT c.id, c.name, c.number,
                   COUNT(DISTINCT CASE WHEN p.id IS NOT NULL THEN m.id END) * 100.0 / COUNT(DISTINCT m.id) AS overallMemberCurrentDuePercentage,
                   COUNT(DISTINCT CASE WHEN m.date_adhesion BETWEEN ? AND ? THEN m.id END) AS newMembersNumber
            FROM collectivity c
            JOIN member m ON m.collectivity_id = c.id
            LEFT JOIN payment p ON p.member_id = m.id
                                AND p.creation_date BETWEEN ? AND ?
            GROUP BY c.id, c.name, c.number
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, from);
            ps.setObject(2, to);
            ps.setObject(3, from);
            ps.setObject(4, to);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CollectivityOverallStatistics stat = new CollectivityOverallStatistics();
                stat.setCollectivityInformation(new CollectivityInformation(
                        rs.getString("name"),
                        rs.getInt("number")
                ));
                stat.setOverallMemberCurrentDuePercentage(rs.getDouble("overallMemberCurrentDuePercentage"));
                stat.setNewMembersNumber(rs.getInt("newMembersNumber"));
                stats.add(stat);
            }
        }
        return stats;
    }
}

