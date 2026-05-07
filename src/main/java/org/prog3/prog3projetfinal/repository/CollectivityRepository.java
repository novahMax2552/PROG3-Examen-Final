package org.prog3.prog3projetfinal.repository;

import org.prog3.prog3projetfinal.entity.Collectivity;
import org.prog3.prog3projetfinal.mapper.CollectivityJdbcMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CollectivityRepository {
    private final Connection connection;
    private final CollectivityJdbcMapper collectivityJdbcMapper;

    public List<Collectivity> saveAll(List<Collectivity> collectivities) {
        List<Collectivity> savedList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO collectivity (id, name, number, location, president_id, vice_president_id, treasurer_id, secretary_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                        "ON CONFLICT (id) DO UPDATE SET " +
                        "name = excluded.name, " +
                        "number = excluded.number, " +
                        "location = excluded.location, " +
                        "president_id = excluded.president_id, " +
                        "vice_president_id = excluded.vice_president_id, " +
                        "treasurer_id = excluded.treasurer_id, " +
                        "secretary_id = excluded.secretary_id"
        )) {
            for (Collectivity collectivity : collectivities) {
                preparedStatement.setString(1, collectivity.getId());
                preparedStatement.setString(2, collectivity.getName());
                if (collectivity.getNumber() == null) {
                    preparedStatement.setNull(3, Types.INTEGER);
                } else {
                    preparedStatement.setInt(3, collectivity.getNumber());
                }
                preparedStatement.setString(4, collectivity.getLocation());
                preparedStatement.setString(5, collectivity.getCollectivityStructure().getPresident().getId());
                preparedStatement.setString(6, collectivity.getCollectivityStructure().getVicePresident().getId());
                preparedStatement.setString(7, collectivity.getCollectivityStructure().getTreasurer().getId());
                preparedStatement.setString(8, collectivity.getCollectivityStructure().getSecretary().getId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            for (Collectivity collectivity : collectivities) {
                savedList.add(findById(collectivity.getId()).orElseThrow());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return savedList;
    }

    public boolean isNumberExists(Integer number) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id FROM collectivity WHERE number = ?"
        )) {
            preparedStatement.setInt(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isNameExists(String name) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id FROM collectivity WHERE name = ?"
        )) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 🔹 Trouver par ID
    public Optional<Collectivity> findById(String id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, name, number, location, president_id, vice_president_id, treasurer_id, secretary_id " +
                        "FROM collectivity WHERE id = ?"
        )) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(collectivityJdbcMapper.mapFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    // 🔹 Trouver toutes les collectivités
    public List<Collectivity> findAll() {
        List<Collectivity> collectivities = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, name, number, location, president_id, vice_president_id, treasurer_id, secretary_id FROM collectivity"
        )) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                collectivities.add(collectivityJdbcMapper.mapFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return collectivities;
    }

    // 🔹 Trouver toutes les collectivités d’un membre
    public List<Collectivity> findAllByMemberId(String memberId) {
        List<Collectivity> collectivities = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT c.id, c.name, c.number, c.location, c.president_id, c.vice_president_id, c.treasurer_id, c.secretary_id " +
                        "FROM collectivity c " +
                        "JOIN collectivity_member cm ON c.id = cm.collectivity_id " +
                        "WHERE cm.member_id = ?"
        )) {
            preparedStatement.setString(1, memberId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                collectivities.add(collectivityJdbcMapper.mapFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return collectivities;
    }
}
