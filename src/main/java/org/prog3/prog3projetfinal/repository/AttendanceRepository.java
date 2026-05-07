package org.prog3.prog3projetfinal.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.prog3.prog3projetfinal.entity.ActivityMemberAttendance;
import org.prog3.prog3projetfinal.entity.AttendanceStatus;
import org.prog3.prog3projetfinal.entity.CreateActivityMemberAttendance;
import org.prog3.prog3projetfinal.entity.MemberDescription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AttendanceRepository {
    private final Connection connection;

    public List<ActivityMemberAttendance> findByMemberAndPeriod(String memberId, LocalDate from, LocalDate to) {
        List<ActivityMemberAttendance> attendanceList = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT id, member_id, attendance_status, activity_date " +
                        "FROM activity_attendance " +
                        "WHERE member_id = ? " +
                        "AND activity_date BETWEEN ? AND ?"
        )) {
            ps.setString(1, memberId);
            ps.setDate(2, java.sql.Date.valueOf(from));
            ps.setDate(3, java.sql.Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ActivityMemberAttendance attendance = ActivityMemberAttendance.builder()
                        .id(rs.getString("id"))
                        .attendanceStatus(AttendanceStatus.valueOf(rs.getString("attendance_status")))
                        .memberDescription(new MemberDescription(memberId, null, null, null, null))
                        .build();
                attendanceList.add(attendance);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return attendanceList;
    }

    public double calculateAssiduity(String memberId, LocalDate from, LocalDate to) {
        List<ActivityMemberAttendance> attendanceList = findByMemberAndPeriod(memberId, from, to);
        long totalActivities = attendanceList.size();
        long attended = attendanceList.stream()
                .filter(a -> a.getAttendanceStatus() == AttendanceStatus.ATTENDED)
                .count();

        return totalActivities > 0 ? (attended * 100.0 / totalActivities) : 0.0;
    }

    // 🔹 Calculer le taux d’assiduité global d’une collectivité
    public double calculateGlobalAssiduity(String collectivityId, LocalDate from, LocalDate to) {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT attendance_status " +
                        "FROM activity_attendance aa " +
                        "JOIN collectivity_activity ca ON aa.activity_id = ca.id " +
                        "WHERE ca.collectivity_id = ? " +
                        "AND ca.activity_date BETWEEN ? AND ?"
        )) {
            ps.setString(1, collectivityId);
            ps.setDate(2, java.sql.Date.valueOf(from));
            ps.setDate(3, java.sql.Date.valueOf(to));
            ResultSet rs = ps.executeQuery();

            int total = 0;
            int attended = 0;
            while (rs.next()) {
                total++;
                if ("ATTENDED".equals(rs.getString("attendance_status"))) {
                    attended++;
                }
            }
            return total > 0 ? (attended * 100.0 / total) : 0.0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ActivityMemberAttendance> saveAttendance(String activityId, List<CreateActivityMemberAttendance> attendanceList) {
        List<ActivityMemberAttendance> saved = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO activity_attendance (id, activity_id, member_id, attendance_status, activity_date) " +
                        "VALUES (?, ?, ?, ?, CURRENT_DATE)"
        )) {
            for (CreateActivityMemberAttendance a : attendanceList) {
                String id = java.util.UUID.randomUUID().toString();
                ps.setString(1, id);
                ps.setString(2, activityId);
                ps.setString(3, a.getMemberIdentifier());
                ps.setString(4, a.getAttendanceStatus().name());
                ps.addBatch();

                saved.add(ActivityMemberAttendance.builder()
                        .id(id)
                        .attendanceStatus(a.getAttendanceStatus())
                        .memberDescription(new MemberDescription(a.getMemberIdentifier(), null, null, null, null))
                        .build());
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return saved;
    }

    public List<ActivityMemberAttendance> findByActivityId(String activityId) {
        List<ActivityMemberAttendance> attendanceList = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT id, member_id, attendance_status, activity_date " +
                        "FROM activity_attendance WHERE activity_id = ?"
        )) {
            ps.setString(1, activityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                attendanceList.add(ActivityMemberAttendance.builder()
                        .id(rs.getString("id"))
                        .attendanceStatus(AttendanceStatus.valueOf(rs.getString("attendance_status")))
                        .memberDescription(new MemberDescription(rs.getString("member_id"), null, null, null, null))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return attendanceList;
    }
}
