package org.example.p4.database.postgresql;

import org.example.p4.database.interfaces.AdresDAO;
import org.example.p4.domain.Adres;
import org.example.p4.domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private final Connection connection;

    public AdresDAOPsql(Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean save(Adres adres) throws SQLException {
        String query = "INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id)" +
                "VALUES (?,?,?,?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, adres.getId());
        preparedStatement.setString(2, adres.getPostcode());
        preparedStatement.setString(3, adres.getHuisnummer());
        preparedStatement.setString(4, adres.getStraat());
        preparedStatement.setString(5, adres.getWoonplaats());
        preparedStatement.setInt(6, adres.getReiziger().getId());
        preparedStatement.executeUpdate();

        return true;
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        String query = "UPDATE adres SET " +
                "postcode = ?," +
                "huisnummer = ?," +
                "straat = ?," +
                "woonplaats = ? " +
                "WHERE adres_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, adres.getPostcode());
        preparedStatement.setString(2, adres.getHuisnummer());
        preparedStatement.setString(3, adres.getStraat());
        preparedStatement.setString(4, adres.getWoonplaats());
        preparedStatement.setInt(5, adres.getId());
        preparedStatement.executeUpdate();

        return true;
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        String query = "DELETE FROM adres WHERE adres_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, adres.getId());
        preparedStatement.executeUpdate();

        return true;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        String query = "SELECT * FROM adres WHERE reiziger_id = ?";
        Adres adresToReturn = null;

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, reiziger.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            adresToReturn = mapResultSetToAdres(resultSet);
        }

        return adresToReturn;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        String query = "SELECT * FROM adres";

        List<Adres> adresList = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            adresList.add(mapResultSetToAdres(resultSet));
        }

        return adresList;
    }

    private Adres mapResultSetToAdres(ResultSet resultSet) throws SQLException {
        ReizigerDAOPsql reizigerDAOPsql = new ReizigerDAOPsql(connection);
        Reiziger reiziger = reizigerDAOPsql.findById(resultSet.getInt("reiziger_id"));
        return new Adres(
                resultSet.getInt("adres_id"),
                resultSet.getString("postcode"),
                resultSet.getString("huisnummer"),
                resultSet.getString("straat"),
                resultSet.getString("woonplaats"),
                reiziger
        );
    }
}
