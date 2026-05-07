package org.prog3.prog3projetfinal.Dao;

import org.prog3.prog3projetfinal.model.CreateMember;
import org.prog3.prog3projetfinal.model.Member;
import org.prog3.prog3projetfinal.model.enums.MemberOccupation;
import org.prog3.prog3projetfinal.util.DBConnection;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

@Repository
public class MemberDao {

    public Member save(CreateMember cm, List<Member> referees) throws SQLException {
        Member newMember = new Member();
        newMember.setId(UUID.randomUUID().toString());
        newMember.setFirstName(cm.getFirstName());
        newMember.setLastName(cm.getLastName());
        newMember.setBirthDate(cm.getBirthDate());
        newMember.setAddress(cm.getAddress());
        newMember.setGender(cm.getGender());
        newMember.setProfession(cm.getProfession());
        newMember.setPhoneNumber(cm.getPhoneNumber());
        newMember.setEmail(cm.getEmail());
        newMember.setOccupation(cm.getOccupation());
        newMember.setCollectivityId(cm.getCollectivityIdentifier());
        newMember.setReferees(referees);
        newMember.setDateAdhesion(LocalDate.now());

        String sql = "INSERT INTO member " +
                "(id, first_name, last_name, birth_date, address, gender, profession, phone_number, email, occupation, collectivity_id, date_adhesion) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newMember.getId());
            ps.setString(2, newMember.getFirstName());
            ps.setString(3, newMember.getLastName());
            ps.setObject(4, newMember.getBirthDate());
            ps.setString(5, newMember.getAddress());
            ps.setString(6, newMember.getGender().name());
            ps.setString(7, newMember.getProfession());
            ps.setString(8, newMember.getPhoneNumber());
            ps.setString(9, newMember.getEmail());
            ps.setString(10, newMember.getOccupation().name());
            ps.setString(11, newMember.getCollectivityId());
            ps.setObject(12, newMember.getDateAdhesion());
            ps.executeUpdate();
        }

        return newMember;
    }

    public List<Member> findMembersByIds(List<String> ids) throws SQLException {
        List<Member> members = new ArrayList<>();

        if (ids == null || ids.isEmpty()) {
            return members;
        }

        String placeholders = String.join(",", Collections.nCopies(ids.size(), "?"));
        String sql = "SELECT id, first_name, last_name, occupation, collectivity_id, date_adhesion " +
                "FROM member WHERE id IN (" + placeholders + ")";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < ids.size(); i++) {
                ps.setString(i + 1, ids.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member m = new Member();
                m.setId(rs.getString("id"));
                m.setFirstName(rs.getString("first_name"));
                m.setLastName(rs.getString("last_name"));
                m.setOccupation(Enum.valueOf(MemberOccupation.class, rs.getString("occupation")));
                m.setCollectivityId(rs.getString("collectivity_id"));
                m.setDateAdhesion(rs.getDate("date_adhesion").toLocalDate());
                members.add(m);
            }
        }
        return members;
    }
    public Optional<Member> findById(String id) throws SQLException {
        String sql = "SELECT * FROM member WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Member m = new Member();
                m.setId(rs.getString("id"));
                m.setFirstName(rs.getString("first_name"));
                m.setLastName(rs.getString("last_name"));
                m.setOccupation(Enum.valueOf(MemberOccupation.class, rs.getString("occupation")));
                m.setCollectivityId(rs.getString("collectivity_id"));
                m.setDateAdhesion(rs.getDate("date_adhesion").toLocalDate());
                return Optional.of(m);
            }
        }
        return Optional.empty();
    }
}
