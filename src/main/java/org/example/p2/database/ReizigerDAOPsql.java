package org.example.p2.database;

import org.example.p2.domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    private final Connection connection;

    public ReizigerDAOPsql(Connection connection){
        this.connection = connection;
    }

    @Override
    public Boolean save(Reiziger reiziger) throws SQLException {
        String query = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum)" +
                "VALUES (?,?,?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, reiziger.getId());
        preparedStatement.setString(2, reiziger.getVoorletters());
        preparedStatement.setString(3, reiziger.getTussenvoegsel());
        preparedStatement.setString(4, reiziger.getAchternaam());
        preparedStatement.setDate(5, java.sql.Date.valueOf(reiziger.getGeboortedatum()));
        preparedStatement.executeUpdate();

        return true;
    }

    @Override
    public Boolean update(Reiziger reiziger) throws SQLException {
        String query = "UPDATE reiziger SET" +
                "voorletters = ?" +
                "tussenvoegsel = ?" +
                "achternaam = ?" +
                "geboortedatum = ?" +
                "WHERE reiziger_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, reiziger.getVoorletters());
        preparedStatement.setString(2, reiziger.getTussenvoegsel());
        preparedStatement.setString(3, reiziger.getAchternaam());
        preparedStatement.setDate(4, java.sql.Date.valueOf(reiziger.getGeboortedatum()));
        preparedStatement.setInt(5, reiziger.getId());
        preparedStatement.executeUpdate();

        return true;
    }

    @Override
    public Boolean delete(Reiziger reiziger) throws SQLException {
        String query = "DELETE FROM reiziger WHERE reiziger_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, reiziger.getId());
        preparedStatement.executeUpdate();

        return true;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        String query = "SELECT * FROM reiziger WHERE reiziger_id = ?";
        Reiziger reiziger = null;

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            reiziger = mapResultSetToReiziger(resultSet);
        } else {
            System.out.println("Geen reiziger gevonden met ID: "+id);
        }

        return reiziger;
    }

    @Override
    public List<Reiziger> findByGbDatum(LocalDate date) throws SQLException {
        String query = "SELECT * FROM reiziger WHERE geboortedatum = ?";

        List<Reiziger> reizigerList = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDate(1, java.sql.Date.valueOf(date));
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            reizigerList.add(mapResultSetToReiziger(resultSet));
        }

        return reizigerList;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        String query = "SELECT * FROM reiziger";

        List<Reiziger> reizigerList = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            reizigerList.add(mapResultSetToReiziger(resultSet));
        }

        return reizigerList;
    }

    private Reiziger mapResultSetToReiziger(ResultSet resultSet) throws SQLException {
        return new Reiziger(
                resultSet.getInt("reiziger_id"),
                resultSet.getString("voorletters"),
                resultSet.getString("tussenvoegsel"),
                resultSet.getString("achternaam"),
                resultSet.getDate("geboortedatum").toLocalDate()
        );
    }
}
