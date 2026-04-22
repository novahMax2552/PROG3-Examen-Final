package org.prog3.prog3projetfinal.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/federation_agricultural";
    private static final String USER = "postgres";       // ⚠️ Mets ton vrai user
    private static final String PASSWORD = "Novah Anusha"; // ⚠️ Mets ton vrai mot de passe

    static {
        try {
            // Charger le driver PostgreSQL
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL Driver not found!", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

