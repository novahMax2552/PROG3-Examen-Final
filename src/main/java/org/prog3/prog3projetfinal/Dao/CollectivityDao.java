package org.prog3.prog3projetfinal.Dao;

import org.prog3.prog3projetfinal.config.DBConnection;
import org.prog3.prog3projetfinal.model.Collectivity;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CollectivityDao {

    public Optional<Collectivity> findById(String id) {
        String sql = "SELECT * FROM collectivity WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, UUID.fromString(id));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Collectivity c = new Collectivity();
                c.setId(rs.getString("id"));
                c.setNumber(rs.getInt("number"));
                c.setName(rs.getString("name"));
                c.setLocation(rs.getString("location"));
                return Optional.of(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public boolean existsByNumber(int number) {
        String sql = "SELECT COUNT(*) FROM collectivity WHERE number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, number);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean existsByName(String name) {
        String sql = "SELECT COUNT(*) FROM collectivity WHERE name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public Collectivity updateIdentity(String id, int number, String name) {
        String sql = "UPDATE collectivity SET number=?, name=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, number);
            stmt.setString(2, name);
            stmt.setObject(3, UUID.fromString(id));
            stmt.executeUpdate();

            Collectivity c = new Collectivity();
            c.setId(id);
            c.setNumber(number);
            c.setName(name);
            return c;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

