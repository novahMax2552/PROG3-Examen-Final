package org.prog3.prog3projetfinal.Dao;

import org.prog3.prog3projetfinal.util.DBConnection;
import org.prog3.prog3projetfinal.model.CreateMember;
import org.prog3.prog3projetfinal.model.Member;
import org.prog3.prog3projetfinal.model.enums.MemberOccupation;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class MemberDao {

    public List<Member> findMembersByIds(List<String> ids) {
        List<Member> result = new ArrayList<>();
        String sql = "SELECT * FROM member WHERE id = ?";
        try (Connection conn = DBConnection.getConnection()) {
            for (String id : ids) {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setObject(1, UUID.fromString(id));
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        Member m = new Member();
                        m.setId(rs.getString("id"));
                        m.setOccupation(MemberOccupation.valueOf(rs.getString("occupation")));
                        m.setCollectivityId(rs.getString("collectivity_id"));
                        m.setDateAdhesion(rs.getDate("date_adhesion").toLocalDate());
                        result.add(m);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public Member save(CreateMember cm, List<Member> referees) {
        String sql = "INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, occupation, collectivity_id, date_adhesion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String id = UUID.randomUUID().toString();

            stmt.setObject(1, UUID.fromString(id));
            stmt.setString(2, cm.getFirstName());
            stmt.setString(3, cm.getLastName());
            stmt.setDate(4, Date.valueOf(cm.getBirthDate()));
            stmt.setString(5, cm.getGender().name());
            stmt.setString(6, cm.getAddress());
            stmt.setString(7, cm.getProfession());
            stmt.setInt(8, cm.getPhoneNumber());
            stmt.setString(9, cm.getEmail());
            stmt.setString(10, cm.getOccupation().name());
            stmt.setObject(11, UUID.fromString(cm.getCollectivityIdentifier()));
            stmt.setDate(12, Date.valueOf(LocalDate.now()));
            stmt.executeUpdate();

            Member newMember = new Member();
            newMember.setId(id);
            newMember.setFirstName(cm.getFirstName());
            newMember.setLastName(cm.getLastName());
            newMember.setOccupation(cm.getOccupation());
            newMember.setReferees(referees);
            return newMember;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
