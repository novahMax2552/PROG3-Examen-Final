package org.prog3.prog3projetfinal.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.prog3.prog3projetfinal.entity.CollectivityActivity;
import org.prog3.prog3projetfinal.controller.dto.CreateCollectivityActivity;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ActivityRepositoryImpl implements ActivityRepository {
    private final Connection connection;

    @Override
    public List<CollectivityActivity> findByCollectivityAndPeriod(String collectivityId, LocalDate from, LocalDate to) {
        List<CollectivityActivity> activities = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT id, collectivity_id, name, description, activity_type, activity_date " +
                        "FROM activity " +
                        "WHERE collectivity_id = ? AND activity_date BETWEEN ? AND ?"
        )) {
            ps.setString(1, collectivityId);
            ps.setDate(2, java.sql.Date.valueOf(from));
            ps.setDate(3, java.sql.Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                activities.add(CollectivityActivity.builder()
                        .id(rs.getString("id"))
                        .collectivityId(rs.getString("collectivity_id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .activityType(rs.getString("activity_type"))
                        .activityDate(rs.getDate("activity_date").toLocalDate())
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return activities;
    }

    @Override
    public List<CollectivityActivity> findByCollectivityId(String collectivityId) {
        List<CollectivityActivity> activities = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT id, collectivity_id, name, description, activity_type, activity_date " +
                        "FROM activity WHERE collectivity_id = ?"
        )) {
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                activities.add(CollectivityActivity.builder()
                        .id(rs.getString("id"))
                        .collectivityId(rs.getString("collectivity_id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .activityType(rs.getString("activity_type"))
                        .activityDate(rs.getDate("activity_date").toLocalDate())
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return activities;
    }

    @Override
    public List<CollectivityActivity> saveAll(String collectivityId, List<CreateCollectivityActivity> activities) {
        List<CollectivityActivity> saved = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO activity (id, collectivity_id, name, description, activity_type, activity_date) VALUES (?, ?, ?, ?, ?, ?)"
        )) {
            for (CreateCollectivityActivity act : activities) {
                ps.setString(1, act.getId());
                ps.setString(2, collectivityId);
                ps.setString(3, act.getName());
                ps.setString(4, act.getDescription());
                ps.setString(5, act.getActivityType());
                ps.setDate(6, java.sql.Date.valueOf(act.getActivityDate()));
                ps.addBatch();

                saved.add(CollectivityActivity.builder()
                        .id(act.getId())
                        .collectivityId(collectivityId)
                        .name(act.getName())
                        .description(act.getDescription())
                        .activityType(act.getActivityType())
                        .activityDate(act.getActivityDate())
                        .build());
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return saved;
    }
}
