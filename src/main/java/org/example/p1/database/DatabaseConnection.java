package org.example.p1.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    //Alle attributen voor het verbinden naar mijn localhost database
    private static final String URL = "jdbc:postgresql://localhost:5433/ovchip";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Yo20030620";

    private static Connection connection;

    private DatabaseConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
}
