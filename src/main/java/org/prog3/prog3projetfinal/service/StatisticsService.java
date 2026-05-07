package org.prog3.prog3projetfinal.service;

import org.prog3.prog3projetfinal.Dao.StatisticsDao;
import org.prog3.prog3projetfinal.model.CollectivityLocalStatistics;
import org.prog3.prog3projetfinal.model.CollectivityOverallStatistics;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class StatisticsService {
    private final StatisticsDao dao;

    public StatisticsService(StatisticsDao dao) {
        this.dao = dao;
    }

    public List<CollectivityLocalStatistics> getLocalStatistics(String id, LocalDate from, LocalDate to) {
        try {
            return dao.getLocalStatistics(id, from, to);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching local statistics", e);
        }
    }

    public List<CollectivityOverallStatistics> getOverallStatistics(LocalDate from, LocalDate to) {
        try {
            return dao.getOverallStatistics(from, to);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching overall statistics", e);
        }
    }
}
