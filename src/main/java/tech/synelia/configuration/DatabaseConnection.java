package tech.synelia.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    public class DatabaseConnection {

        private static final String URL =
                "jdbc:postgresql://localhost:5432/jeu_capitales";

        private static final String USER = "souley";
        private static final String PASSWORD = "password";

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }


