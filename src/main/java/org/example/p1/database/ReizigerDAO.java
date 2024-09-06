package org.example.p1.database;

import org.example.p1.domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAO {
    private final Connection connection;

    //Ik Verkrijg de connectie met de database via de "getConnection" method en assign het daarna aan de private connection variabel
    public ReizigerDAO(){
        connection = DatabaseConnection.getConnection();
    }

    public List<Reiziger> getAllReizigers() throws SQLException {
        //SQL Query
        String query = "SELECT * FROM reiziger";

        //Ik maak een nieuwe List van reizigers
        List<Reiziger> reizigerList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            //met een while loop voeg ik alle reiziger objecten toe aan de lijst van reizigers.
            while (resultSet.next()) {
                reizigerList.add(mapResultSetToReiziger(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //ik sluit daarna de connectie
        connection.close();
        return reizigerList;
    }

    //Met deze methode map ik alle info vanuit de resultset naar het (nieuwe) Reiziger object. Dit object return ik.
    public Reiziger mapResultSetToReiziger(ResultSet resultSet) throws SQLException {
        return new Reiziger(
                resultSet.getInt("reiziger_id"),
                resultSet.getString("voorletters"),
                resultSet.getString("tussenvoegsel"),
                resultSet.getString("achternaam"),
                resultSet.getDate("geboortedatum")
        );
    }
}
