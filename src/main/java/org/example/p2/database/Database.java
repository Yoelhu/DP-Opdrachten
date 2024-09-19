package org.example.p2.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    //Alle attributen voor het verbinden naar mijn localhost database
    private static final String URL = "jdbc:postgresql://localhost:5433/ovchip";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Yo20030620";

    private static Connection connection;

    private Database() {}

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return connection;
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }
}
